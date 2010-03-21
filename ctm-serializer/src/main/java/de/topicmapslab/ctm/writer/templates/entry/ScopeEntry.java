/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/    
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 *
 */
package de.topicmapslab.ctm.writer.templates.entry;

import static de.topicmapslab.ctm.writer.utility.CTMTokens.COMMA;
import static de.topicmapslab.ctm.writer.utility.CTMTokens.SCOPE;
import static de.topicmapslab.ctm.writer.utility.CTMTokens.WHITESPACE;

import java.util.Arrays;

import org.tmapi.core.Scoped;
import org.tmapi.core.Topic;

import de.topicmapslab.ctm.writer.core.CTMTopicMapWriter;
import de.topicmapslab.ctm.writer.exception.SerializerException;
import de.topicmapslab.ctm.writer.properties.CTMTopicMapWriterProperties;
import de.topicmapslab.ctm.writer.utility.CTMBuffer;

/**
 * Class representing a template-entry definition of an role-entry.
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 * 
 */
public class ScopeEntry {

	/**
	 * the parent topic map writer
	 */
	private final CTMTopicMapWriter writer;
	/**
	 * a list of themes
	 */
	private final Topic[] themes;

	/**
	 * a list of variables
	 */
	private final String[] variables;

	/**
	 * constructor calling
	 * {@link ScopeEntry#ScopeEntry(CTMTopicMapWriterProperties,Topic[], String...)}
	 * with an empty topic array.
	 * 
	 * @param writer
	 *            the parent topic map writer
	 * @param themes
	 *            a non-empty list of themes
	 * @throws SerializerException
	 */
	protected ScopeEntry(CTMTopicMapWriter writer, String... variables)
			throws SerializerException {
		this(writer, new Topic[0], variables);
	}

	/**
	 * constructor calling
	 * {@link ScopeEntry#ScopeEntry(CTMTopicMapWriterProperties,Topic[], String...)}
	 * with an empty string array.
	 * 
	 * @param writer
	 *            the parent topic map writer
	 * @param themes
	 *            a non-empty list of themes
	 * @throws SerializerException
	 */
	protected ScopeEntry(CTMTopicMapWriter writer, Topic... themes)
			throws SerializerException {
		this(writer, themes, new String[0]);
	}

	/**
	 * constructor
	 * 
	 * @param writer
	 *            the parent topic map writer
	 * @param themes
	 *            a non-empty list of themes
	 * @throws SerializerException
	 */
	protected ScopeEntry(CTMTopicMapWriter writer, Topic[] themes,
			String... variables) throws SerializerException {
		if (themes.length == 0 && variables.length == 0) {
			throw new SerializerException(
					"themes and variables can not be empty.");
		}
		this.themes = themes;
		this.variables = variables;
		this.writer = writer;
	}

	/**
	 * Method to convert the given scope to its specific CTM string. The result
	 * should be written to the given output buffer.
	 * 
	 * @param buffer
	 *            the output buffer
	 * @throws SerializerException
	 *             Thrown if serialization failed.
	 */
	public void serialize(CTMBuffer buffer) throws SerializerException {
		boolean first = true;
		for (Topic theme : themes) {
			if (first) {
				buffer.append(SCOPE);
				first = false;
			} else {
				buffer.append(COMMA, WHITESPACE);
			}
			buffer.append(writer.getCtmIdentity().getMainIdentifier(
					writer.getProperties(), theme).toString());
		}

		for (String variable : variables) {
			if (first) {
				buffer.append(SCOPE, variable);
				first = false;
			} else {
				buffer.append(COMMA, variable);
			}
		}
	}

	/**
	 * Check if entry is adaptive for given scoped element.
	 * 
	 * @param scoped
	 *            the scoped element
	 * @return <code>true</code> if the entry can replaced a part of the given
	 *         scoped element.
	 */
	public boolean isAdaptiveFor(Scoped scoped) {
		return scoped.getScope().containsAll(Arrays.asList(themes))
				&& scoped.getScope().size() == themes.length + variables.length;
	}

	/**
	 * Method returns the internal list of themes
	 * 
	 * @return the list of themes
	 */
	public Topic[] getThemes() {
		return themes;
	}

	/**
	 * Method returns the internal list of variables
	 * 
	 * @return the list of variables
	 */
	public String[] getVariables() {
		return variables;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ScopeEntry) {
			return super.equals(obj)
					&& Arrays.asList(themes).containsAll(
							Arrays.asList(((ScopeEntry) obj).themes))
					&& Arrays.asList(variables).containsAll(
							Arrays.asList(((ScopeEntry) obj).variables));
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
