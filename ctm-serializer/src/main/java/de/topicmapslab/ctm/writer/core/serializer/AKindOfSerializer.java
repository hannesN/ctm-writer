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

import java.io.IOException;
import java.util.Set;

import org.tmapi.core.ModelConstraintException;
import org.tmapi.core.Topic;

import de.topicmapslab.ctm.writer.core.CTMTopicMapWriter;
import de.topicmapslab.ctm.writer.exception.SerializerException;
import de.topicmapslab.ctm.writer.utility.CTMStreamWriter;
import de.topicmapslab.ctm.writer.utility.TypeHierarchyUtils;

/**
 * Class to realize the serialization of the following CTM grammar rule. <br />
 * <br />
 * <code>kind-of	::=	'ako' topic-ref</code> <br />
 * <br />
 * The serialized CTM string represents the super-type-relation of a topic within the topic block.
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 * 
 */
public class AKindOfSerializer implements ISerializer<Topic> {

	/**
	 * Method to convert the given construct to its specific CTM string. The result should be written to the given
	 * output buffer.
	 * 
	 * @param writer
	 *            the CTM writer
	 * @param affectedConstructs
	 *            the constructs affected by upper templates
	 * @param subtype
	 *            the subtype to serialize
	 * @param buffer
	 *            the output buffer
	 * @param newLine
	 *            indicates if the writer has to add a new line before firsr isa statement
	 * @return <code>true</code> if new content was written into buffer, <code>false</code> otherwise
	 * @throws SerializerException
	 *             Thrown if serialization failed.
	 */
	public static boolean serialize(CTMTopicMapWriter writer, Set<Object> affectedConstructs, Topic subtype,
			CTMStreamWriter buffer, boolean newLine) throws SerializerException, IOException {
		try {
			/*
			 * iterate over all types given known about getTypes
			 */
			boolean addTail = false;
			/*
			 * extract all super-types
			 */
			for (Topic supertype : TypeHierarchyUtils.getSupertypes(subtype)) {

				/*
				 * add super-type-definition
				 */
				if (!affectedConstructs.contains(supertype)) {
					/*
					 * adding a new line after main identity
					 */
					if (newLine) {
						buffer.appendLine();
						newLine = false;
					}
					if (addTail) {
						buffer.appendTailLine();
						addTail = false;
					}
					buffer.append(true, TABULATOR, AKO,
							writer.getCtmIdentity().getMainIdentifier(writer.getProperties(), supertype).toString());
					addTail = true;
				}
			}
			return addTail;
		} catch (ModelConstraintException e) {
			throw new SerializerException(e);
		}
	}

}
