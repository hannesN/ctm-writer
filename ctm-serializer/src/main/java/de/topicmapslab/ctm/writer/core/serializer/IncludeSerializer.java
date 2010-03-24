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

import java.util.List;

import org.tmapi.core.TopicMap;

import de.topicmapslab.ctm.writer.core.PrefixHandler;
import de.topicmapslab.ctm.writer.exception.SerializerException;
import de.topicmapslab.ctm.writer.utility.CTMBuffer;

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
	 * the prefix handler
	 */
	private final List<String> includes;
	
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
	public IncludeSerializer(List<String> includes, PrefixHandler prefixHandler) {
		this.includes = includes;
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
