/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/    
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 *
 */
package de.topicmapslab.ctm.writer.core.serializer;

import static de.topicmapslab.ctm.writer.utility.CTMTokens.PREFIX;
import static de.topicmapslab.ctm.writer.utility.CTMTokens.PREFIXBEGIN;
import static de.topicmapslab.ctm.writer.utility.CTMTokens.PREFIXEND;
import static de.topicmapslab.ctm.writer.utility.CTMTokens.WHITESPACE;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import org.tmapi.core.TopicMap;

import de.topicmapslab.common.tools.prefix.core.PrefixIdentifier;
import de.topicmapslab.ctm.writer.core.PrefixHandler;
import de.topicmapslab.ctm.writer.exception.SerializerException;
import de.topicmapslab.ctm.writer.utility.CTMStreamWriter;

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
public class PrefixesSerializer implements ISerializer<TopicMap> {

	/**
	 * Export the prefix definitions of the current writer instance.
	 * 
	 * @param prefixHandler
	 *            the prefix handler
	 * @param autoDetect
	 *            flag indicates if the prefixes should detect automatically
	 * @param topicMap
	 *            the topic map
	 * @param buffer
	 *            the CTM buffer
	 * @return <code>true</code> if the prefixes were exported correctly,
	 *         <code>false</code> otherwise.
	 * @throws SerializerException
	 *             thrown if operation fails
	 */
	public static boolean serialize(PrefixHandler prefixHandler,
			boolean autoDetect, TopicMap topicMap, CTMStreamWriter buffer)
			throws SerializerException, IOException {

		boolean result = false;
		/*
		 * try to identify all prefixes of the topic map
		 */
		if (autoDetect) {
			Map<String, String> prefixes = PrefixIdentifier.prefixMap(topicMap);

			prefixHandler.getPrefixMap().putAll(prefixes);
		}
		/*
		 * iterate over entries
		 */
		for (Entry<String, String> prefix : prefixHandler.getPrefixMap()
				.entrySet()) {
			/*
			 * add to buffer
			 */
			buffer.append(true, PREFIX, prefix.getKey(), WHITESPACE);
			buffer.appendLine(false, PREFIXBEGIN, prefix.getValue(), PREFIXEND);

			result = true;
		}
		return result;
	}

}
