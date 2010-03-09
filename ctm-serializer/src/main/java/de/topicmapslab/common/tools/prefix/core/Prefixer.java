/** 
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/    
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 *
 */
package de.topicmapslab.common.tools.prefix.core;

import java.util.Map;
import java.util.Map.Entry;

/**
 * Utility class for providing IRI prefixes.
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 * 
 */
public class Prefixer {

	/**
	 * Static method transform the given absolute URI to a relative URI by using
	 * the given prefixes.
	 * 
	 * @param uri
	 *            the absolute URI
	 * @param prefixes
	 *            a map of QName and Prefix entries
	 * @return the relative URI, if a prefix is contained in the given map,
	 *         otherwise the absolute URI will be returned.
	 */
	public static final String toPrefixedIri(final String uri,
			final Map<String, String> prefixes) {
		for (Entry<String, String> prefix : prefixes.entrySet()) {
			if (uri.startsWith(prefix.getValue())) {
				String prefixed = uri.replaceFirst(prefix.getValue(), prefix
						.getKey()
						+ ":");
				return prefixed;
			}
		}
		return uri;
	}

}
