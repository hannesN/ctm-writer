/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/    
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 *
 */
package de.topicmapslab.ctm.writer.templates;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import de.topicmapslab.ctm.writer.exception.SerializerException;
import de.topicmapslab.ctm.writer.templates.entry.base.IEntry;
import de.topicmapslab.ctm.writer.utility.CTMBuffer;

/**
 * Class representing a CTM template defintion
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 * 
 */
public class Template {

	/**
	 * the template name
	 */
	private final String templateName;

	/**
	 * the template entries
	 */
	private final List<IEntry> entries;

	/**
	 * a list of all parameters of the template
	 */
	private final List<String> variables;

	/**
	 * flag indicates if the template should be serialized
	 */
	private boolean serialize = true;
	
	private ITemplateScanner scanner;

	/**
	 * constructor
	 * 
	 * @param templateName
	 *            the template name
	 * @param entries
	 *            an array of entries
	 */
	protected Template(final String templateName, IEntry... entries) {
		this.templateName = templateName;
		this.entries = new LinkedList<IEntry>();
		this.variables = new LinkedList<String>();
		for (IEntry entry : entries) {
			add(entry);
		}
	}

	/**
	 * Method returns the internal name of the template.
	 * 
	 * @return the templateName
	 */
	public String getTemplateName() {
		return templateName;
	}

	/**
	 * Method return the internal set of entries.
	 * 
	 * @return the entries
	 */
	public List<IEntry> getEntries() {
		return entries;
	}

	/**
	 * Adding a new entry to the internal list. After adding the entry to the
	 * list, the variable of the entry will be extracted and added to the list
	 * too.
	 * 
	 * @param entry
	 *            the new entry
	 */
	public void add(final IEntry entry) {
		variables.addAll(entry.getVariables());
		this.entries.add(entry);
	}

	/**
	 * Removing an entry from the internal list. After removing the entry, the
	 * variables of the entry will be extracted and removed from variable list.
	 * 
	 * @param entry
	 *            the entry to remove
	 */
	protected void remove(final IEntry entry) {
		variables.removeAll(entry.getVariables());
		entries.remove(entry);
	}

	/**
	 * Method returns all internal parameter arguments of the template
	 * definition.
	 * 
	 * @return the variables a list of all parameters
	 */
	public List<String> getVariables() {
		return variables;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Template) {
			Template template = (Template) obj;
			for (IEntry entry : getEntries()) {
				if (!template.getEntries().contains(entry)) {
					return false;
				}
			}
			return true;
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

	/**
	 * Method checks if internal entry set only contains entries of the given
	 * type
	 * 
	 * @param clazz
	 *            the type to check
	 * @return <code>true</code> if internal entry set only contains entries of
	 *         the given type, <code>false</code> otherwise.
	 */
	public boolean containsOnlyInstanceOf(Class<? extends IEntry>... classes) {
		List<Class<? extends IEntry>> list = Arrays.asList(classes);
		for (IEntry entry : entries) {
			if (!list.contains(entry.getClass())) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Redirects to {@link TemplateSerializer#serialize(CTMBuffer)} <br />
	 * <br /> {@inheritDoc}
	 */
	@Override
	public String toString() {
		CTMBuffer buffer = new CTMBuffer();
		try {
			TemplateSerializer.serialize(this, buffer);
		} catch (SerializerException e) {
			e.printStackTrace();
		}
		return buffer.getBuffer().toString();
	}

	public static final Set<Template> fromCTM(final File file)
			throws SerializerException {
		throw new UnsupportedOperationException("not implemented yet.");
	}

	/**
	 * Checks if the template should be serialized
	 * 
	 * @return <code>true</code> if the template definition should serialize
	 *         too, <code>false</code> otherwise.
	 */
	public boolean shouldSerialize() {
		return serialize;
	}

	/**
	 * Change the serialization flag of the template
	 * 
	 * @param serialize
	 *            the serialize to set
	 */
	public void setSerialize(boolean serialize) {
		this.serialize = serialize;
	}

	/**
	 * @param scanner the scanner to set
	 */
	public void setScanner(ITemplateScanner scanner) {
		this.scanner = scanner;
	}
	
	/**
	 * @return the scanner
	 */
	public ITemplateScanner getScanner() {
		return scanner;
	}
	
}
