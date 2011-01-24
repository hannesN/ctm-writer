/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/    
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 *
 */
package de.topicmapslab.ctm.writer.core.serializer;

import static de.topicmapslab.ctm.writer.utility.CTMTokens.INCLUDE;
import static de.topicmapslab.ctm.writer.utility.CTMTokens.PREFIXBEGIN;
import static de.topicmapslab.ctm.writer.utility.CTMTokens.PREFIXEND;
import static de.topicmapslab.ctm.writer.utility.CTMTokens.WHITESPACE;

import java.io.IOException;
import java.util.List;

import org.tmapi.core.TopicMap;

import de.topicmapslab.ctm.writer.core.PrefixHandler;
import de.topicmapslab.ctm.writer.exception.SerializerException;
import de.topicmapslab.ctm.writer.utility.ICTMWriter;

/**
 * Class to realize the serialization of the following CTM grammar rule. <br />
 * <br />
 * <code>		include	::= '%include' iri_ref</code><br />
 * <br />
 * The serialized CTM string represents the a include definition.
 * 
 * @author Hannes Niederhausen
 * @email niederhausen@informatik.uni-leipzig.de
 * 
 */
public class IncludeSerializer implements ISerializer<TopicMap> {

	/**
	 * Method to convert the includes of the current writer instance to the CTM
	 * string.
	 * 
	 * @param includes
	 *            the includes to serialize
	 * @param prefixHandler
	 *            the prefix handler
	 * @param buffer
	 *            the output buffer
	 * @return <code>true</code> if new content was written into buffer,
	 *         <code>false</code> otherwise
	 * @throws SerializerException
	 *             Thrown if serialization failed.
	 */
	public static boolean serialize(List<String> includes,
			PrefixHandler prefixHandler, ICTMWriter buffer)
			throws SerializerException, IOException {

		boolean result = false;

		/*
		 * iterate over entries
		 */
		for (String include : includes) {
			/*
			 * add to buffer
			 */
			buffer.append(true, INCLUDE, WHITESPACE);
			if (prefixHandler.isQName(include))
				buffer.appendLine(false, include);
			else
				buffer.appendLine(false, PREFIXBEGIN, include, PREFIXEND);

			result = true;
		}
		return result;
	}

}
