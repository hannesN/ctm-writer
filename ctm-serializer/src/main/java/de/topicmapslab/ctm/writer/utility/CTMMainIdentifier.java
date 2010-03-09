/**
 * 
 */
package de.topicmapslab.ctm.writer.utility;

import de.topicmapslab.ctm.writer.core.serializer.PrefixesSerializer;

/**
 * Class which stores the main identifier of a topic and its type.
 * 
 * @author Hannes Niederhausen
 * 
 */
public class CTMMainIdentifier {

	enum IdentifierType {
		SUBJECT_IDENTIFIER, SUBJECT_LOCATOR, ITEM_IDENTIFIER
	}

	private String identifier;

	private IdentifierType type;

	public CTMMainIdentifier(String identifier, IdentifierType type) {
		this.identifier = identifier;
		this.type = type;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public IdentifierType getType() {
		return type;
	}

	public void setType(IdentifierType type) {
		this.type = type;
	}

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

		if (identifier.contains(":")) {
			int idx = identifier.indexOf(':');
			String prefix = identifier.substring(0, idx);
			if (PrefixesSerializer.knownPrefixes.containsKey(prefix)) {
				b.append(identifier);
				return b.toString();
			}
		}
		b.append(CTMTokens.PREFIXBEGIN);
		b.append(identifier);
		b.append(CTMTokens.PREFIXEND);

		return b.toString();
	}

}
