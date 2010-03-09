/** 
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/    
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 *
 */
package de.topicmapslab.common.tools.prefix.core;

import java.util.LinkedList;
import java.util.List;

import org.tmapi.core.Locator;

/**
 * 
 * Tokenizer class to extract all possible prefixes from a given IRI. The IRI
 * http://www.topicmapslab.de/tools/ctm#this.xtm will be split to {
 * http://www.topicmapslab.de/, http://www.topicmapslab.de/tools/,
 * http://www.topicmapslab.de/tools/ctm }.
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 * 
 */
public class PrefixerTokenizer {

	private final List<String> tokens;
	private final String iri;

	/**
	 * constructor, which is calling
	 * {@link PrefixerTokenizer#PrefixerTokenizer(String)} with the argument
	 * {@link Locator#toExternalForm()}.
	 * 
	 * @param locator
	 *            the locator of a topic map construct
	 */
	public PrefixerTokenizer(Locator locator) {
		this(locator.toExternalForm());
	}

	/**
	 * construct
	 * 
	 * @param iri
	 *            the IRI to split
	 */
	public PrefixerTokenizer(String iri) {
		tokens = new LinkedList<String>();
		this.iri = iri;
		tokenize();
	}

	/**
	 * internal tokenizer method
	 */
	private void tokenize() {
		StringBuffer buffer = new StringBuffer();

		/*
		 * extract first char
		 */
		char lastChar = iri.charAt(0);
		buffer.append(lastChar);
		/*
		 * iterate over all characters
		 */
		for (int index = 1; index < iri.length(); index++) {
			char c = iri.charAt(index);
			buffer.append(c);
			/*
			 * check if slash / contains to the protocol part
			 */
			if (c == '/') {
				/*
				 * is protocol
				 */
				if (lastChar != '/' && index < iri.length() - 1
						&& iri.charAt(index + 1) != '/') {
					tokens.add(buffer.toString());
				}
			} else if (c == '#') {
				if (lastChar != '/') {
					tokens.add(buffer.toString());
				}
			}
			lastChar = c;
		}
	}

	/**
	 * Method provides access to the internal token list.
	 * 
	 * @return the tokens
	 */
	public List<String> getTokens() {
		return tokens;
	}

}
