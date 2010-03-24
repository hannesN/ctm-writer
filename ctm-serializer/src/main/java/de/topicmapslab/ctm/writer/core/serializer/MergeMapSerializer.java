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

import java.util.Map;
import java.util.Map.Entry;

import org.tmapi.core.TopicMap;

import de.topicmapslab.ctm.writer.core.PrefixHandler;
import de.topicmapslab.ctm.writer.exception.SerializerException;
import de.topicmapslab.ctm.writer.utility.CTMBuffer;

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
	 * the prefix handler
	 */
	private final Map<String, String> mergeMap;
	
	/**
	 * the prefix handler to check for qnames 
	 */
	private final PrefixHandler prefixHandler;

	/**
	 * constructor
	 * 
	 * @param includes list of iri which should be included
	 * @param prefixHandler the prefixhandler of the writer
	 * 
	 * @param prefixHandler
	 *            the prefix handler of the CTM writer
	 */
	public MergeMapSerializer(Map<String, String> mergeMap, PrefixHandler prefixHandler) {
		this.mergeMap = mergeMap;
		this.prefixHandler = prefixHandler;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean serialize(TopicMap topicMap, CTMBuffer buffer)
			throws SerializerException {

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
				buffer.append(false, PREFIXBEGIN, e.getKey(), PREFIXEND, WHITESPACE);

			buffer.appendLine(e.getValue());
			
			result = true;
		}
		return result;
	}

}
