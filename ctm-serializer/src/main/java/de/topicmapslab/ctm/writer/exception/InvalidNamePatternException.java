package de.topicmapslab.ctm.writer.exception;

/**
 * Exception class representing an invalid pattern in the context of CTM.
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 */
public class InvalidNamePatternException extends SerializerException {

	private static final long serialVersionUID = 1L;

	/**
	 * the invalid pattern
	 */
	private final String pattern;
	/**
	 * the expected regualr expression
	 */
	private final String regexp;

	/**
	 * base constructor
	 * 
	 * @param pattern
	 *            the invalid pattern
	 * @param regexp
	 *            the expected regular expression
	 */
	public InvalidNamePatternException(String pattern, String regexp) {
		super("Invalid pattern: " + pattern
				+ " doesn't match the regular expression " + regexp);
		this.pattern = pattern;
		this.regexp = regexp;
	}

	/**
	 * constructor
	 * 
	 * @param message
	 *            the error message
	 * @param pattern
	 *            the invalid pattern
	 * @param regexp
	 *            the expected regular expression
	 */
	public InvalidNamePatternException(String message, String pattern,
			String regexp) {
		super(message);
		this.pattern = pattern;
		this.regexp = regexp;
	}

	/**
	 * constructor
	 * 
	 * @param cause
	 *            the error cause
	 * @param pattern
	 *            the invalid pattern
	 * @param regexp
	 *            the expected regular expression
	 */
	public InvalidNamePatternException(Throwable cause, String pattern,
			String regexp) {
		super(cause);
		this.pattern = pattern;
		this.regexp = regexp;
	}

	/**
	 * constructor
	 * 
	 * @param cause
	 *            the error cause
	 * @param message
	 *            the error message
	 * @param pattern
	 *            the invalid pattern
	 * @param regexp
	 *            the expected regular expression
	 */
	public InvalidNamePatternException(String message, Throwable cause,
			String pattern, String regexp) {
		super(message, cause);
		this.pattern = pattern;
		this.regexp = regexp;
	}

	/**
	 * Returns the invalid pattern causes this exception.
	 * 
	 * @return the invalid pattern
	 */
	public String getPattern() {
		return pattern;
	}

	/**
	 * Returns the expected regular expression
	 * 
	 * @return the expected regular expression
	 */
	public String getRegexp() {
		return regexp;
	}

}
