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
public class CTMStreamWriter implements ICTMWriter {

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
	 * {@inheritDoc}
	 */
	public void appendLine() throws IOException {
		this.stream.write(NEWLINE.getBytes(UTF_8));
	}

	/** 
	 * {@inheritDoc}
	 */
	public void appendLine(final String... elements) throws IOException {
		appendLine(true, elements);
	}

	/** 
	 * {@inheritDoc}
	 */
	public void appendLine(final boolean withWhitespaces, final String... elements) throws IOException {
		appendElements(withWhitespaces, elements);
		this.stream.write(NEWLINE.getBytes(UTF_8));
	}

	/** 
	 * {@inheritDoc}
	 */
	public void appendLine(final String line) throws IOException {
		this.stream.write(line.trim().getBytes(UTF_8));
		this.stream.write(NEWLINE.getBytes(UTF_8));
	}

	/** 
	 * {@inheritDoc}
	 */
	public void appendTailLine() throws IOException {
		this.stream.write(TAIL.getBytes(UTF_8));
		this.stream.write(NEWLINE.getBytes(UTF_8));
	}

	/** 
	 * {@inheritDoc}
	 */
	public void appendTail() throws IOException {
		this.stream.write(TAIL.getBytes(UTF_8));
	}

	/** 
	 * {@inheritDoc}
	 */
	public void appendTailLine(final String... elements) throws IOException {
		appendElements(true, elements);
		this.stream.write(TAIL.getBytes(UTF_8));
		this.stream.write(NEWLINE.getBytes(UTF_8));
	}

	/** 
	 * {@inheritDoc}
	 */
	public void appendTailLine(final String line) throws IOException {
		this.stream.write(line.getBytes(UTF_8));
		this.stream.write(TAIL.getBytes(UTF_8));
		this.stream.write(NEWLINE.getBytes(UTF_8));
	}

	/** 
	 * {@inheritDoc}
	 */
	public void appendBlockEnd() throws IOException {
		this.stream.write(DOT.getBytes(UTF_8));
		this.stream.write(NEWLINE.getBytes(UTF_8));
	}

	/** 
	 * {@inheritDoc}
	 */
	public void appendTailLine(final boolean withWhitespaces, final String... elements) throws IOException {
		appendElements(withWhitespaces, elements);
		this.stream.write(TAIL.getBytes(UTF_8));
		this.stream.write(NEWLINE.getBytes(UTF_8));
	}

	/** 
	 * {@inheritDoc}
	 */
	public void append(final String str) throws IOException {
		this.stream.write(str.getBytes(UTF_8));
	}

	/** 
	 * {@inheritDoc}
	 */
	public void append(final String... elements) throws IOException {
		appendElements(true, elements);
	}

	/** 
	 * {@inheritDoc}
	 */
	public void append(final boolean withWhitespaces, final String... elements) throws IOException {
		appendElements(withWhitespaces, elements);
	}

	/** 
	 * {@inheritDoc}
	 */
	public void appendCommentLine(final String comment) throws IOException {
		appendLine(COMMENT, comment);
	}

}
