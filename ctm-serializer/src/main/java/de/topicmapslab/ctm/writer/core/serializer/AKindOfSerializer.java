/*
 * 
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/    
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 *
 */
package de.topicmapslab.ctm.writer.core.serializer;

import static de.topicmapslab.ctm.writer.utility.CTMTokens.AKO;
import static de.topicmapslab.ctm.writer.utility.CTMTokens.TABULATOR;

import java.util.HashSet;
import java.util.Set;

import org.tmapi.core.ModelConstraintException;
import org.tmapi.core.Topic;

import de.topicmapslab.ctm.writer.core.CTMTopicMapWriter;
import de.topicmapslab.ctm.writer.exception.SerializerException;
import de.topicmapslab.ctm.writer.properties.CTMTopicMapWriterProperties;
import de.topicmapslab.ctm.writer.utility.CTMBuffer;
import de.topicmapslab.ctm.writer.utility.TypeHierarchyUtils;

/**
 * Class to realize the serialization of the following CTM grammar rule. <br />
 * <br />
 * <code>kind-of	::=	'ako' topic-ref</code> <br />
 * <br />
 * The serialized CTM string represents the super-type-relation of a topic
 * within the topic block.
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 * 
 */
public class AKindOfSerializer implements ISerializer<Topic> {

	/**
	 * a set of constructs which are affected by template-invocations
	 */
	private final Set<Object> affectedConstructs = new HashSet<Object>();

	/**
	 * the parent topic map writer
	 */
	private final CTMTopicMapWriter writer;

	/**
	 * constructor
	 * 
	 * @param affectedConstructs
	 *            a set of constructs which are affected by template-invocations
	 * @param properties
	 *            the internal {@link CTMTopicMapWriterProperties}
	 */
	public AKindOfSerializer(CTMTopicMapWriter writer,
			Set<Object> affectedConstructs) {
		this.affectedConstructs.addAll(affectedConstructs);
		this.writer = writer;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean serialize(Topic subtype, CTMBuffer buffer)
			throws SerializerException {
		try {
			boolean result = false;
			/*
			 * extract all super-types
			 */
			for (Topic supertype : TypeHierarchyUtils.getSupertypes(subtype)) {

				/*
				 * add super-type-definition
				 */
				if (!affectedConstructs.contains(supertype)) {
					buffer.appendTailLine(true, TABULATOR, AKO, writer
							.getCtmIdentity().getMainIdentifier(writer.getProperties(),
									supertype).toString());
					result = true;
				}
			}
			return result;
		} catch (ModelConstraintException e) {
			throw new SerializerException(e);
		}
	}

}
