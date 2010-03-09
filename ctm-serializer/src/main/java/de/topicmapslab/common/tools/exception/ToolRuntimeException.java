/** 
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/    
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 *
 */
package de.topicmapslab.common.tools.exception;

import de.topicmapslab.common.exception.CommonException;

/**
 * Basic Exception of all exception of TML JAVA tools.
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 * 
 */
public class ToolRuntimeException extends CommonException {

	private static final long serialVersionUID = 1L;

	/**
	 * empty construct bequeathed from super-class
	 */
	public ToolRuntimeException() {
	}

	/**
	 * construct bequeathed from super-class
	 * 
	 * @param message
	 *            the message
	 */
	public ToolRuntimeException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * construct bequeathed from super-class
	 * 
	 * @param cause
	 *            the cause
	 */
	public ToolRuntimeException(Throwable cause) {
		super(cause);
	}

	/**
	 * construct bequeathed from super-class
	 * 
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public ToolRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

}
