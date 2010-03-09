/**
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/    
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 *
 */
package de.topicmapslab.ctm.writer.utility;

/**
 * Utility class of CTM tokens.
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 * 
 */
public final class CTMTokens {

	/**
	 * private hidden constructor
	 */
	private CTMTokens() {

	}

	/**
	 * CTM reification token <code>~</code>
	 */
	public static final String REIFIER = "~";
	/**
	 * CTM scope token <code>@</code>
	 */
	public static final String SCOPE = "@";
	/**
	 * CTM name token <code>-</code>
	 */
	public static final String NAME = "-";
	/**
	 * CTM data-type token <code>^^</code>
	 */
	public static final String DATATYPE = "^^";
	/**
	 * CTM is-instance-of keyword <code>isa</code>
	 */
	public static final String ISA = "isa";
	/**
	 * CTM a-kind-of keyword <code>ako</code>
	 */
	public static final String AKO = "ako";
	/**
	 * CTM tail-end token <code>;</code>
	 */
	public static final String TAIL = ";";
	/**
	 * CTM definition-end token <code>.</code>
	 */
	public static final String DOT = ".";
	/**
	 * CTM type token <code>:</code>
	 */
	public static final String COLON = ":";
	/**
	 * CTM subject-locator token <code>=</code>
	 */
	public static final String SUBJECTLOCATOR = "=";
	/**
	 * CTM item-identifier token <code>^</code>
	 */
	public static final String ITEMIDENTIFIER = "^";
	/**
	 * CTM document version <code>%version 1.0</code>
	 */
	public static final String VERSION = "%version 1.0 ";
	/**
	 * CTM document encoding <code>%encoding "UTF-8"</code>
	 */
	public static final String UTF8ENCODING = "%encoding \"UTF-8\"";
	/**
	 * CTM prefix token <code>%prefix</code>
	 */
	public static final String PREFIX = "%prefix";
	/**
	 * CTM comment token <code>#</code>
	 */
	public static final String COMMENT = "#";
	/**
	 * CTM white-space
	 */
	public static final String WHITESPACE = " ";
	/**
	 * CTM tabulator
	 */
	public static final String TABULATOR = "\t";
	/**
	 * CTM comma token <code>,</code>
	 */
	public static final String COMMA = ",";
	/**
	 * CTM round brackets open <code>(</code>
	 */
	public static final String BRC = ")";
	/**
	 * CTM round brackets close <code>)</code>
	 */
	public static final String BRO = "(";
	/**
	 * CTM slash <code>/</code>
	 */
	public static final String SLASH = "/";
	/**
	 * CTM quotes for string values <code>"</code>
	 */
	public static final String QUOTE = "\"";
	/**
	 * CTM quotes for string values <code>"""</code>
	 */
	public static final String TRIPPLEQUOTE = QUOTE + QUOTE + QUOTE;
	/**
	 * CTM template-definition keyword <code>def</code>
	 */
	public static final String DEF = "def";
	/**
	 * CTM template-definition-end keyword <code>end</code>
	 */
	public static final String END = "end";
	/**
	 * CTM new line and carriage return
	 */
	public static final String NEWLINE = "\r\n";
	/**
	 * CTM topic variable <code>$topic</code>
	 */
	public static final String TOPICVARIABLE = "$topic";
	/**
	 * CTM prefix IRI start
	 */
	public static final String PREFIXBEGIN = "<";
	/**
	 * CTM prefix IRI end
	 */
	public static final String PREFIXEND = ">";

}
