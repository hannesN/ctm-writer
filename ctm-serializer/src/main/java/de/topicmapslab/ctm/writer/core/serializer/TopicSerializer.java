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

import de.topicmapslab.ctm.writer.core.CTMTopicMapWriter;
import de.topicmapslab.ctm.writer.exception.SerializerException;
import de.topicmapslab.ctm.writer.templates.Template;
import de.topicmapslab.ctm.writer.templates.TemplateInvocationSerializer;
import de.topicmapslab.ctm.writer.utility.CTMBuffer;

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
	 * the parent topic map writer
	 */
	private final CTMTopicMapWriter writer;

	/**
	 * 
	 * constructor, calling the
	 * {@link TopicSerializer#TopicSerializer(CTMTopicMapWriter, Set)} with a
	 * new {@link HashSet}
	 * 
	 * @param writer
	 *            the parent topic map writer
	 */
	public TopicSerializer(CTMTopicMapWriter writer) {
		this(writer, new HashSet<Template>());
	}

	/**
	 * constructor
	 * 
	 * @param writer
	 *            the parent topic map writer
	 * 
	 * @param adaptiveTemplates
	 *            a set of templates replacing parts of the topic block
	 */
	public TopicSerializer(CTMTopicMapWriter writer,
			Set<Template> adaptiveTemplates) {
		this.writer = writer;
		this.adaptiveTemplates = adaptiveTemplates;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean serialize(Topic topic, CTMBuffer buffer)
			throws SerializerException {

		buffer.appendLine(writer.getCtmIdentity().getMainIdentifier(
				writer.getProperties(), topic).toString(), WHITESPACE);

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
		new IsInstanceOfSerializer(writer, affectedConstructs).serialize(topic,
				buffer);

		/*
		 * add super-type-sub-type-associations
		 */
		new AKindOfSerializer(writer, affectedConstructs).serialize(topic,
				buffer);

		/*
		 * add name entries if not affected by template-invocations
		 */
		NameSerializer nameSerializer = new NameSerializer(writer);
		for (Name name : topic.getNames()) {
			if (!affectedConstructs.contains(name)) {
				nameSerializer.serialize(name, buffer);
			}
		}

		/*
		 * add occurrence entries if not affected by template-invocations
		 */
		OccurrenceSerializer occurrenceSerializer = new OccurrenceSerializer(
				writer);
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
			if (locator.toExternalForm().equals(
					writer.getCtmIdentity().getMainIdentifier(
							writer.getProperties(), topic).getIdentifier())) {
				continue;
			}

			String identity = writer.getCtmIdentity().getPrefixedIdentity(
					locator);
			buffer.appendTailLine(true, TABULATOR, writer.getCtmIdentity()
					.getEscapedCTMIdentity(identity, locator));
		}

		/*
		 * add all subject-locator
		 */
		for (Locator locator : topic.getSubjectLocators()) {
			if (affectedConstructs.contains(locator)) {
				continue;
			}
			String identity = writer.getCtmIdentity().getPrefixedIdentity(
					locator);
			buffer.appendTailLine(true, TABULATOR, SUBJECTLOCATOR, writer
					.getCtmIdentity().getEscapedCTMIdentity(identity, locator));
		}

		/*
		 * add all item-identifier if enabled
		 */
		if (writer.getProperties().isExportOfItemIdentifierEnabled()) {
			for (Locator locator : topic.getItemIdentifiers()) {
				String identity = writer.getCtmIdentity().getPrefixedIdentity(
						locator);
				if (writer.getCtmIdentity().isSystemItemIdentifier(
						writer.getProperties(), identity)
						|| affectedConstructs.contains(locator)) {
					continue;
				}
				buffer.appendTailLine(true, TABULATOR, ITEMIDENTIFIER, writer
						.getCtmIdentity().getEscapedCTMIdentity(identity,
								locator));
			}
		}

		/*
		 * finish topic-block
		 */
		buffer.clearCTMTail();
		return true;
	}

}
