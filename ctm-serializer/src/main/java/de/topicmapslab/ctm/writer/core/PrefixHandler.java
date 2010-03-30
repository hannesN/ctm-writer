/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/    
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 *
 */
package de.topicmapslab.ctm.writer.core;

import java.util.HashMap;
import java.util.Map;

/**
 * Class store all registered prefixes.
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 * 
 */
public class PrefixHandler {

	/**
	 * the prefix map
	 */
	private final Map<String, String> prefixMap = new HashMap<String, String>();

	/**
	 * Get the prefix IRI for the given name-space identifier.
	 * 
	 * @param namespace
	 *            the identifier
	 * @return the prefix IRI or <code>null</code> if no prefix is registered
	 *         for given name-space
	 */
	public String getPrefix(final String namespace) {
		return prefixMap.get(namespace);
	}

	/**
	 * Register a new prefix definition.
	 * 
	 * @param namespace
	 *            the name-space
	 * @param prefix
	 *            the prefix IRI
	 */
	public void setPrefix(final String namespace, final String prefix) {
		this.prefixMap.put(namespace, prefix);
	}

	/**
	 * Check if the given name-space is contained by the internal prefix map.
	 * 
	 * @param namespace
	 *            the name-space
	 * @return <code>true</code> if a prefix stored for the name-space,
	 *         <code>false</code> otherwise.
	 */
	public boolean isKnownNamespace(final String namespace) {
		return prefixMap.containsKey(namespace);
	}

	/**
	 * Get all registered prefix with their corresponding name-spaces.
	 * 
	 * @return a map containing all prefixes
	 */
	public Map<String, String> getPrefixMap() {
		return prefixMap;
	}

	/**
	 * Checks if the given iri is a qName using one of the specified prefixes.
	 * 
	 * @param iri the iri to check
	 * 
	 * @return <code>true</code> of iri is a qname
	 */
	public boolean isQName(String iri) {
		int idx = iri.indexOf(":");
		if (idx==-1)
			return false;
		
		String prefix = iri.substring(0, idx);
		
		return getPrefixMap().containsKey(prefix);
	}
}
