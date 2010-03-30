/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/    
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 *
 */
package de.topicmapslab.ctm.writer.utility;

import java.io.UnsupportedEncodingException;

import de.topicmapslab.common.tools.URIEncoder;
import de.topicmapslab.ctm.writer.core.PrefixHandler;

/**
 * Class which stores the main identifier of a topic and its type.
 * 
 * @author Hannes Niederhausen
 * 
 */
public class CTMMainIdentifier {

	/**
	 * an enumeration containing all identifier types
	 * 
	 */
	enum IdentifierType {
		SUBJECT_IDENTIFIER, SUBJECT_LOCATOR, ITEM_IDENTIFIER
	}

	/**
	 * the string literal of the identifier
	 */
	private String identifier;

	/**
	 * the type of the identifier
	 */
	private IdentifierType type;

	/**
	 * the prefix handler of the topic map writer
	 */
	private final PrefixHandler prefixHandler;

	/**
	 * constructor
	 * 
	 * @param prefixHandler
	 *            the prefix handler
	 * @param identifier
	 *            the string literal of the identifier
	 * @param type
	 *            the type of the identifier
	 */
	public CTMMainIdentifier(final PrefixHandler prefixHandler,
			String identifier, IdentifierType type) {
		this.identifier = identifier;
		this.type = type;
		this.prefixHandler = prefixHandler;
	}

	/**
	 * Get the string literal of the identifier
	 * 
	 * @return the string literal
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * Set the string literal of the identifier
	 * 
	 * @param identifier
	 *            the string literal
	 */
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	/**
	 * Get the type of the identifier
	 * 
	 * @return the type
	 */
	public IdentifierType getType() {
		return type;
	}

	/**
	 * Set the internal type of this identifier.
	 * @param type the identifier
	 */
	public void setType(IdentifierType type) {
		this.type = type;
	}

	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		StringBuilder b = new StringBuilder();
		switch (type) {
		case ITEM_IDENTIFIER:
			b.append("^");
			break;
		case SUBJECT_IDENTIFIER:
			break;
		case SUBJECT_LOCATOR:
			b.append("=");
			break;
		}
		
		String id = identifier;
						
		try {
			id = URIEncoder.encodeURI(id, "UTF-8");
		} catch (UnsupportedEncodingException e) {			
			e.printStackTrace();
		}

		if (id.contains(":") && !id.contains("%") && !id.contains(",")) {
			int idx = id.indexOf(':');
			String prefix = id.substring(0, idx);
			if (prefixHandler.isKnownNamespace(prefix)) {
				b.append(id);
				return b.toString();
			}
		}
		b.append(CTMTokens.PREFIXBEGIN);
		b.append(id);
		b.append(CTMTokens.PREFIXEND);

		return b.toString();
	}

}
