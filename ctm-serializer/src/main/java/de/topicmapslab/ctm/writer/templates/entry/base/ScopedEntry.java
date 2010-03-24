package de.topicmapslab.ctm.writer.templates.entry.base;

import java.util.List;

import de.topicmapslab.ctm.writer.templates.entry.ScopeEntry;
import de.topicmapslab.ctm.writer.templates.entry.param.IEntryParam;

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
	 * @param param
	 *            the parameter
	 */
	public ScopedEntry(IEntryParam param) {
		super(param);
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getVariables() {
		List<String> variables = super.getVariables();
		if (scopeEntry != null) {
			variables.addAll(scopeEntry.getVariables());
		}
		return variables;
	}

}
