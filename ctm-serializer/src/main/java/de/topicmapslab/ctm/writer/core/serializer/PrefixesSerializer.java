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

import java.util.Map;
import java.util.Map.Entry;

import org.tmapi.core.TopicMap;

import de.topicmapslab.common.tools.prefix.core.PrefixIdentifier;
import de.topicmapslab.ctm.writer.core.PrefixHandler;
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
public class PrefixesSerializer implements ISerializer<TopicMap> {

	/**
	 * the prefix handler
	 */
	private final PrefixHandler prefixHandler;

	/**
	 * constructor
	 * 
	 * @param prefixHandler
	 *            the prefix handler of the CTM writer
	 */
	public PrefixesSerializer(PrefixHandler prefixHandler) {
		this.prefixHandler = prefixHandler;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean serialize(TopicMap topicMap, CTMBuffer buffer)
			throws SerializerException {

		boolean result = false;
		/*
		 * try to identify all prefixes of the topic map
		 */
		Map<String, String> prefixes = PrefixIdentifier.prefixMap(topicMap);

		/*
		 * iterate over entries
		 */
		for (Entry<String, String> prefix : prefixes.entrySet()) {
			/*
			 * add to buffer
			 */
			buffer.append(true, PREFIX, prefix.getKey(), WHITESPACE);
			buffer.appendLine(false, PREFIXBEGIN, prefix.getValue(), PREFIXEND);
			/*
			 * store known prefix
			 */
			prefixHandler.setPrefix(prefix.getKey(), prefix.getValue());
			result = true;
		}
		return result;
	}

}
