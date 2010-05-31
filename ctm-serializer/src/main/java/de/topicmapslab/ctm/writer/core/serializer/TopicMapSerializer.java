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

import java.util.Collection;
import java.util.HashSet;
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
import de.topicmapslab.ctm.writer.templates.Template;
import de.topicmapslab.ctm.writer.templates.TemplateMerger;
import de.topicmapslab.ctm.writer.templates.TemplateSerializer;
import de.topicmapslab.ctm.writer.templates.autodetection.TemplateDetection;
import de.topicmapslab.ctm.writer.templates.entry.AssociationEntry;
import de.topicmapslab.ctm.writer.templates.entry.TopicEntry;
import de.topicmapslab.ctm.writer.utility.CTMBuffer;
import de.topicmapslab.java.tmdm.TmdmSubjectIdentifier;

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

	public TopicMapSerializer(CTMTopicMapWriter writer,
			PrefixHandler prefixHandler) {
		this.prefixHandler = prefixHandler;
		this.writer = writer;
	}

	/**
	 * add a template to the internal set. Templates are used to replace
	 * frequently used parts of topic blocks or associations.
	 * 
	 * @param template
	 *            the template to add
	 */
	public void add(Template template) {
		templates.add(template);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean serialize(TopicMap topicMap, CTMBuffer buffer)
			throws SerializerException {

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
		buffer.appendLine();

		/*
		 * add reification of topic map if exists
		 */
		CTMBuffer ctmBuffer = new CTMBuffer();
		if (ReifiableSerializer.serialize(writer, topicMap, ctmBuffer)) {
			buffer.appendCommentLine("reifier of the topicmap");
			buffer.appendLine(ctmBuffer);
			buffer.appendLine();
		}

		/*
		 * add prefixes if some exists
		 */
		if (writer.getProperties().isPrefixDetectionEnabled()
				|| !prefixHandler.getPrefixMap().isEmpty()) {
			CTMBuffer prefixBuffer = new CTMBuffer();
			buffer.appendCommentLine("prefixes");
			buffer.appendLine();
			if (PrefixesSerializer.serialize(prefixHandler, writer
					.getProperties().isPrefixDetectionEnabled(), topicMap,
					prefixBuffer)) {
				buffer.appendLine(prefixBuffer);
			}
			buffer.appendLine();
		}

		/*
		 * add includes if exists
		 */
		if (!writer.getIncludes().isEmpty()) {
			CTMBuffer includeBuffer = new CTMBuffer();
			buffer.appendCommentLine("includes");
			buffer.appendLine();
			if (IncludeSerializer.serialize(writer.getIncludes(),
					prefixHandler, includeBuffer)) {
				buffer.appendLine(includeBuffer);
			}
			buffer.appendLine();
		}

		/*
		 * add mergemap if exists
		 */
		if (!writer.getMergeMaps().isEmpty()) {
			CTMBuffer mergeMapBuffer = new CTMBuffer();
			buffer.appendCommentLine("mergemap");
			buffer.appendLine();
			if (MergeMapSerializer.serialize(writer.getMergeMaps(),
					prefixHandler, mergeMapBuffer)) {
				buffer.appendLine(mergeMapBuffer);
			}
			buffer.appendLine();
		}

		/*
		 * start topic map block
		 */
		buffer.appendLine();

		/*
		 * try to auto-detect templates if properties is enabled
		 */
		if (writer.getProperties().isTemplateDetectionEnabled()) {
			TemplateDetection detection = new TemplateDetection(writer,
					topicMap);
			templates.addAll(detection.tryToDetectTemplates());
		}

		/*
		 * try to merge templates if properties is set
		 */
		if (writer.getProperties().isTemplateMergerEnabled()) {
			Collection<Template> templates = new TemplateMerger(writer
					.getProperties()).mergeTemplates(this.templates);
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
				if (!writer.getProperties().getRestrictedTemplatesToExport()
						.contains(template.getTemplateName())
						&& template.shouldSerialize()) {
					ctmBuffer = new CTMBuffer();
					TemplateSerializer.serialize(template, ctmBuffer);
					buffer.appendLine(ctmBuffer);
				}
			}
		}
		/*
		 * generate topic-definition blocks
		 */
		buffer.appendCommentLine("topic definitions");
		for (Topic topic : topicMap.getTopics()) {
			/*
			 * ignore TMDM topics
			 */
			if (!topic.getSubjectIdentifiers().isEmpty()
					&& TmdmSubjectIdentifier.isTmdmSubjectIdentifier(topic
							.getSubjectIdentifiers().iterator().next()
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
			/*
			 * already exported
			 */
			if (affectedConstronstructs.contains(association)) {
				continue;
			}
			ctmBuffer = new CTMBuffer();
			try {
				writer.getCtmIdentity().getIdentity(writer.getProperties(),
						association.getType());
				/*
				 * ignore TMDM associations
				 */
				if (!association.getType().getSubjectIdentifiers().isEmpty()
						&& TmdmSubjectIdentifier
								.isTmdmSubjectIdentifier(association.getType()
										.getSubjectIdentifiers().iterator()
										.next().toExternalForm())) {
					continue;
				}
				affectedConstronstructs.addAll(AssociationSerializer.serialize(
						writer, getAdaptiveTemplates(association), association,
						ctmBuffer));
				buffer.appendLine(ctmBuffer);
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
	 * Method to convert the given constructs to its specific CTM strings. The
	 * result should be written to the given output buffer.
	 * 
	 * @param constructs
	 *            a set of topic map constructs to serialize
	 * @param buffer
	 *            the output buffer
	 * @return <code>true</code> if new content was written into buffer,
	 *         <code>false</code> otherwise
	 * @throws SerializerException
	 *             Thrown if serialization failed.
	 */
	public boolean serialize(Collection<Construct> constructs, CTMBuffer buffer)
			throws SerializerException {

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

		// /*
		// * add comment
		// */
		// buffer.appendCommentLine("Generated by the CTM Topic Map Writer.");
		//
		// /*
		// * add encoding
		// */
		// buffer.appendLine(UTF8ENCODING);
		//
		// /*
		// * add version
		// */
		// buffer.appendLine(VERSION);

		/*
		 * start topic map block
		 */
		buffer.appendLine();

		/*
		 * generate topic-definition blocks
		 */
		// buffer.appendCommentLine("topic definitions");
		for (Topic topic : topics) {
			serializeTopicToCTM(topic, buffer);
		}

		/*
		 * generate association-definition blocks
		 */
		// buffer.appendCommentLine("association definitions");
		for (Association association : associations) {

			CTMBuffer ctmBuffer = new CTMBuffer();
			try {
				writer.getCtmIdentity().getIdentity(writer.getProperties(),
						association.getType());
				/*
				 * ignore TMDM associations
				 */
				AssociationSerializer.serialize(writer,
						getAdaptiveTemplates(association), association,
						ctmBuffer);
				buffer.appendLine(ctmBuffer);
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
	 * Internal method to export the given topic to CTM if topic is not a TMDM
	 * type. The result is written to the given buffer.
	 * 
	 * @param topic
	 *            the topic to export
	 * @param buffer
	 *            the buffer written to
	 * @return <code>true</code> if new content was written to the given buffer,
	 *         <code>false</code> otherwise.
	 * @throws SerializerException
	 *             thrown if serialization failed
	 */
	private final boolean serializeTopicToCTM(final Topic topic,
			final CTMBuffer buffer) throws SerializerException {
		CTMBuffer ctmBuffer = new CTMBuffer();
		try {
			/*
			 * generate only for non TMDM types
			 */
			writer.getCtmIdentity().getIdentity(writer.getProperties(), topic);
			TopicSerializer.serialize(writer, getAdaptiveTemplates(topic),
					topic, ctmBuffer);
			buffer.appendLine(ctmBuffer);
			return true;
		} catch (NoIdentityException e) {
			return false;
		}
	}

	/**
	 * Extract adaptive templates from internal list.
	 * 
	 * @param topic
	 *            the topic which should be adaptive to the templates
	 * @return a set of adaptive templates for the given topic
	 */
	@SuppressWarnings("unchecked")
	private final Set<Template> getAdaptiveTemplates(final Topic topic) {
		Set<Template> templates = new HashSet<Template>();

		for (Template t : this.templates) {
			if (!t.containsOnlyInstanceOf(AssociationEntry.class,
					TopicEntry.class)
					&& !t.getTemplateName().matches(".*-invoc--?[0-9]+")
					&& t.isAdaptiveFor(topic)) {
				templates.add(t);
			}
		}
		return templates;
	}

	/**
	 * Extract adaptive templates from internal list.
	 * 
	 * @param association
	 *            the association which should be adaptive to the templates
	 * @return a set of adaptive templates for the given association
	 */
	@SuppressWarnings("unchecked")
	private final Set<Template> getAdaptiveTemplates(
			final Association association) {
		Set<Template> templates = new HashSet<Template>();

		for (Template t : this.templates) {
			if (t.containsOnlyInstanceOf(AssociationEntry.class,
					TopicEntry.class)
					&& t.isAdaptiveFor(association)) {
				templates.add(t);
			}
		}
		return templates;
	}
}
