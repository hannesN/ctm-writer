package de.topicmapslab.ctm.writer.templates.entry.base;

import de.topicmapslab.ctm.writer.templates.entry.ScopeEntry;

/**
 * Abstract class of a template entry which can contain a scope entry.
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 */
public abstract class ScopedEntry extends ReifiedEntry {

	/**
	 * the scopeEntry entry
	 */
	private ScopeEntry scopeEntry = null;

	/**
	 * constructor
	 * 
	 * @param the
	 *            value or variable definition of the template-entry
	 */
	public ScopedEntry(String valueOrVariable) {
		super(valueOrVariable);
	}

	/**
	 * Change the value of the {@link ScopeEntry}
	 * 
	 * @param scopeEntry
	 *            the new entry or <code>null</code>
	 */
	public void setScopeEntry(ScopeEntry scopeEntry) {
		this.scopeEntry = scopeEntry;
	}

	/**
	 * Returns the value of the internal entry
	 * 
	 * @return a reference of the internally stored entry
	 */
	public ScopeEntry getScopeEntry() {
		return scopeEntry;
	}

}
