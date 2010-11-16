/*
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
import static de.topicmapslab.ctm.writer.utility.CTMTokens.UTF_8;
import static de.topicmapslab.ctm.writer.utility.CTMTokens.WHITESPACE;

import java.io.IOException;
import java.io.OutputStream;

/**
 * stream class for CTM content.
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 * 
 */
public class CTMStreamWriter {

	private final OutputStream stream;

	/**
	 * constructor
	 */
	public CTMStreamWriter(final OutputStream stream) {
		this.stream = stream;
	}

	/**
	 * Internal method for appending string tokens, which can be split by white-spaces if the given flag is true.
	 * 
	 * @param withWhitespaces
	 *            flag if elements should be be split by white-spaces
	 * @param elements
	 *            the elements to add
	 */
	private void appendElements(final boolean withWhitespaces, final String... elements) throws IOException {
		boolean first = true;
		for (String element : elements) {
			if (!first && withWhitespaces) {
				stream.write(WHITESPACE.getBytes(UTF_8));
			}
			stream.write(element.getBytes(UTF_8));
			first = false;
		}
	}

	/**
	 * Appends a new line to stream, by adding a new-line-command.
	 */
	public void appendLine() throws IOException {
		this.stream.write(NEWLINE.getBytes(UTF_8));
	}

	/**
	 * Appends a new line to stream after the given elements. Method is calling
	 * {@link CTMstream#appendLine(boolean, String...)} with default parameter <code>true</code>.
	 * 
	 * @param elements
	 *            the elements to add.
	 */
	public void appendLine(final String... elements) throws IOException {
		appendLine(true, elements);
	}

	/**
	 * Appends a new line to stream after the given elements. The elements are added to stream divided by white-spaces
	 * if the flag is <code>true</code>.
	 * 
	 * @param withWhitespaces
	 *            divide new elements with white-spaces
	 * @param elements
	 *            the elements to add
	 */
	public void appendLine(final boolean withWhitespaces, final String... elements) throws IOException {
		appendElements(withWhitespaces, elements);
		this.stream.write(NEWLINE.getBytes(UTF_8));
	}

	/**
	 * Appends a new line to stream after adding the given line.
	 * 
	 * @param line
	 *            the line to add
	 */
	public void appendLine(final String line) throws IOException {
		this.stream.write(line.trim().getBytes(UTF_8));
		this.stream.write(NEWLINE.getBytes(UTF_8));
	}

	/**
	 * Appends a new CTM tail-line by adding a semicolon and new-line-command at the end of line.
	 */
	public void appendTailLine() throws IOException {
		this.stream.write(TAIL.getBytes(UTF_8));
		this.stream.write(NEWLINE.getBytes(UTF_8));
	}

	/**
	 * Appends a CTM tail-symbol to the stream.
	 */
	public void appendTail() throws IOException {
		this.stream.write(TAIL.getBytes(UTF_8));
	}

	/**
	 * Appends the given line and a CTM tail-line-command to the stream.
	 * 
	 * @param elements
	 *            the elements to add
	 */
	public void appendTailLine(final String... elements) throws IOException {
		appendElements(true, elements);
		this.stream.write(TAIL.getBytes(UTF_8));
		this.stream.write(NEWLINE.getBytes(UTF_8));
	}

	/**
	 * Appends the given line and a CTM tail-line-command to the stream.
	 * 
	 * @param line
	 *            the line to add
	 */
	public void appendTailLine(final String line) throws IOException {
		this.stream.write(line.getBytes(UTF_8));
		this.stream.write(TAIL.getBytes(UTF_8));
		this.stream.write(NEWLINE.getBytes(UTF_8));
	}

	/**
	 * Appends the given line and a CTM block-end-command to the stream.
	 * 
	 */
	public void appendBlockEnd() throws IOException {
		this.stream.write(DOT.getBytes(UTF_8));
		this.stream.write(NEWLINE.getBytes(UTF_8));
	}

	/**
	 * Appends the given elements and a CTM tail-line-command to the stream. The given elements are added to the stream
	 * divided by white-spaces if flag is <code>true</code>.
	 * 
	 * @param withWhitespaces
	 *            divide new elements with white-spaces
	 * @param elements
	 *            the elements to add
	 */
	public void appendTailLine(final boolean withWhitespaces, final String... elements) throws IOException {
		appendElements(withWhitespaces, elements);
		this.stream.write(TAIL.getBytes(UTF_8));
		this.stream.write(NEWLINE.getBytes(UTF_8));
	}

	/**
	 * Appends a new string to the stream.
	 * 
	 * @param str
	 *            the string to add
	 */
	public void append(final String str) throws IOException {
		this.stream.write(str.getBytes(UTF_8));
	}

	/**
	 * Appends a number of elements to the stream divided by white-spaces. Method is calling
	 * {@link CTMstream#append(boolean, String...)} with default parameter <code>true</code>.
	 * 
	 * @param elements
	 *            the elements to add
	 */
	public void append(final String... elements) throws IOException {
		appendElements(true, elements);
	}

	/**
	 * Appends a number of elements to the stream divided by white-spaces if the flag is <code>true</code>.
	 * 
	 * @param withWhitespaces
	 *            divide new elements with white-spaces
	 * @param elements
	 *            the elements to add
	 */
	public void append(final boolean withWhitespaces, final String... elements) throws IOException {
		appendElements(withWhitespaces, elements);
	}

	/**
	 * Appends a CTM comment to the stream by adding a hex, the comment and a new-line-command
	 * 
	 * @param comment
	 *            the comment to add
	 */
	public void appendCommentLine(final String comment) throws IOException {
		appendLine(COMMENT, comment);
	}

}
