/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/    
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 *
 */
package de.topicmapslab.ctm.writer.core.serializer;

import static de.topicmapslab.ctm.writer.utility.CTMTokens.UTF8ENCODING;
import static de.topicmapslab.ctm.writer.utility.CTMTokens.VERSION;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.tmapi.core.Association;
import org.tmapi.core.Construct;
import org.tmapi.core.Name;
import org.tmapi.core.Occurrence;
import org.tmapi.core.Role;
import org.tmapi.core.Topic;
import org.tmapi.core.TopicMap;
import org.tmapi.core.Variant;

import de.topicmapslab.ctm.writer.core.CTMTopicMapWriter;
import de.topicmapslab.ctm.writer.core.PrefixHandler;
import de.topicmapslab.ctm.writer.exception.NoIdentityException;
import de.topicmapslab.ctm.writer.exception.SerializerException;
import de.topicmapslab.ctm.writer.templates.ITemplateScanner;
import de.topicmapslab.ctm.writer.templates.Template;
import de.topicmapslab.ctm.writer.templates.TemplateMatching;
import de.topicmapslab.ctm.writer.templates.TemplateMerger;
import de.topicmapslab.ctm.writer.templates.TemplateSerializer;
import de.topicmapslab.ctm.writer.templates.autodetection.TemplateDetection;
import de.topicmapslab.ctm.writer.utility.ICTMWriter;
import de.topicmapslab.identifier.TmdmSubjectIdentifier;

/**
 * Class to realize the serialization of the following CTM grammar rule. <br />
 * <br />
 * <code>	topicmap ::=	prolog
 * 							( directive* reifier )?
 * 							(
 * 	 							directive	  |
 * 								topic	  |
 * 								association	  |
 * 								template	  |
 * 								template-invocation	
 * 							)   </code><br />
 * <br />
 * The serialized CTM string represents the a topic map block.
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 * 
 */
public class TopicMapSerializer implements ISerializer<TopicMap> {

	/**
	 * set of defined templates
	 */
	private final Set<Template> templates = new HashSet<Template>();

	/**
	 * the parent topic map writer
	 */
	private final CTMTopicMapWriter writer;

	/**
	 * the prefix handler
	 */
	private final PrefixHandler prefixHandler;

	private final Map<Template, Set<TemplateMatching>> matchings = new HashMap<Template, Set<TemplateMatching>>();
	private final Map<Construct, Set<TemplateMatching>> constructMatchings = new HashMap<Construct, Set<TemplateMatching>>();
	private final Set<Construct> ignoredConstructs = new HashSet<Construct>();

	/**
	 * Constructor 
	 * 
	 * @param writer the topic map writer to use
	 * @param prefixHandler a prefix handler
	 */
	public TopicMapSerializer(CTMTopicMapWriter writer, PrefixHandler prefixHandler) {
		this.prefixHandler = prefixHandler;
		this.writer = writer;
	}

