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
	 * Method to convert the given construct to its specific CTM string. The
	 * result should be written to the given output buffer.
	 * 
	 * @param writer
	 *            the CTM writer
	 * @param adaptiveTemplates
	 *            the template which can be used in combination with the given
	 *            topic
	 * @param topic
	 *            the topic to serialize
	 * @param buffer
	 *            the output buffer
	 * @return <code>true</code> if new content was written into buffer,
	 *         <code>false</code> otherwise
	 * @throws SerializerException
	 *             Thrown if serialization failed.
	 */
	public static boolean serialize(CTMTopicMapWriter writer,
			Set<Template> adaptiveTemplates, Topic topic, CTMBuffer buffer)
			throws SerializerException {

		final String mainIdentifier = writer.getCtmIdentity()
				.getMainIdentifier(writer.getProperties(), topic).toString();

		buffer.appendLine(mainIdentifier, WHITESPACE);

		/*
		 * add template-invocations and store affected elements of the topic
		 * block
		 */
		Set<Object> affectedConstructs = new HashSet<Object>();
		for (Template template : adaptiveTemplates) {
			buffer.append(TABULATOR);
			affectedConstructs.addAll(TemplateInvocationSerializer.serialize(
					template, topic, buffer));
		}

		/*
		 * add type-instance-associations
		 */
		IsInstanceOfSerializer.serialize(writer, affectedConstructs, topic,
				buffer);

		/*
		 * add super-type-sub-type-associations
		 */
		AKindOfSerializer.serialize(writer, affectedConstructs, topic, buffer);

		/*
		 * add name entries if not affected by template-invocations
		 */
		for (Name name : topic.getNames()) {
			if (!affectedConstructs.contains(name)) {
				NameSerializer.serialize(writer, name, buffer);
			}
		}

		/*
		 * add occurrence entries if not affected by template-invocations
		 */
		for (Occurrence occurrence : topic.getOccurrences()) {
			if (!affectedConstructs.contains(occurrence)) {
				OccurrenceSerializer.serialize(writer, occurrence, buffer);
			}
		}

		/*
		 * add all subject-identifeir
		 */
		for (Locator locator : topic.getSubjectIdentifiers()) {
			if (affectedConstructs.contains(locator)) {
				continue;
			}
			String identity = writer.getCtmIdentity().getPrefixedIdentity(
					locator);
			identity = writer.getCtmIdentity().getEscapedCTMIdentity(identity,
					locator);
			if (!identity.equalsIgnoreCase(mainIdentifier)) {
				buffer.appendTailLine(true, TABULATOR, identity);
			}
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
			identity = writer.getCtmIdentity().getEscapedCTMIdentity(identity,
					locator);
			if (!mainIdentifier.contains(identity)) {
				buffer
						.appendTailLine(true, TABULATOR, SUBJECTLOCATOR,
								identity);
			}
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
				if (!mainIdentifier.contains(identity)) {
					buffer.appendTailLine(true, TABULATOR, ITEMIDENTIFIER,
							writer.getCtmIdentity().getEscapedCTMIdentity(
									identity, locator));
				}
			}
		}

		/*
		 * finish topic-block
		 */
		buffer.clearCTMTail();
		return true;
	}

}
