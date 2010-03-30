/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/    
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 *
 */
package de.topicmapslab.common.tools.prefix.model;

import de.topicmapslab.common.tools.exception.ToolRuntimeException;

/**
 * Interface definition of a QName-provider. Implementations of this interface
 * are providing a unique QName for the given IRI.
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 * 
 */
public interface IQnameProvider {

	/**
	 * Generates a unique QName for the given IRI.
	 * 
	 * @param iri
	 *            the IRI
	 * @return the generated QName
	 * @throws ToolRuntimeException
	 *             thrown if generation fails
	 */
	public String generateQname(final String iri) throws ToolRuntimeException;

}
