/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/    
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 *
 */
package de.topicmapslab.ctm.writer.templates.entry;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.tmapi.core.Locator;
import org.tmapi.core.Topic;

import de.topicmapslab.ctm.writer.exception.SerializerException;
import de.topicmapslab.ctm.writer.utility.CTMIdentity;

/**
 * Class representing a template-entry definition of an
 * subject-identifier-entry.
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 * 
 */
public class SubjectIdentifierEntry extends IdentifierEntry {

	/**
	 * constructor
	 * 
	 * @param valueOrVariable
	 *            the subject-identifier or variable to define value of the template-entry
	 */
	public SubjectIdentifierEntry(String valueOrVariable, CTMIdentity ctmIdentity) {
		super(valueOrVariable, ctmIdentity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getPrefix() {
		return "";
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isAdaptiveFor(Topic topic) {
		return !topic.getSubjectIdentifiers().isEmpty();
	}

	/**
	 * {@inheritDoc}
	 */
	public List<String> extractArguments(Topic topic,
			Set<Object> affectedConstructs) throws SerializerException {

		if (!isAdaptiveFor(topic)) {
			throw new SerializerException(
					"template entry is not adaptive for given topic.");
		}
		List<String> arguments = new LinkedList<String>();
		/*
		 * value is variable
		 */
		if (isDependentFromVariable()) {
			Locator locator = topic.getSubjectIdentifiers().iterator().next();
			affectedConstructs.add(locator);
			arguments.add(ctmIdentity.getPrefixedIdentity(locator));
		}
		/*
		 * value is a constant
		 */
		else {
			Locator locator = topic.getTopicMap().createLocator(
					getValueOrVariable());
			affectedConstructs.add(locator);
			arguments.add(getValueOrVariable());
		}

		return arguments;
	}

}
