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

import de.topicmapslab.ctm.writer.exception.SerializerException;
import de.topicmapslab.ctm.writer.properties.CTMTopicMapWriterProperties;
import de.topicmapslab.ctm.writer.templates.entry.base.EntryImpl;
import de.topicmapslab.ctm.writer.utility.CTMBuffer;
import de.topicmapslab.ctm.writer.utility.CTMIdentity;
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
	 * properties for CTM topic map writer
	 */
	private final CTMTopicMapWriterProperties properties;

	/**
	 * the super-type
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
	public AKindOfEntry(CTMTopicMapWriterProperties properties, Topic type, CTMIdentity ctmIdentity) {
		super(type.toString());
		this.type = type;
		this.properties = properties;
		this.ctmIdentity = ctmIdentity;
	}

	/**
	 * {@inheritDoc}
	 */
	public void serialize(CTMBuffer buffer) throws SerializerException {
		// buffer.appendTailLine(true, TABULATOR, AKO, CTMIdentity
		// .getPrefixedIdentity(type));
		buffer.appendTailLine(true, TABULATOR, AKO, ctmIdentity
				.getMainIdentifier(properties,type).toString());
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
		arguments.add(ctmIdentity.getMainIdentifier(properties,type).toString());
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
