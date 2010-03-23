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
import static de.topicmapslab.ctm.writer.utility.CTMTokens.NAME;
import static de.topicmapslab.ctm.writer.utility.CTMTokens.QUOTE;
import static de.topicmapslab.ctm.writer.utility.CTMTokens.TABULATOR;
import static de.topicmapslab.ctm.writer.utility.CTMTokens.TRIPPLEQUOTE;
import static de.topicmapslab.ctm.writer.utility.CTMTokens.WHITESPACE;

import org.tmapi.core.Name;
import org.tmapi.core.Topic;
import org.tmapi.core.Variant;

import de.topicmapslab.ctm.writer.core.CTMTopicMapWriter;
import de.topicmapslab.ctm.writer.exception.NoIdentityException;
import de.topicmapslab.ctm.writer.exception.SerializerException;
import de.topicmapslab.ctm.writer.properties.CTMTopicMapWriterProperties;
import de.topicmapslab.ctm.writer.utility.CTMBuffer;

/**
 * Class to realize the serialization of the following CTM grammar rule. <br />
 * <br />
 * <code>name ::= '-'  (type ':')?  string scope?  reifier?  variant* </code><br />
 * <br />
 * The serialized CTM string represents the name of a topic within the topic
 * block.
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 * 
 */
public class NameSerializer implements ISerializer<Name> {

	/**
	 * the parent topic map writer
	 */
	private final CTMTopicMapWriter writer;

	/**
	 * constructor
	 * 
	 * @param properties
	 *            the internal {@link CTMTopicMapWriterProperties} *
	 */
	public NameSerializer(CTMTopicMapWriter writer) {
		this.writer = writer;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean serialize(Name name, CTMBuffer buffer)
			throws SerializerException {

		/*
		 * begin name definition
		 */
		buffer.append(true, TABULATOR, NAME, WHITESPACE);
		try {
			/*
			 * add type if it is not default name type of TMDM
			 */
			buffer.append(false, writer.getCtmIdentity().getMainIdentifier(
					writer.getProperties(), name.getType()).toString(),
					WHITESPACE, COLON, WHITESPACE);
		} catch (NoIdentityException e) {
			// VOID
		}
		/*
		 * append value
		 */
		buffer.append(false, QUOTE, name.getValue(), QUOTE);

		CTMBuffer ctmBuffer = null;

		/*
		 * add scope if exists
		 */
		ctmBuffer = new CTMBuffer();
		if (new ScopedSerializer(writer).serialize(name, ctmBuffer)) {
			buffer.append(WHITESPACE);
			buffer.append(ctmBuffer);
		}

		/*
		 * add reifier if exists
		 */
		ctmBuffer = new CTMBuffer();
		if (new ReifiableSerializer(writer).serialize(name, ctmBuffer)) {
			buffer.append(WHITESPACE);
			buffer.append(ctmBuffer);
		}

		/*
		 * add variants if exists
		 */
		for (Variant variant : name.getVariants()) {
			ctmBuffer = new CTMBuffer();
			/*
			 * redirect to variant serializer
			 */
			new VariantSerializer(writer).serialize(variant, ctmBuffer);
			buffer.append(WHITESPACE);
			buffer.append(ctmBuffer);
		}

		/*
		 * end topic-tail
		 */
		buffer.appendTailLine();

		return true;
	}

	/**
	 * Static method to generate CTM name-block by value and type.
	 * 
	 * @param writer
	 *            the parent topic map writer
	 * @param value
	 *            the value of the name
	 * @param type
	 *            the type of the name
	 * @param buffer
	 *            the buffer written to
	 * @return <code>true</code> if new content was written into buffer,
	 *         <code>false</code> otherwise.
	 * @throws SerializerException
	 *             Thrown if serialization failed.
	 */
	public static boolean serialize(final CTMTopicMapWriter writer,
			final String value, final Topic type, CTMBuffer buffer)
			throws SerializerException {

		buffer.append(true, TABULATOR, NAME, WHITESPACE);
		try {
			buffer.append(false, writer.getCtmIdentity().getMainIdentifier(
					writer.getProperties(), type).toString(), COLON);
		} catch (NoIdentityException e) {
		} catch (NullPointerException e) {
		}

		if (value.startsWith("$")) {
			buffer.append(false, value);
		} else if (value.contains(QUOTE)) {
			buffer.append(false, TRIPPLEQUOTE, value, TRIPPLEQUOTE);
		} else {
			buffer.append(false, QUOTE, value, QUOTE);
		}
		return true;
	}
}
