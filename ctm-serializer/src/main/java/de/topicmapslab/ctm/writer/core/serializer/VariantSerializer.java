/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/    
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 *
 */
package de.topicmapslab.ctm.writer.core.serializer;

import static de.topicmapslab.ctm.writer.utility.CTMTokens.BRC;
import static de.topicmapslab.ctm.writer.utility.CTMTokens.BRO;
import static de.topicmapslab.ctm.writer.utility.CTMTokens.WHITESPACE;

import org.tmapi.core.Variant;

import de.topicmapslab.ctm.writer.core.CTMTopicMapWriter;
import de.topicmapslab.ctm.writer.exception.SerializerException;
import de.topicmapslab.ctm.writer.utility.CTMBuffer;

/**
 * Class to realize the serialization of the following CTM grammar rule. <br />
 * <br />
 * <code>	prefix-directive ::= '%prefix' identifier reference  </code><br />
 * <br />
 * The serialized CTM string represents the a prefix definition.
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 * 
 */
public class VariantSerializer implements ISerializer<Variant> {

	/**
	 * Method to convert the given construct to its specific CTM string. The
	 * result should be written to the given output buffer.
	 * 
	 * @param writer
	 *            the CTM writer
	 * @param variant
	 *            the variant to serialize
	 * @param buffer
	 *            the output buffer
	 * @return <code>true</code> if new content was written into buffer,
	 *         <code>false</code> otherwise
	 * @throws SerializerException
	 *             Thrown if serialization failed.
	 */
	public static boolean serialize(CTMTopicMapWriter writer, Variant variant,
			CTMBuffer buffer) throws SerializerException {

		buffer.append(BRO);
		/*
		 * add value and data-type
		 */
		DatatypeAwareSerializer.serialize(writer, variant, buffer);

		/*
		 * add scope if exists
		 */
		CTMBuffer ctmBuffer = new CTMBuffer();
		if (ScopedSerializer.serialize(writer, variant, ctmBuffer)) {
			buffer.append(WHITESPACE);
			buffer.append(ctmBuffer);
		}

		/*
		 * add reifier if exists
		 */
		ctmBuffer = new CTMBuffer();
		if (ReifiableSerializer.serialize(writer, variant, ctmBuffer)) {
			buffer.append(WHITESPACE);
			buffer.append(ctmBuffer);
		}

		buffer.append(BRC);
		return true;
	}

	/**
	 * Static method to generate CTM occurrence-block by value and data-type.
	 * 
	 * @param writer
	 *            the parent topic map writer
	 * @param value
	 *            the value of the variant
	 * @param datatype
	 *            the data-type of the variant
	 * @param buffer
	 *            the buffer written to
	 * @return <code>true</code> if new content was written into buffer,
	 *         <code>false</code> otherwise.
	 * @throws SerializerException
	 *             Thrown if serialization failed.
	 */
	public static boolean serialize(CTMTopicMapWriter writer, String value,
			Object datatype, CTMBuffer buffer) throws SerializerException {
		/*
		 * add value and data-type
		 */
		DatatypeAwareSerializer.serialize(writer, datatype, value, buffer);

		return true;
	}

}
