/**
 * 
 */
package de.topicmapslab.ctm.writer.utility;

import java.io.IOException;

/**
 * @author Hannes Niederhausen
 *
 */
public interface ICTMWriter {

	/**
	 * Appends a new line to stream, by adding a new-line-command.
	 */
	public void appendLine() throws IOException;

	/**
	 * Appends a new line to stream after the given elements. Method is calling
	 * {@link CTMstream#appendLine(boolean, String...)} with default parameter <code>true</code>.
	 * 
	 * @param elements
	 *            the elements to add.
	 */
	public void appendLine(final String... elements) throws IOException;

	/**
	 * Appends a new line to stream after the given elements. The elements are added to stream divided by white-spaces
	 * if the flag is <code>true</code>.
	 * 
	 * @param withWhitespaces
	 *            divide new elements with white-spaces
	 * @param elements
	 *            the elements to add
	 */
	public void appendLine(final boolean withWhitespaces,
			final String... elements) throws IOException;

	/**
	 * Appends a new line to stream after adding the given line.
	 * 
	 * @param line
	 *            the line to add
	 */
	public void appendLine(final String line) throws IOException;

	/**
	 * Appends a new CTM tail-line by adding a semicolon and new-line-command at the end of line.
	 */
	public void appendTailLine() throws IOException;

	/**
	 * Appends a CTM tail-symbol to the stream.
	 */
	public void appendTail() throws IOException;

	/**
	 * Appends the given line and a CTM tail-line-command to the stream.
	 * 
	 * @param elements
	 *            the elements to add
	 */
	public void appendTailLine(final String... elements) throws IOException;

	/**
	 * Appends the given line and a CTM tail-line-command to the stream.
	 * 
	 * @param line
	 *            the line to add
	 */
	public void appendTailLine(final String line) throws IOException;

	/**
	 * Appends the given line and a CTM block-end-command to the stream.
	 * 
	 */
	public void appendBlockEnd() throws IOException;

	/**
	 * Appends the given elements and a CTM tail-line-command to the stream. The given elements are added to the stream
	 * divided by white-spaces if flag is <code>true</code>.
	 * 
	 * @param withWhitespaces
	 *            divide new elements with white-spaces
	 * @param elements
	 *            the elements to add
	 */
	public void appendTailLine(final boolean withWhitespaces,
			final String... elements) throws IOException;

	/**
	 * Appends a new string to the stream.
	 * 
	 * @param str
	 *            the string to add
	 */
	public void append(final String str) throws IOException;

	/**
	 * Appends a number of elements to the stream divided by white-spaces. Method is calling
	 * {@link CTMstream#append(boolean, String...)} with default parameter <code>true</code>.
	 * 
	 * @param elements
	 *            the elements to add
	 */
	public void append(final String... elements) throws IOException;

	/**
	 * Appends a number of elements to the stream divided by white-spaces if the flag is <code>true</code>.
	 * 
	 * @param withWhitespaces
	 *            divide new elements with white-spaces
	 * @param elements
	 *            the elements to add
	 */
	public void append(final boolean withWhitespaces, final String... elements)
			throws IOException;

	/**
	 * Appends a CTM comment to the stream by adding a hex, the comment and a new-line-command
	 * 
	 * @param comment
	 *            the comment to add
	 */
	public void appendCommentLine(final String comment) throws IOException;

}