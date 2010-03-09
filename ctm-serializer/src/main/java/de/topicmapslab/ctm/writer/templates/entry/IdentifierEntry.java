/**
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/    
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 *
 */
package de.topicmapslab.ctm.writer.templates.entry;

import static de.topicmapslab.ctm.writer.utility.CTMTokens.TABULATOR;
import de.topicmapslab.ctm.writer.exception.SerializerException;
import de.topicmapslab.ctm.writer.templates.entry.base.EntryImpl;
import de.topicmapslab.ctm.writer.utility.CTMBuffer;
import de.topicmapslab.ctm.writer.utility.CTMIdentity;

/**
 * Abstract class representing a template-entry definition of identifier-entry.
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 * 
 */
public abstract class IdentifierEntry extends EntryImpl {

	/**
	 * identity utility (cache and generator)
	 */
	protected final CTMIdentity ctmIdentity;
	
	/**
	 * constructor
	 * 
	 * @param valueOrVariable
	 *            the value or variable definition of the template-entry
	 */
	public IdentifierEntry(String valueOrVariable, CTMIdentity ctmIdentity) {
		super(valueOrVariable);
		this.ctmIdentity = ctmIdentity;
	}

	/**
	 * {@inheritDoc}
	 */
	public void serialize(CTMBuffer buffer) throws SerializerException {
		buffer.appendTailLine(true, TABULATOR, getPrefix(),
				getValueOrVariable());
	}

	/**
	 * Method returns the optional CTM token used to identify the type of
	 * identifier.
	 * 
	 * @return the CTM token
	 */
	protected abstract String getPrefix();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof IdentifierEntry) {
			return super.equals(obj)
					&& getPrefix().equalsIgnoreCase(
							((IdentifierEntry) obj).getPrefix());
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
