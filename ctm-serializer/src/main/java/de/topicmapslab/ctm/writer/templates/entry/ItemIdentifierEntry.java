/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/    
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 *
 */
package de.topicmapslab.ctm.writer.templates.entry;

import static de.topicmapslab.ctm.writer.utility.CTMTokens.ITEMIDENTIFIER;
import de.topicmapslab.ctm.writer.templates.entry.param.IEntryParam;
import de.topicmapslab.ctm.writer.utility.CTMIdentity;

/**
 * Class representing a template-entry definition of an item-identifier-entry.
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 * 
 */
public class ItemIdentifierEntry extends IdentifierEntry {

	/**
	 * constructor
	 * 
	 * @param param
	 *            the parameter
	 */
	public ItemIdentifierEntry(IEntryParam param, CTMIdentity ctmIdentity) {
		super(param, ctmIdentity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getPrefix() {
		return ITEMIDENTIFIER;
	}

	// /**
	// * {@inheritDoc}
	// */
	// public boolean isAdaptiveFor(Topic topic) {
	// return !topic.getItemIdentifiers().isEmpty();
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
	// * if value is a variable
	// */
	// if (getParameter() instanceof VariableParam) {
	// Locator locator = topic.getItemIdentifiers().iterator().next();
	// affectedConstructs.add(locator);
	// arguments.add(ctmIdentity.getPrefixedIdentity(locator));
	// }
	// /*
	// * if value is constant
	// */
	// else if (getParameter() instanceof ValueParam) {
	// Locator locator = topic.getTopicMap().createLocator(
	// getParameter().getCTMRepresentation());
	// affectedConstructs.add(locator);
	// arguments.add(ctmIdentity.getPrefixedIdentity(locator));
	// }
	//
	// return arguments;
	// }

}
