/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/    
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 *
 */
package de.topicmapslab.ctm.writer.exception;

/**
 * Exception thrown if a topic has not subject-identifier, subject-locator and
 * item-identifier.
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 * 
 */
public class NoIdentityException extends SerializerException {

	private static final long serialVersionUID = 1L;

	/**
	 * empty construct bequeathed from super-class
	 */
	public NoIdentityException() {
	}

	/**
	 * construct bequeathed from super-class
	 * 
	 * @param message
	 *            the message
	 */
	public NoIdentityException(String message) {
		super(message);
	}

	/**
	 * construct bequeathed from super-class
	 * 
	 * @param cause
	 *            the cause
	 */
	public NoIdentityException(Throwable cause) {
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
	public NoIdentityException(String message, Throwable cause) {
		super(message, cause);
	}

}
