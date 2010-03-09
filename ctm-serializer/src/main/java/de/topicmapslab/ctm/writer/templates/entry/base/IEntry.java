/**
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/    
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
import de.topicmapslab.ctm.writer.templates.Template;
import de.topicmapslab.ctm.writer.utility.CTMBuffer;

/**
 * Interface definition of template-definition-entries, which can be used as
 * parameters for method {@link Template#add(IEntry)}.
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 * 
 */
public interface IEntry {

	/**
	 * Method to convert the given construct to its specific CTM string. The
	 * result should be written to the given output buffer.
	 * 
	 * @param buffer
	 *            the output buffer
	 * @throws SerializerException
	 *             Thrown if serialization failed.
	 */
	public void serialize(CTMBuffer buffer) throws SerializerException;

	/**
	 * Check if entry is adaptive for given construct.
	 * 
	 * @param construct
	 *            the construct
	 * @return <code>true</code> if the entry can replaced a part of the given
	 *         construct.
	 */
	public boolean isAdaptiveFor(Construct construct);

	/**
	 * The internal definition of a value or variable as value part of
	 * template-entry.
	 * 
	 * @return the valueOrVariable the definition of a value or variable
	 */
	public String getValueOrVariable();

	/**
	 * Checks if the template value definition is a variable or a constant value
	 * ( a topic reference etc. )
	 * 
	 * @return <code>true</code> if the value is a variable name,
	 *         <code>false</code> otherwise
	 */
	public boolean isDependentFromVariable();

	/**
	 * Method is called to extract the arguments for the entry from the given
	 * construct. The extracted arguments are used as argument list for
	 * template-invocation.
	 * 
	 * @param type
	 *            the type of the given construct
	 * @param construct
	 *            the construct itself
	 * @param affectedConstructs
	 *            a set used to store affected entries of the given construct
	 * @return a list of extracted arguments
	 * @throws SerializerException
	 *             thrown if given construct is not adaptive for entry
	 */
	public List<String> extractArguments(final Topic type,
			final Construct construct, Set<Object> affectedConstructs)
			throws SerializerException;
}
