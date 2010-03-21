/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/    
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 *
 */
package de.topicmapslab.ctm.writer.templates.entry;

import static de.topicmapslab.ctm.writer.utility.CTMTokens.AKO;
import static de.topicmapslab.ctm.writer.utility.CTMTokens.TABULATOR;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.tmapi.core.Topic;

import de.topicmapslab.ctm.writer.core.CTMTopicMapWriter;
import de.topicmapslab.ctm.writer.exception.SerializerException;
import de.topicmapslab.ctm.writer.templates.entry.base.EntryImpl;
import de.topicmapslab.ctm.writer.utility.CTMBuffer;
import de.topicmapslab.ctm.writer.utility.TypeHierarchyUtils;

/**
 * Class representing a template-entry definition of a kind-of-association.
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 * 
 */
public class AKindOfEntry extends EntryImpl {

	/**
	 * the parent topic map writer
	 */
	private final CTMTopicMapWriter writer;

	/**
	 * the super-type
	 */
	private final Topic type;

	/**
	 * constructor
	 * 
	 * @param writer
	 *            the parent topic map writer
	 * @param valueOrVariable
	 *            the value or variable definition of the template-entry
	 */
	protected AKindOfEntry(CTMTopicMapWriter writer, Topic type) {
		super(type.toString());
		this.type = type;
		this.writer = writer;
	}

	/**
	 * {@inheritDoc}
	 */
	public void serialize(CTMBuffer buffer) throws SerializerException {
		// buffer.appendTailLine(true, TABULATOR, AKO, CTMIdentity
		// .getPrefixedIdentity(type));
		buffer.appendTailLine(true, TABULATOR, AKO, writer.getCtmIdentity()
				.getMainIdentifier(writer.getProperties(), type).toString());
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isAdaptiveFor(Topic topic) {
		return TypeHierarchyUtils.getSupertypes(topic).contains(type);
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
		arguments.add(writer.getCtmIdentity().getMainIdentifier(
				writer.getProperties(), type).toString());
		affectedConstructs.add(type);

		return arguments;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AKindOfEntry) {
			return super.equals(obj) && type.equals(((AKindOfEntry) obj).type);
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
