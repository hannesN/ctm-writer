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
 * Exception thrown if any operation during the process of serialization failed.
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 * 
 */
public class SerializerException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * empty construct bequeathed from super-class
	 */
	public SerializerException() {
		// VOID
	}

	/**
	 * construct bequeathed from super-class
	 * 
	 * @param message
	 *            the message
	 */
	public SerializerException(String message) {
		super(message);
	}

	/**
	 * construct bequeathed from super-class
	 * 
	 * @param cause
	 *            the cause
	 */
	public SerializerException(Throwable cause) {
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
	public SerializerException(String message, Throwable cause) {
		super(message, cause);
	}

}
