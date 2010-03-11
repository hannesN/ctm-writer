/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/    
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 *
 */
package de.topicmapslab.common.exception;

/**
 * Basic Exception of all exception of TML JAVA programs.
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 * 
 */
public class CommonException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Construct
	 */
	public CommonException() {
	}

	/**
	 * Construct with cause message
	 * 
	 * @param message
	 *            the message
	 */
	public CommonException(String message) {
		super(message);
	}

	/**
	 * Construct with cause
	 * 
	 * @param cause
	 *            the cause of the exception
	 */
	public CommonException(Throwable cause) {
		super(cause);
	}

	/**
	 * Construct with cause and message.
	 * 
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public CommonException(String message, Throwable cause) {
		super(message, cause);
	}

}
