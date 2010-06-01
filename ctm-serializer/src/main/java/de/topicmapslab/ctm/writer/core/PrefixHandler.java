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
	 * @param prefix
	 *            the prefix
	 * @return the IRI or <code>null</code> if no prefix is registered
	 *         for given prefix identififer
	 */
	public String getPrefix(final String prefix) {
		return prefixMap.get(prefix);
	}

	/**
	 * Register a new prefix definition.
	 * 
	 * A prefix definition in CTM is: %prefix key IRI
	 * 
	 * @param prefix
	 *            the prefix
	 * @param iri
	 *            the IRI
	 */
	public void setPrefix(final String prefix, final String iri) {
		this.prefixMap.put(prefix, iri);
	}

	/**
	 * Check if the given prefix is contained by the internal prefix map.
	 * 
	 * @param prefix
	 *            the prefix
	 * @return <code>true</code> if a prefix stored for the prefix identifier,
	 *         <code>false</code> otherwise.
	 */
	public boolean isKnownPrefix(final String prefix) {
		return prefixMap.containsKey(prefix);
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
