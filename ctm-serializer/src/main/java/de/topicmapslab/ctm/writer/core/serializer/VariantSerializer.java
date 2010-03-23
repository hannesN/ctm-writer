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
	 * the parent topic map writer
	 */
	private final CTMTopicMapWriter writer;

	/**
	 * constructor
	 * 
	 * @param writer
	 *            the parent topic map writer
	 */
	public VariantSerializer(CTMTopicMapWriter writer) {
		this.writer = writer;
	}

	public boolean serialize(Variant variant, CTMBuffer buffer)
			throws SerializerException {

		buffer.append(BRO);
		/*
		 * add value and data-type
		 */
		new DatatypeAwareSerializer(writer).serialize(variant, buffer);

		/*
		 * add scope if exists
		 */
		CTMBuffer ctmBuffer = new CTMBuffer();
		if (new ScopedSerializer(writer).serialize(variant, ctmBuffer)) {
			buffer.append(WHITESPACE);
			buffer.append(ctmBuffer);
		}

		/*
		 * add reifier if exists
		 */
		ctmBuffer = new CTMBuffer();
		if (new ReifiableSerializer(writer).serialize(variant,
				ctmBuffer)) {
			buffer.append(WHITESPACE);
			buffer.append(ctmBuffer);
		}

		buffer.append(BRC);
		return true;
	}

}
