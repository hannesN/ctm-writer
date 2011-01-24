/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/    
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 *
 */
package de.topicmapslab.ctm.writer.core.serializer;

import static de.topicmapslab.ctm.writer.utility.CTMTokens.MERGEMAP;
import static de.topicmapslab.ctm.writer.utility.CTMTokens.PREFIXBEGIN;
import static de.topicmapslab.ctm.writer.utility.CTMTokens.PREFIXEND;
import static de.topicmapslab.ctm.writer.utility.CTMTokens.WHITESPACE;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import org.tmapi.core.TopicMap;

import de.topicmapslab.ctm.writer.core.PrefixHandler;
import de.topicmapslab.ctm.writer.exception.SerializerException;
import de.topicmapslab.ctm.writer.utility.ICTMWriter;

/**
 * Class to realize the serialization of the following CTM grammar rule. <br />
 * <br />
 * <code>		mergemap	::= '%mergemap' iri_ref notation</code><br />
 * <br />
 * The serialized CTM string represents the a mergemap definition.
 * 
 * @author Hannes Niederhausen
 * @email niederhausen@informatik.uni-leipzig.de
 * 
 */
public class MergeMapSerializer implements ISerializer<TopicMap> {

	/**
	 * Export the merge map items of the current writer instance to the CTM
	 * file.
	 * 
	 * @param mergeMap
	 *            the merge map
	 * @param prefixHandler
	 *            the prefix handler
	 * @param buffer
	 *            the CTM buffer
	 * @return <code>true</code> if the merge map was export correctly,
	 *         <code>false</code> otherwise.
	 * @throws SerializerException
	 *             thrown if serialization failed
	 */
	public static boolean serialize(Map<String, String> mergeMap,
			PrefixHandler prefixHandler, ICTMWriter buffer)
			throws SerializerException, IOException {

		boolean result = false;

		/*
		 * iterate over entries
		 */
		for (Entry<String, String> e : mergeMap.entrySet()) {
			/*
			 * add to buffer
			 */
			buffer.append(true, MERGEMAP, WHITESPACE);
			if (prefixHandler.isQName(e.getKey()))
				buffer.append(false, e.getKey(), WHITESPACE);
			else
				buffer.append(false, PREFIXBEGIN, e.getKey(), PREFIXEND,
						WHITESPACE);

			buffer.appendLine(e.getValue());

			result = true;
		}
		return result;
	}

}
