/* 
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/    
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 *
 */
package de.topicmapslab.ctm.writer.core.serializer;

import org.tmapi.core.Construct;

import de.topicmapslab.ctm.writer.exception.SerializerException;
import de.topicmapslab.ctm.writer.utility.CTMBuffer;

/**
 * 
 * Interface definition of a CTM serializer for a specific construct type.
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 * 
 * @param <T>
 *            type parameter for specification of adaptive constructs
 */
public interface ISerializer<T extends Construct> {

	/**
	 * Method to convert the given construct to its specific CTM string. The
	 * result should be written to the given output buffer.
	 * 
	 * @param construct
	 *            the construct to serialize
	 * @param buffer
	 *            the output buffer
	 * @return <code>true</code> if new content was written into buffer,
	 *         <code>false</code> otherwise
	 * @throws SerializerException
	 *             Thrown if serialization failed.
	 */
	public boolean serialize(T construct, CTMBuffer buffer)
			throws SerializerException;
}
