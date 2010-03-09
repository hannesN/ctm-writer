/**
 * 
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/    
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 *
 */
package de.topicmapslab.ctm.writer.utility;

import static de.topicmapslab.ctm.writer.utility.CTMTokens.COMMENT;
import static de.topicmapslab.ctm.writer.utility.CTMTokens.DOT;
import static de.topicmapslab.ctm.writer.utility.CTMTokens.NEWLINE;
import static de.topicmapslab.ctm.writer.utility.CTMTokens.TAIL;
import static de.topicmapslab.ctm.writer.utility.CTMTokens.WHITESPACE;

/**
 * Buffer class for CTM content.
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 * 
 */
public class CTMBuffer {

	/**
	 * the internal buffer
	 */
	private StringBuffer buffer;

	/**
	 * constructor
	 */
	public CTMBuffer() {
		buffer = new StringBuffer();		
	}

	/**
	 * Internal method for appending string tokens, which can be split by
	 * white-spaces if the given flag is true.
	 * 
	 * @param withWhitespaces
	 *            flag if elements should be be split by white-spaces
	 * @param elements
	 *            the elements to add
	 */
	private void appendElements(final boolean withWhitespaces,
			final String... elements) {
		final StringBuffer buffer = new StringBuffer();
		boolean first = true;
		for (String element : elements) {
			if (!first && withWhitespaces) {
				buffer.append(WHITESPACE);
			}
			buffer.append(element);
			first = false;
		}
		this.buffer.append(buffer.toString());
	}

	/**
	 * Appends a new line to buffer, by adding a new-line-command.
	 */
	public void appendLine() {
		this.buffer.append(NEWLINE);
	}

	/**
	 * Appends a new line to buffer after the given elements. Method is calling
	 * {@link CTMBuffer#appendLine(boolean, String...)} with default parameter
	 * <code>true</code>.
	 * 
	 * @param elements
	 *            the elements to add.
	 */
	public void appendLine(final String... elements) {
		appendLine(true, elements);
	}

	/**
	 * Appends a new line to buffer after the given elements. The elements are
	 * added to buffer divided by white-spaces if the flag is <code>true</code>.
	 * 
	 * @param withWhitespaces
	 *            divide new elements with white-spaces
	 * @param elements
	 *            the elements to add
	 */
	public void appendLine(final boolean withWhitespaces,
			final String... elements) {
		appendElements(withWhitespaces, elements);
		this.buffer.append(NEWLINE);
	}

	/**
	 * Appends a new line to buffer after adding the given line.
	 * 
	 * @param line
	 *            the line to add
	 */
	public void appendLine(final String line) {
		this.buffer.append(line.trim() + NEWLINE);
	}

	/**
	 * Appends a new CTM tail-line by adding a semicolon and new-line-command at
	 * the end of line.
	 */
	public void appendTailLine() {
		this.buffer.append(TAIL + NEWLINE);
	}

	/**
	 * Appends a CTM tail-symbol to the buffer.
	 */
	public void appendTail() {
		this.buffer.append(TAIL);
	}

	/**
	 * Appends the given line and a CTM tail-line-command to the buffer.
	 * 
	 * @param elements
	 *            the elements to add
	 */
	public void appendTailLine(final String... elements) {
		appendElements(true, elements);
		this.buffer.append(TAIL + NEWLINE);
	}

	/**
	 * Appends the given line and a CTM tail-line-command to the buffer.
	 * 
	 * @param line
	 *            the line to add
	 */
	public void appendTailLine(final String line) {
		this.buffer.append(line + TAIL + NEWLINE);
	}

	/**
	 * Appends the given elements and a CTM tail-line-command to the buffer. The
	 * given elements are added to the buffer divided by white-spaces if flag is
	 * <code>true</code>.
	 * 
	 * @param withWhitespaces
	 *            divide new elements with white-spaces
	 * @param elements
	 *            the elements to add
	 */
	public void appendTailLine(final boolean withWhitespaces,
			final String... elements) {
		appendElements(withWhitespaces, elements);
		this.buffer.append(TAIL + NEWLINE);
	}

	/**
	 * Appends a new string to the buffer.
	 * 
	 * @param str
	 *            the string to add
	 */
	public void append(final String str) {
		this.buffer.append(str);
	}

	/**
	 * Appends a number of elements to the buffer divided by white-spaces.
	 * Method is calling {@link CTMBuffer#append(boolean, String...)} with
	 * default parameter <code>true</code>.
	 * 
	 * @param elements
	 *            the elements to add
	 */
	public void append(final String... elements) {
		appendElements(true, elements);
	}

	/**
	 * Appends a number of elements to the buffer divided by white-spaces if the
	 * flag is <code>true</code>.
	 * 
	 * @param withWhitespaces
	 *            divide new elements with white-spaces
	 * @param elements
	 *            the elements to add
	 */
	public void append(final boolean withWhitespaces, final String... elements) {
		appendElements(withWhitespaces, elements);
	}

	/**
	 * Appends the whole content of the given buffer and a new line-command.
	 * 
	 * @param buffer
	 *            the buffer to add
	 */
	public void appendLine(final CTMBuffer buffer) {
		this.buffer.append(buffer.getBuffer() + NEWLINE);
	}

	/**
	 * Appends the whole content of the given buffer.
	 * 
	 * @param buffer
	 *            the buffer to add
	 */
	public void append(final CTMBuffer buffer) {
		this.buffer.append(buffer.getBuffer());
	}

	/**
	 * Provide access to the internal {@link StringBuffer}.
	 * 
	 * @return the buffer
	 */
	public StringBuffer getBuffer() {
		return buffer;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return {@link StringBuffer#toString()}
	 */
	@Override
	public String toString() {
		return buffer.toString();
	}

	/**
	 * Method is clearing the internal CTM string by replacing the last
	 * tail-symbol with a dot to symbolize the end of topic-definition-block
	 */
	public void clearCTMTail() {
		String tail = getBuffer().toString();
		int index = tail.lastIndexOf(TAIL);
		if (index != -1) {
			String ctm = tail.substring(0, index) + DOT + NEWLINE;
			buffer = new StringBuffer();
			buffer.append(ctm);
		} else {
			buffer.setLength(buffer.length()-NEWLINE.length());
			buffer.append(DOT + NEWLINE);
		}
	}

	/**
	 * Appends a CTM comment to the buffer by adding a hex, the comment and a
	 * new-line-command
	 * 
	 * @param comment
	 *            the comment to add
	 */
	public void appendCommentLine(final String comment) {
		appendLine(COMMENT, comment);
	}

}
