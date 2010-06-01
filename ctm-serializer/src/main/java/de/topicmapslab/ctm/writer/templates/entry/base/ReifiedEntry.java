package de.topicmapslab.ctm.writer.templates.entry.base;

import java.util.List;

import de.topicmapslab.ctm.writer.templates.entry.ReifierEntry;
import de.topicmapslab.ctm.writer.templates.entry.param.IEntryParam;
import de.topicmapslab.ctm.writer.templates.entry.param.VariableParam;

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
	 * @param param
	 *            the parameter
	 */
	public ReifiedEntry(IEntryParam param) {
		super(param);
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getVariables() {
		List<String> variables = super.getVariables();
		if (reifierEntry != null) {
			if (reifierEntry.getReifierParameter() instanceof VariableParam) {
				variables.add(reifierEntry.getReifierParameter()
						.getCTMRepresentation());
			}
		}
		return variables;
	}

}
