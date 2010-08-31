package de.topicmapslab.ctm.writer.templates;

import de.topicmapslab.ctm.writer.core.CTMTopicMapWriter;
import de.topicmapslab.ctm.writer.templates.entry.EntryFactory;
import de.topicmapslab.ctm.writer.templates.entry.base.IEntry;

/**
 * Factory class to create new templates for CTM file.
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 */
public class TemplateFactory {

	/**
	 * internal reference of the entry factory
	 */
	private final EntryFactory factory;

	/**
	 * constructor
	 * 
	 * @param writer
	 *            the parent topic map writer
	 */
	public TemplateFactory(CTMTopicMapWriter writer) {
		factory = new EntryFactory(writer);
	}

	/**
	 * Creates a mew template with the given name and add the given entries to
	 * this new instance.
	 * 
	 * @param templateName
	 *            the template name
	 * @param entries
	 *            the entries
	 * @return the new instance
	 */
	public Template newTemplate(final String templateName, IEntry... entries) {
		return new Template(templateName, entries);
	}

	/**
	 * Returns the internal reference of the entry factory to create new
	 * template entries.
	 * 
	 * @return the internal reference
	 */
	public EntryFactory getEntryFactory() {
		return factory;
	}

}
