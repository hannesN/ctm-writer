/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/    
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 *
 */
package de.topicmapslab.ctm.writer.core.serializer;

import static de.topicmapslab.ctm.writer.utility.CTMTokens.ITEMIDENTIFIER;
import static de.topicmapslab.ctm.writer.utility.CTMTokens.SUBJECTLOCATOR;
import static de.topicmapslab.ctm.writer.utility.CTMTokens.TABULATOR;
import static de.topicmapslab.ctm.writer.utility.CTMTokens.WHITESPACE;

import java.util.HashSet;
import java.util.Set;

import org.tmapi.core.Locator;
import org.tmapi.core.Name;
import org.tmapi.core.Occurrence;
import org.tmapi.core.Topic;

import de.topicmapslab.ctm.writer.exception.SerializerException;
import de.topicmapslab.ctm.writer.properties.CTMTopicMapWriterProperties;
import de.topicmapslab.ctm.writer.templates.Template;
import de.topicmapslab.ctm.writer.templates.TemplateInvocationSerializer;
import de.topicmapslab.ctm.writer.utility.CTMBuffer;
import de.topicmapslab.ctm.writer.utility.CTMIdentity;

/**
 * Class to realize the serialization of the following CTM grammar rule. <br />
 * <br />
 * <code>	topic ::= topic-identity topic-tail*  '.'  </code><br />
 * <br />
 * The serialized CTM string represents the a topic block.
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 * 
 */
public class TopicSerializer implements ISerializer<Topic> {

	/**
	 * internal list of adaptive templates
	 */
	private final Set<Template> adaptiveTemplates;

	/**
	 * properties for CTM topic map writer
	 */
	private final CTMTopicMapWriterProperties properties;

	/**
	 * identity utility (cache and generator)
	 */
	private final CTMIdentity ctmIdentity;

	/**
	 * 
	 * constructor, calling the
	 * {@link TopicSerializer#TopicSerializer(CTMTopicMapWriterProperties, Set)}
	 * with a new {@link HashSet}
	 * 
	 * @param properties
	 *            the properties
	 */
	public TopicSerializer(CTMTopicMapWriterProperties properties, CTMIdentity identity) {
		this(properties, new HashSet<Template>(), identity);
	}

	/**
	 * constructor
	 * 
	 * @param properties
	 *            the properties
	 * 
	 * @param adaptiveTemplates
	 *            a set of templates replacing parts of the topic block
	 */
	public TopicSerializer(CTMTopicMapWriterProperties properties,
			Set<Template> adaptiveTemplates, CTMIdentity identity) {
		this.properties = properties;
		this.adaptiveTemplates = adaptiveTemplates;
		this.ctmIdentity = identity;
	}
	
	public CTMIdentity getCtmIdentity() {
		return ctmIdentity;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean serialize(Topic topic, CTMBuffer buffer)
			throws SerializerException {

		
		
		buffer.appendLine(
				getCtmIdentity().getMainIdentifier(properties, topic).toString(),
				WHITESPACE);

		/*
		 * add template-invocations and store affected elements of the topic
		 * block
		 */
		Set<Object> affectedConstructs = new HashSet<Object>();
		for (Template template : adaptiveTemplates) {
			TemplateInvocationSerializer serializer = new TemplateInvocationSerializer(
					template);
			buffer.append(TABULATOR);
			serializer.serialize(topic, buffer);
			affectedConstructs.addAll(serializer.getAffectedConstructs());
		}

		/*
		 * add type-instance-associations
		 */
		new IsInstanceOfSerializer(properties, ctmIdentity, affectedConstructs).serialize(
				topic, buffer);

		/*
		 * add super-type-sub-type-associations
		 */
		new AKindOfSerializer(properties, ctmIdentity, affectedConstructs).serialize(topic,
				buffer);

		/*
		 * add name entries if not affected by template-invocations
		 */
		NameSerializer nameSerializer = new NameSerializer(properties, ctmIdentity);
		for (Name name : topic.getNames()) {
			if (!affectedConstructs.contains(name)) {
				nameSerializer.serialize(name, buffer);
			}
		}

		/*
		 * add occurrence entries if not affected by template-invocations
		 */
		OccurrenceSerializer occurrenceSerializer = new OccurrenceSerializer(properties, ctmIdentity);
		for (Occurrence occurrence : topic.getOccurrences()) {
			if (!affectedConstructs.contains(occurrence)) {
				occurrenceSerializer.serialize(occurrence, buffer);
			}
		}

		/*
		 * add all subject-identifeir
		 */
		for (Locator locator : topic.getSubjectIdentifiers()) {
			if (affectedConstructs.contains(locator)) {
				continue;
			}
			if (locator.toExternalForm().equals(ctmIdentity.getMainIdentifier(properties, topic).getIdentifier())) {
				continue;
			}
			
			String identity = getCtmIdentity().getPrefixedIdentity(locator);
			buffer.appendTailLine(true, TABULATOR, getCtmIdentity()
					.getEscapedCTMIdentity(identity, locator));
		}

		/*
		 * add all subject-locator
		 */
		for (Locator locator : topic.getSubjectLocators()) {
			if (affectedConstructs.contains(locator)) {
				continue;
			}
			String identity = getCtmIdentity().getPrefixedIdentity(locator);
			buffer.appendTailLine(true, TABULATOR, SUBJECTLOCATOR, getCtmIdentity()
					.getEscapedCTMIdentity(identity, locator));
		}

		/*
		 * add all item-identifier if enabled
		 */
		if (properties.isExportOfItemIdentifierEnabled()) {
			for (Locator locator : topic.getItemIdentifiers()) {
				String identity = getCtmIdentity().getPrefixedIdentity(locator);
				if (getCtmIdentity().isSystemItemIdentifier(properties, identity)
						|| affectedConstructs.contains(locator)) {
					continue;
				}
				buffer.appendTailLine(true, TABULATOR, ITEMIDENTIFIER,
						getCtmIdentity().getEscapedCTMIdentity(identity, locator));
			}
		}

		/*
		 * finish topic-block
		 */
		buffer.clearCTMTail();
		return true;
	}

}