	/**
	 * add a template to the internal set. Templates are used to replace frequently used parts of topic blocks or
	 * associations.
	 * 
	 * @param template
	 *            the template to add
	 */
	public void addTemplate(Template template) {
		templates.add(template);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean serialize(TopicMap topicMap, ICTMWriter buffer) throws SerializerException, IOException {

		/*
		 * add encoding
		 */
		buffer.appendLine(UTF8ENCODING);

		/*
		 * add version
		 */
		buffer.appendLine(VERSION);

		// some empty lines
		buffer.appendLine();

		/*
		 * add reification of topic map if exists
		 */
		buffer.appendCommentLine("reifier of the topicmap");
		if (ReifiableSerializer.serialize(writer, topicMap, buffer)) {
			buffer.appendLine();
		}

		/*
		 * add prefixes if some exists
		 */
		if (writer.getProperties().isPrefixDetectionEnabled() || !prefixHandler.getPrefixMap().isEmpty()) {
			buffer.appendCommentLine("prefixes");
			buffer.appendLine();
			PrefixesSerializer.serialize(prefixHandler, writer.getProperties().isPrefixDetectionEnabled(), topicMap,
					buffer);
			buffer.appendLine();
		}

		/*
		 * add includes if exists
		 */
		if (!writer.getIncludes().isEmpty()) {
			buffer.appendCommentLine("includes");
			buffer.appendLine();
			IncludeSerializer.serialize(writer.getIncludes(), prefixHandler, buffer);
		}

		/*
		 * add mergemap if exists
		 */
		if (!writer.getMergeMaps().isEmpty()) {
			buffer.appendCommentLine("mergemap");
			buffer.appendLine();
			MergeMapSerializer.serialize(writer.getMergeMaps(), prefixHandler, buffer);
		}

		/*
		 * start topic map block
		 */
		buffer.appendLine();

		/*
		 * try to auto-detect templates if properties is enabled
		 */
		if (writer.getProperties().isTemplateDetectionEnabled()) {
			TemplateDetection detection = new TemplateDetection(writer, topicMap);
			templates.addAll(detection.tryToDetectTemplates());
		}

		/*
		 * try to merge templates if properties is set
		 */
		if (writer.getProperties().isTemplateMergerEnabled()) {
			Collection<Template> templates = new TemplateMerger(writer.getProperties()).mergeTemplates(this.templates);
			this.templates.clear();
			this.templates.addAll(templates);
		}

		/*
		 * check if templates has to exported
		 */
		if (writer.getProperties().isTemplateExportEnabled()) {
			/*
			 * generate template-definition blocks
			 */
			if (!templates.isEmpty()) {
				buffer.appendCommentLine("template definitions");
			}
			for (Template template : templates) {
				/*
				 * check if the template is restricted for export
				 */
				if (!writer.getProperties().getRestrictedTemplatesToExport().contains(template.getTemplateName())
						&& template.shouldSerialize()) {
					TemplateSerializer.serialize(template, buffer);
				}
			}
		}

		/*
		 * call template scanners
		 */
		for (Template t : templates) {
			ITemplateScanner scanner = t.getScanner();
			if (scanner == null) {
				continue;
			}

			Set<TemplateMatching> set = scanner.getAdaptiveConstructs(topicMap);
			for (TemplateMatching matching : set) {
				if (matching.getContext() == null) {
					continue;
				}
				Set<TemplateMatching> s = constructMatchings.get(matching.getContext());
				if (s == null) {
					s = new HashSet<TemplateMatching>();
				}
				s.add(matching);
				matching.setTemplate(t);
				constructMatchings.put(matching.getContext(), s);
				ignoredConstructs.addAll(matching.getAffectedConstructs());
			}
			matchings.put(t, set);
		}

		/*
		 * generate topic-definition blocks
		 */
		buffer.appendCommentLine("topic definitions");
		for (Topic topic : topicMap.getTopics()) {
			if (ignoredConstructs.contains(topic)) {
				continue;
			}
			/*
			 * ignore TMDM topics
			 */
			if (!topic.getSubjectIdentifiers().isEmpty()
					&& TmdmSubjectIdentifier.isTmdmSubjectIdentifier(topic.getSubjectIdentifiers().iterator().next()
							.toExternalForm())) {
				continue;
			}
			serializeTopicToCTM(topic, buffer);
		}

		/*
		 * generate association-definition blocks
		 */
		buffer.appendCommentLine("association definitions");
		Set<Object> affectedConstronstructs = new HashSet<Object>();
		for (Association association : topicMap.getAssociations()) {
			if (ignoredConstructs.contains(association)) {
				continue;
			}
			/*
			 * already exported
			 */
			if (affectedConstronstructs.contains(association)) {
				continue;
			}
			try {
				writer.getCtmIdentity().getIdentity(writer.getProperties(), association.getType());
				/*
				 * ignore TMDM associations
				 */
				if (!association.getType().getSubjectIdentifiers().isEmpty()
						&& TmdmSubjectIdentifier.isTmdmSubjectIdentifier(association.getType().getSubjectIdentifiers()
								.iterator().next().toExternalForm())) {
					continue;
				}
				affectedConstronstructs.addAll(AssociationSerializer.serialize(writer, new HashSet<Template>(),
						association, buffer));
				buffer.appendLine();
			} catch (NoIdentityException e) {
			}
		}

		/*
		 * add comment
		 */
		buffer.appendCommentLine("Generated by the CTM Topic Map Writer.");

		/*
		 * end topic map definition
		 */
		return true;
	}

	/**
	 * Method to convert the given constructs to its specific CTM strings. The result should be written to the given
	 * output buffer.
	 * 
	 * @param constructs
	 *            a set of topic map constructs to serialize
	 * @param buffer
	 *            the output buffer
	 * @return <code>true</code> if new content was written into buffer, <code>false</code> otherwise
	 * @throws SerializerException
	 *             Thrown if serialization failed.
	 */
	public boolean serialize(Collection<Construct> constructs, ICTMWriter buffer) throws SerializerException,
			IOException {

		/*
		 * topic item store
		 */
		Collection<Topic> topics = new HashSet<Topic>();
		/*
		 * association item store
		 */
		Collection<Association> associations = new HashSet<Association>();

		/*
		 * sort constructs by type
		 */
		for (Construct construct : constructs) {
			if (construct instanceof Topic) {
				topics.add((Topic) construct);
			} else if (construct instanceof Name) {
				topics.add(((Name) construct).getParent());
			} else if (construct instanceof Occurrence) {
				topics.add(((Occurrence) construct).getParent());
			} else if (construct instanceof Variant) {
				topics.add(((Variant) construct).getParent().getParent());
			} else if (construct instanceof Association) {
				associations.add(((Association) construct));
			} else if (construct instanceof Role) {
				associations.add(((Role) construct).getParent());
			}
		}

		/*
		 * generate topic-definition blocks
		 */
		// buffer.appendCommentLine("topic definitions");
		for (Topic topic : topics) {
			serializeTopicToCTM(topic, buffer);
			buffer.appendLine();
		}

		/*
		 * generate association-definition blocks
		 */
		// buffer.appendCommentLine("association definitions");
		for (Association association : associations) {
			try {
				writer.getCtmIdentity().getIdentity(writer.getProperties(), association.getType());
				/*
				 * ignore TMDM associations
				 */
				AssociationSerializer.serialize(writer, new HashSet<Template>(), association, buffer);
				buffer.appendLine();
			} catch (NoIdentityException e) {
			}
		}

		/*
		 * add comment
		 */
		buffer.appendCommentLine("Generated by the CTM Topic Map Writer.");

		/*
		 * end topic map definition
		 */
		return true;
	}

	/**
	 * Internal method to export the given topic to CTM if topic is not a TMDM type. The result is written to the given
	 * buffer.
	 * 
	 * @param topic
	 *            the topic to export
	 * @param buffer
	 *            the buffer written to
	 * @return <code>true</code> if new content was written to the given buffer, <code>false</code> otherwise.
	 * @throws SerializerException
	 *             thrown if serialization failed
	 */
	private final boolean serializeTopicToCTM(final Topic topic, final ICTMWriter buffer)
			throws SerializerException, IOException {
		try {
			/*
			 * generate only for non TMDM types
			 */
			writer.getCtmIdentity().getIdentity(writer.getProperties(), topic);
			TopicSerializer.serialize(writer, constructMatchings.get(topic), topic, buffer);
			buffer.appendLine();
			return true;
		} catch (NoIdentityException e) {
			return false;
		}
	}

	/**
	 * Adds a construct to the ignore set. Every construct in this set will not be serialized. This might be useful, if
	 * the topic map contains topics specified in external ctm files.
	 * 
	 * @param the
	 *            construct to ignore
	 */
	public void addIgnoredConstruct(Construct construct) {
		ignoredConstructs.add(construct);
	}

	// /**
	// * Extract adaptive templates from internal list.
	// *
	// * @param topic
	// * the topic which should be adaptive to the templates
	// * @return a set of adaptive templates for the given topic
	// */
	// @SuppressWarnings("unchecked")
	// private final Set<Template> getAdaptiveTemplates(final Topic topic) {
	// Set<Template> templates = new HashSet<Template>();
	//
	// for (Template t : this.templates) {
	// if (!t.containsOnlyInstanceOf(AssociationEntry.class,
	// TopicEntry.class)
	// && !t.getTemplateName().matches(".*-invoc--?[0-9]+")
	// && t.isAdaptiveFor(topic)) {
	// templates.add(t);
	// }
	// }
	// return templates;
	// }
	//
	// /**
	// * Extract adaptive templates from internal list.
	// *
	// * @param association
	// * the association which should be adaptive to the templates
	// * @return a set of adaptive templates for the given association
	// */
	// @SuppressWarnings("unchecked")
	// private final Set<Template> getAdaptiveTemplates(
	// final Association association) {
	// Set<Template> templates = new HashSet<Template>();
	//
	// for (Template t : this.templates) {
	// if (t.containsOnlyInstanceOf(AssociationEntry.class,
	// TopicEntry.class)
	// && t.isAdaptiveFor(association)) {
	// templates.add(t);
	// }
	// }
	// return templates;
	// }
}
