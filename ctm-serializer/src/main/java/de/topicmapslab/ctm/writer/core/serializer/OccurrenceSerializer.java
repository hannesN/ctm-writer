/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/    
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 *
 */
package de.topicmapslab.ctm.writer.core.serializer;

import static de.topicmapslab.ctm.writer.utility.CTMTokens.COLON;
import static de.topicmapslab.ctm.writer.utility.CTMTokens.TABULATOR;
import static de.topicmapslab.ctm.writer.utility.CTMTokens.WHITESPACE;

import org.tmapi.core.Occurrence;
import org.tmapi.core.Topic;

import de.topicmapslab.ctm.writer.exception.SerializerException;
import de.topicmapslab.ctm.writer.properties.CTMTopicMapWriterProperties;
import de.topicmapslab.ctm.writer.utility.CTMBuffer;
import de.topicmapslab.ctm.writer.utility.CTMIdentity;

/**
 * Class to realize the serialization of the following CTM grammar rule. <br />
 * <br />
 * <code>occurrence ::= '-'  type ':' literal scope?  reifier?  </code><br />
 * <br />
 * The serialized CTM string represents the occurrence of a topic within the
 * topic block.
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 * 
 */
public class OccurrenceSerializer implements ISerializer<Occurrence> {

	/**
	 * properties for CTM topic map writer
	 */
	private final CTMTopicMapWriterProperties properties;

	/**
	 * identity utility (cache and generator)
	 */
	private final CTMIdentity ctmIdentity;
	
	/**
	 * constructor
	 * 
	 * @param properties
	 *            the internal {@link CTMTopicMapWriterProperties} *
	 */
	public OccurrenceSerializer(CTMTopicMapWriterProperties properties, CTMIdentity ctmIdentity) {
		this.properties = properties;
		this.ctmIdentity = ctmIdentity;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean serialize(Occurrence occurrence, CTMBuffer buffer)
			throws SerializerException {

		/*
		 * begin occurrence-definition block
		 */
		buffer.append(true, TABULATOR, ctmIdentity.getMainIdentifier(
				properties, occurrence.getType()).toString(), COLON, WHITESPACE);

		/*
		 * add value and data-type
		 */
		new DatatypeAwareSerializer(properties, ctmIdentity).serialize(occurrence, buffer);

		CTMBuffer ctmBuffer = null;
		/*
		 * add scope if exists
		 */
		ctmBuffer = new CTMBuffer();
		if (new ScopedSerializer(properties, ctmIdentity).serialize(occurrence, ctmBuffer)) {
			buffer.append(WHITESPACE);
			buffer.append(ctmBuffer);
		}

		/*
		 * add reifier if exists
		 */
		ctmBuffer = new CTMBuffer();
		if (new ReifiableSerializer(properties, ctmIdentity)
				.serialize(occurrence, ctmBuffer)) {
			buffer.append(WHITESPACE);
			buffer.append(ctmBuffer);
		}

		buffer.appendTailLine();
		return true;
	}

	/**
	 * Static method to generate CTM occurrence-block by value, data-type and
	 * type.
	 * 
	 * @param properties
	 *            the internal {@link CTMTopicMapWriterProperties}
	 * @param value
	 *            the value of the occurrence
	 * @param datatype
	 *            the data-type of the occurrence
	 * @param type
	 *            the type of the occurrence
	 * @param buffer
	 *            the buffer written to
	 * @return <code>true</code> if new content was written into buffer,
	 *         <code>false</code> otherwise.
	 * @throws SerializerException
	 *             Thrown if serialization failed.
	 */
	public static boolean serialize(CTMTopicMapWriterProperties properties, CTMIdentity ctmIdentity,
			String value, Object datatype, Topic type, CTMBuffer buffer)
			throws SerializerException {

		buffer.append(true, TABULATOR, ctmIdentity.getMainIdentifier(
				properties, type).toString(), COLON, WHITESPACE);

		/*
		 * add value and data-type
		 */
		new DatatypeAwareSerializer(properties, ctmIdentity).serialize(datatype, value,
				buffer);

		return true;
	}

}
