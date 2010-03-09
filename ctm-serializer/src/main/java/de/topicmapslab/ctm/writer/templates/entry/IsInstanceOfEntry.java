/**
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/    
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 *
 */
package de.topicmapslab.ctm.writer.templates.entry;

import static de.topicmapslab.ctm.writer.utility.CTMTokens.ISA;
import static de.topicmapslab.ctm.writer.utility.CTMTokens.TABULATOR;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.tmapi.core.Topic;

import de.topicmapslab.ctm.writer.exception.SerializerException;
import de.topicmapslab.ctm.writer.properties.CTMTopicMapWriterProperties;
import de.topicmapslab.ctm.writer.templates.entry.base.EntryImpl;
import de.topicmapslab.ctm.writer.utility.CTMBuffer;
import de.topicmapslab.ctm.writer.utility.CTMIdentity;

/**
 * Class representing a template-entry definition of a instance-of-association.
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 * 
 */
public class IsInstanceOfEntry extends EntryImpl {

	/**
	 * properties for CTM topic map writer
	 */
	private final CTMTopicMapWriterProperties properties;
	/**
	 * the type
	 */
	private final Topic type;
	
	/**
	 * identity utility (cache and generator)
	 */
	private final CTMIdentity ctmIdentity;

	/**
	 * constructor
	 * 
	 * @param properties
	 *            the properties
	 * @param valueOrVariable
	 *            the value or variable definition of the template-entry
	 */
	public IsInstanceOfEntry(CTMTopicMapWriterProperties properties, CTMIdentity ctmIdentity, Topic type) {
		super(type.toString());
		this.type = type;
		this.properties = properties;
		this.ctmIdentity = ctmIdentity;
	}

	/**
	 * {@inheritDoc}
	 */
	public void serialize(CTMBuffer buffer) throws SerializerException {
		buffer.appendTailLine(true, TABULATOR, ISA, ctmIdentity
				.generateItemIdentifier(properties, type).toString());
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isAdaptiveFor(Topic topic) {
		return topic.getTypes().contains(type);
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
		 * is variable depended value
		 */
		if (isDependentFromVariable()) {
			Topic type = topic.getTypes().iterator().next();
			arguments.add(ctmIdentity.generateItemIdentifier(properties, type).toString());
			affectedConstructs.add(type);

		}
		/*
		 * is constant value
		 */
		else {
			arguments.add(ctmIdentity
					.getPrefixedIdentity(properties, this.type).toString());
			affectedConstructs.add(this.type);
		}
		return arguments;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof IsInstanceOfEntry) {
			return super.equals(obj)
					&& type.equals(((IsInstanceOfEntry) obj).type);
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
