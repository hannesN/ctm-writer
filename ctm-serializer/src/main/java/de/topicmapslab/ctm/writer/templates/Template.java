/**
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/    
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 *
 */
package de.topicmapslab.ctm.writer.templates;

import static de.topicmapslab.ctm.writer.utility.CTMTokens.TOPICVARIABLE;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.tmapi.core.Construct;

import de.topicmapslab.ctm.writer.exception.SerializerException;
import de.topicmapslab.ctm.writer.templates.entry.AssociationEntry;
import de.topicmapslab.ctm.writer.templates.entry.NameEntry;
import de.topicmapslab.ctm.writer.templates.entry.OccurrenceEntry;
import de.topicmapslab.ctm.writer.templates.entry.TemplateEntry;
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
	 * construct calling {@link Template#Template(String, List)} with a new
	 * {@link LinkedList}
	 * 
	 * @param templateName
	 *            the template name
	 */
	public Template(final String templateName) {
		this(templateName, new LinkedList<IEntry>());
	}

	/**
	 * constructor
	 * 
	 * @param templateName
	 *            the template name
	 * @param entries
	 *            a list of entries
	 */
	public Template(final String templateName, List<IEntry> entries) {
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
		/*
		 * is multiple-entry of type association-entry
		 */
		if (entry instanceof AssociationEntry) {
			/*
			 * iterate over variables or values
			 */
			for (String variable : ((AssociationEntry) entry)
					.getValueOrVariables()) {
				/*
				 * check if value is a variable and is not the default topic
				 * argument
				 */
				if (variable.startsWith("$")
						&& !variable.equalsIgnoreCase(TOPICVARIABLE)) {
					variables.add(variable);
				}
			}
		}
		/*
		 * is multiple-entry of type template-entry
		 */
		else if (entry instanceof TemplateEntry) {
			/*
			 * iterate over variables or values
			 */
			for (String variable : ((TemplateEntry) entry)
					.getValuesOrVariables()) {
				/*
				 * check if value is a variable and is not the default topic
				 * argument
				 */
				if (variable.startsWith("$")
						&& !variable.equalsIgnoreCase(TOPICVARIABLE)) {
					variables.add(variable);
				}
			}
		}
		/*
		 * is name-entry
		 */
		else if (entry instanceof NameEntry) {
			String valueOrVariable = entry.getValueOrVariable();
			if (valueOrVariable != null && valueOrVariable.startsWith("$")
					&& !valueOrVariable.equalsIgnoreCase(TOPICVARIABLE)) {
				variables.add(valueOrVariable);
			}

			NameEntry nameEntry = (NameEntry) entry;
			/*
			 * check if name entry contains a variable dependent reifier entry
			 */
			if (nameEntry.getReifierEntry() != null
					&& nameEntry.getReifierEntry().getReifierOrVariable()
							.toString().startsWith("$")) {
				variables.add(nameEntry.getReifierEntry()
						.getReifierOrVariable().toString());
			}
			/*
			 * check if name entry contains a scope entry
			 */
			if (nameEntry.getScopeEntry() != null) {
				for (String variable : nameEntry.getScopeEntry().getVariables()) {
					variables.add(variable);
				}
			}
		}
		/*
		 * is occurrence-entry
		 */
		else if (entry instanceof OccurrenceEntry) {
			String valueOrVariable = entry.getValueOrVariable();
			if (valueOrVariable != null && valueOrVariable.startsWith("$")
					&& !valueOrVariable.equalsIgnoreCase(TOPICVARIABLE)) {
				variables.add(valueOrVariable);
			}

			OccurrenceEntry occurrenceEntry = (OccurrenceEntry) entry;
			/*
			 * check if occurrence entry contains a variable dependent reifier
			 * entry
			 */
			if (occurrenceEntry.getReifierEntry() != null
					&& occurrenceEntry.getReifierEntry().getReifierOrVariable()
							.toString().startsWith("$")) {
				variables.add(occurrenceEntry.getReifierEntry()
						.getReifierOrVariable().toString());
			}
			/*
			 * check if occurrence entry contains a scope entry
			 */
			if (occurrenceEntry.getScopeEntry() != null) {
				for (String variable : occurrenceEntry.getScopeEntry()
						.getVariables()) {
					variables.add(variable);
				}
			}
		}
		/*
		 * is simple-entry
		 */
		else {
			final String valueOrVariable = entry.getValueOrVariable();
			/*
			 * check if value exists, is a variable and is not the default topic
			 * argument
			 */
			if (valueOrVariable != null && valueOrVariable.startsWith("$")
					&& !valueOrVariable.equalsIgnoreCase(TOPICVARIABLE)) {
				variables.add(valueOrVariable);
			}

		}

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
		/*
		 * is multiple-entry of type association-entry
		 */
		if (entry instanceof AssociationEntry) {
			/*
			 * iterate over variables or values
			 */
			for (String variable : ((AssociationEntry) entry)
					.getValueOrVariables()) {
				/*
				 * check if value is a variable
				 */
				if (variable.startsWith("$")) {
					variables.remove(variable);
				}
			}
		}
		/*
		 * is multiple-entry of type association-entry
		 */
		else if (entry instanceof TemplateEntry) {
			/*
			 * iterate over variables or values
			 */
			for (String variable : ((TemplateEntry) entry)
					.getValuesOrVariables()) {
				/*
				 * check if value is a variable
				 */
				if (variable.startsWith("$")) {
					variables.remove(variable);
				}
			}
		}
		/*
		 * is name-entry
		 */
		else if (entry instanceof NameEntry) {
			String valueOrVariable = entry.getValueOrVariable();
			if (valueOrVariable != null && valueOrVariable.startsWith("$")
					&& !valueOrVariable.equalsIgnoreCase(TOPICVARIABLE)) {
				variables.remove(valueOrVariable);
			}

			NameEntry nameEntry = (NameEntry) entry;
			/*
			 * check if name entry contains a variable dependent reifier entry
			 */
			if (nameEntry.getReifierEntry() != null
					&& nameEntry.getReifierEntry().getReifierOrVariable()
							.toString().startsWith("$")) {
				variables.remove(nameEntry.getReifierEntry()
						.getReifierOrVariable().toString());
			}
			/*
			 * check if name entry contains a scope entry
			 */
			if (nameEntry.getScopeEntry() != null) {
				for (String variable : nameEntry.getScopeEntry().getVariables()) {
					variables.remove(variable);
				}
			}
		}
		/*
		 * is occurrence-entry
		 */
		else if (entry instanceof OccurrenceEntry) {
			String valueOrVariable = entry.getValueOrVariable();
			if (valueOrVariable != null && valueOrVariable.startsWith("$")
					&& !valueOrVariable.equalsIgnoreCase(TOPICVARIABLE)) {
				variables.remove(valueOrVariable);
			}

			OccurrenceEntry occurrenceEntry = (OccurrenceEntry) entry;
			/*
			 * check if occurrence entry contains a variable dependent reifier
			 * entry
			 */
			if (occurrenceEntry.getReifierEntry() != null
					&& occurrenceEntry.getReifierEntry().getReifierOrVariable()
							.toString().startsWith("$")) {
				variables.remove(occurrenceEntry.getReifierEntry()
						.getReifierOrVariable().toString());
			}
			/*
			 * check if occurrence entry contains a scope entry
			 */
			if (occurrenceEntry.getScopeEntry() != null) {
				for (String variable : occurrenceEntry.getScopeEntry()
						.getVariables()) {
					variables.remove(variable);
				}
			}
		}
		/*
		 * is simple-entry
		 */
		else {
			final String valueOrVariable = entry.getValueOrVariable();
			/*
			 * check if value exists and is a variable
			 */
			if (valueOrVariable != null && valueOrVariable.startsWith("$")) {
				variables.remove(valueOrVariable);
			}
		}
		entries.remove(entry);
	}

	/**
	 * Check if entry is adaptive for given construct. Method iterates over all
	 * contained entries and redirect to {@link IEntry#isAdaptiveFor(Construct)}
	 * .
	 * 
	 * @param construct
	 *            the construct
	 * @return <code>true</code> if the entry can replaced a part of the given
	 *         construct.
	 */
	public boolean isAdaptiveFor(Construct construct) {
		for (IEntry entry : entries) {
			if (!entry.isAdaptiveFor(construct)) {
				return false;
			}
		}
		return true;
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
	public boolean containsOnlyInstanceOf(Class<? extends IEntry> clazz) {
		for (IEntry entry : entries) {
			if (!clazz.isInstance(entry)) {
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
			new TemplateSerializer(this).serialize(buffer);
		} catch (SerializerException e) {
			e.printStackTrace();
		}
		return buffer.getBuffer().toString();
	}

	public static final Set<Template> fromCTM(final File file)
			throws SerializerException {
		throw new UnsupportedOperationException("not implemented yet.");
		// Set<Template> templates = new HashSet<Template>();
		//		
		// return templates;
	}

}
