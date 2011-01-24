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

import java.io.IOException;

import org.tmapi.core.Name;
import org.tmapi.core.Variant;

import de.topicmapslab.ctm.writer.core.CTMTopicMapWriter;
import de.topicmapslab.ctm.writer.exception.NoIdentityException;
import de.topicmapslab.ctm.writer.exception.SerializerException;
import de.topicmapslab.ctm.writer.utility.CTMMainIdentifier;
import de.topicmapslab.ctm.writer.utility.ICTMWriter;

/**
 * Class to realize the serialization of the following CTM grammar rule. <br />
 * <br />
 * <code>name ::= '-'  (type ':')?  string scope?  reifier?  variant* </code><br />
 * <br />
 * The serialized CTM string represents the name of a topic within the topic block.
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 * 
 */
public class NameSerializer implements ISerializer<Name> {

	private static final String defaultNameIdentifier = "http://psi.topicmaps.org/iso13250/model/topic-name";
	
	/**
	 * Method to convert the given construct to its specific CTM string. The result should be written to the given
	 * output buffer.
	 * 
	 * @param writer
	 *            the CTM writer
	 * @param name
	 *            the name to serialize
	 * @param buffer
	 *            the output buffer
	 * @return <code>true</code> if new content was written into buffer, <code>false</code> otherwise
	 * @throws SerializerException
	 *             Thrown if serialization failed.
	 */
	public static boolean serialize(CTMTopicMapWriter writer, Name name, ICTMWriter buffer)
			throws SerializerException, IOException {

		/*
		 * begin name definition
		 */
		// write the tabs before the rest to omit whitespace between tabs and "-"
		buffer.append(TABULATOR);
		buffer.append(false, NAME, WHITESPACE);
		try {
			/*
			 * add type if it is not default name type of TMDM
			 */
			CTMMainIdentifier mainIdentifier = writer.getCtmIdentity().getMainIdentifier(writer.getProperties(), name.getType());
			if ((!mainIdentifier.getIdentifier().equals(defaultNameIdentifier)) && (!"tmdm:topic-name".equals(mainIdentifier.getIdentifier()))) {
				buffer.append(false, mainIdentifier.toString(), WHITESPACE, COLON, WHITESPACE);
			}
		} catch (NoIdentityException e) {
			// VOID
		}
		/*
		 * append value
		 */
		if (name.getValue().contains("\""))
			buffer.append(false, TRIPPLEQUOTE, name.getValue(), TRIPPLEQUOTE);
		else
			buffer.append(false, QUOTE, name.getValue(), QUOTE);

		/*
		 * add scope if exists
		 */
		if (ScopedSerializer.serialize(writer, name, buffer)) {
			buffer.append(WHITESPACE);
		}

		/*
		 * add reifier if exists
		 */
		if (ReifiableSerializer.serialize(writer, name, buffer)) {
			buffer.append(WHITESPACE);
		}

		/*
		 * add variants if exists
		 */
		for (Variant variant : name.getVariants()) {
			/*
			 * redirect to variant serializer
			 */
			VariantSerializer.serialize(writer, variant, buffer);
			buffer.append(WHITESPACE);
		}
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
	 *            the string representation of the type of the name
	 * @param buffer
	 *            the buffer written to
	 * @return <code>true</code> if new content was written into buffer, <code>false</code> otherwise.
	 * @throws SerializerException
	 *             Thrown if serialization failed.
	 */
	public static boolean serialize(final CTMTopicMapWriter writer, final String value, final String type,
			ICTMWriter buffer) throws SerializerException, IOException {

		buffer.append(true, TABULATOR, NAME, WHITESPACE);
		if (type != null) {
			buffer.append(false, type, COLON);
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
