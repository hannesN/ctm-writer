/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/    
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 *
 */
package de.topicmapslab.ctm.writer.templates.entry;

import static de.topicmapslab.ctm.writer.utility.CTMTokens.SUBJECTLOCATOR;
import de.topicmapslab.ctm.writer.templates.entry.param.IEntryParam;
import de.topicmapslab.ctm.writer.utility.CTMIdentity;

/**
 * Class representing a template-entry definition of an subject-locator-entry.
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 * 
 */
public class SubjectLocatorEntry extends IdentifierEntry {

	/**
	 * constructor
	 * 
	 * @param value
	 *            the subject-locator or variable to define value of the
	 *            template-entry
	 */
	public SubjectLocatorEntry(IEntryParam value, CTMIdentity ctmIdentity) {
		super(value, ctmIdentity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getPrefix() {
		return SUBJECTLOCATOR;
	}

	// /**
	// * {@inheritDoc}
	// */
	// public boolean isAdaptiveFor(Topic topic) {
	// return !topic.getSubjectLocators().isEmpty();
	// }
	//
	// /**
	// * {@inheritDoc}
	// */
	// public List<String> extractArguments(Topic topic,
	// Set<Object> affectedConstructs) throws SerializerException {
	//
	// if (!isAdaptiveFor(topic)) {
	// throw new SerializerException(
	// "template entry is not adaptive for given topic.");
	// }
	//
	// List<String> arguments = new LinkedList<String>();
	// /*
	// * value is variable
	// */
	// if (isDependentFromVariable()) {
	// Locator locator = topic.getSubjectLocators().iterator().next();
	// affectedConstructs.add(locator);
	// arguments.add(ctmIdentity.getPrefixedIdentity(locator));
	// }
	// /*
	// * value is a constant
	// */
	// else {
	// Locator locator = topic.getTopicMap().createLocator(
	// getParameter().getCTMRepresentation());
	// affectedConstructs.add(locator);
	// arguments.add(getParameter().getCTMRepresentation());
	// }
	//
	// return arguments;
	// }
}
