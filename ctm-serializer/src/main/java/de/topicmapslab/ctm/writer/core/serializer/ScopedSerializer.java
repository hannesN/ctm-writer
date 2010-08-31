/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/    
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 *
 */
package de.topicmapslab.ctm.writer.core.serializer;

import static de.topicmapslab.ctm.writer.utility.CTMTokens.COMMA;
import static de.topicmapslab.ctm.writer.utility.CTMTokens.SCOPE;
import static de.topicmapslab.ctm.writer.utility.CTMTokens.WHITESPACE;

import org.tmapi.core.Scoped;
import org.tmapi.core.Topic;

import de.topicmapslab.ctm.writer.core.CTMTopicMapWriter;
import de.topicmapslab.ctm.writer.exception.SerializerException;
import de.topicmapslab.ctm.writer.utility.CTMBuffer;

/**
 * Class to realize the serialization of the following CTM grammar rule. <br />
 * <br />
 * <code>	scope ::= '@' WS*  topic-ref  (',' topic-ref)*   </code><br />
 * <br />
 * The serialized CTM string represents the a scope definition.
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 * 
 */
public class ScopedSerializer implements ISerializer<Scoped> {

	/**
	 * Method to convert the given construct to its specific CTM string. The
	 * result should be written to the given output buffer.
	 * 
	 * @param writer
	 *            the CTM writer
	 * @param scoped
	 *            the scoped item to serialize
	 * @param buffer
	 *            the output buffer
	 * @return <code>true</code> if new content was written into buffer,
	 *         <code>false</code> otherwise
	 * @throws SerializerException
	 *             Thrown if serialization failed.
	 */
	public static boolean serialize(CTMTopicMapWriter writer, Scoped scoped,
			CTMBuffer buffer) throws SerializerException {
		if (!scoped.getScope().isEmpty()) {
			boolean first = true;
			for (Topic theme : scoped.getScope()) {
				if (first) {
					buffer.append(SCOPE);
					first = false;
				} else {
					buffer.append(true, COMMA, WHITESPACE);
				}
				buffer.append(writer.getCtmIdentity().getMainIdentifier(
						writer.getProperties(), theme).toString());
			}
			return true;
		}
		return false;
	}

}
