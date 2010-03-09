/**
 * Copyright: Copyright 2010Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/    
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 *
 */
package de.topicmapslab.ctm.writer.templates.entry.base;

import java.util.List;
import java.util.Set;

import org.tmapi.core.Construct;
import org.tmapi.core.Topic;

import de.topicmapslab.ctm.writer.exception.SerializerException;

/**
 * Base implementation of simple-template entries representing only name,
 * occurrence, identifier or type-hierarchy entries.
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 * 
 */
public abstract class EntryImpl implements IEntry {

	/**
	 * the value or variable definition of the template-entry
	 */
	private String valueOrVariable;

	/**
	 * constructor
	 * 
	 * @param valueOrVariable
	 *            the value or variable definition of the template-entry
	 */
	public EntryImpl(final String valueOrVariable) {
		this.valueOrVariable = valueOrVariable;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getValueOrVariable() {
		return valueOrVariable;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isDependentFromVariable() {
		return getValueOrVariable().startsWith("$");
	}

	/**
	 * Setter of the value or variable definition of the template-entry.
	 * 
	 * @param valueOrVariable
	 *            the new definition
	 */
	public void setValueOrVariable(String valueOrVariable) {
		this.valueOrVariable = valueOrVariable;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof EntryImpl) {
			if (isDependentFromVariable()) {
				return true;
			}
			return getValueOrVariable().equalsIgnoreCase(
					((EntryImpl) obj).getValueOrVariable());
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
	 * Redirection from {@link IEntry#extractArguments(Topic, Construct, Set)}.
	 * 
	 * Method is called to extract the arguments for the entry from the given
	 * topic. The extracted arguments are used as argument list for
	 * template-invocation.
	 * 
	 * @param topic
	 *            the topic itself
	 * @param affectedConstructs
	 *            a set used to store affected entries of the given topic
	 * @return a list of extracted arguments
	 * @throws SerializerException
	 *             thrown if given topic is not adaptive for entry
	 */
	public abstract List<String> extractArguments(final Topic topic,
			Set<Object> affectedConstructs) throws SerializerException;

	/**
	 * {@inheritDoc}
	 */
	public List<String> extractArguments(final Topic type,
			final Construct construct, Set<Object> affectedConstructs)
			throws SerializerException {
		return extractArguments((Topic) construct, affectedConstructs);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isAdaptiveFor(Construct construct) {
		if (construct instanceof Topic) {
			return isAdaptiveFor((Topic) construct);
		}
		return false;
	}

	/**
	 * Redirection from {@link IEntry#isAdaptiveFor(Construct)}.
	 * 
	 * Check if given entry is adaptive for given topic.
	 * 
	 * @param topic
	 *            the topic
	 * @return <code>true</code> if the entry can replaced a part of the given
	 *         topic.
	 */
	public abstract boolean isAdaptiveFor(Topic topic);
}
