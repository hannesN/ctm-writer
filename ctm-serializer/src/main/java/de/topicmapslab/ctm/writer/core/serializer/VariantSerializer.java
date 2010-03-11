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

import de.topicmapslab.ctm.writer.exception.SerializerException;
import de.topicmapslab.ctm.writer.properties.CTMTopicMapWriterProperties;
import de.topicmapslab.ctm.writer.utility.CTMBuffer;
import de.topicmapslab.ctm.writer.utility.CTMIdentity;

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
	public VariantSerializer(CTMTopicMapWriterProperties properties, CTMIdentity ctmIdentity) {
		this.properties = properties;
		this.ctmIdentity = ctmIdentity;
	}

	public boolean serialize(Variant variant, CTMBuffer buffer)
			throws SerializerException {

		buffer.append(BRO);
		/*
		 * add value and data-type
		 */
		new DatatypeAwareSerializer(properties, ctmIdentity).serialize(variant, buffer);

		/*
		 * add scope if exists
		 */
		CTMBuffer ctmBuffer = new CTMBuffer();
		if (new ScopedSerializer(properties, ctmIdentity).serialize(variant, ctmBuffer)) {
			buffer.append(WHITESPACE);
			buffer.append(ctmBuffer);
		}

		/*
		 * add reifier if exists
		 */
		ctmBuffer = new CTMBuffer();
		if (new ReifiableSerializer(properties, ctmIdentity).serialize(variant, ctmBuffer)) {
			buffer.append(WHITESPACE);
			buffer.append(ctmBuffer);
		}

		buffer.append(BRC);
		return true;
	}

}
