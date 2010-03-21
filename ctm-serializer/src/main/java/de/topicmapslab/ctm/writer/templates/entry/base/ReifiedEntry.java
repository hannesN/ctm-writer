package de.topicmapslab.ctm.writer.templates.entry.base;

import de.topicmapslab.ctm.writer.templates.entry.ReifierEntry;

/**
 * Abstract class of template entries which can contain a reification entry.
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 */
public abstract class ReifiedEntry extends EntryImpl {

	/**
	 * the reifier entry
	 */
	private ReifierEntry reifierEntry = null;

	/**
	 * constructor
	 * 
	 * @param the
	 *            value or variable definition of the template-entry
	 */
	public ReifiedEntry(String valueOrVariable) {
		super(valueOrVariable);
	}

	/**
	 * Change the value of the {@link ReifierEntry}
	 * 
	 * @param reifierEntry
	 *            the new entry or <code>null</code>
	 */
	public void setReifierEntry(ReifierEntry reifierEntry) {
		this.reifierEntry = reifierEntry;
	}

	/**
	 * Returns the value of the internal entry
	 * 
	 * @return a reference of the internally stored entry
	 */
	public ReifierEntry getReifierEntry() {
		return reifierEntry;
	}

}
