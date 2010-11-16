/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/    
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 *
 */
package de.topicmapslab.ctm.writer.templates;

import static de.topicmapslab.ctm.writer.utility.CTMTokens.BRC;
import static de.topicmapslab.ctm.writer.utility.CTMTokens.BRO;
import static de.topicmapslab.ctm.writer.utility.CTMTokens.COMMA;
import static de.topicmapslab.ctm.writer.utility.CTMTokens.DEF;
import static de.topicmapslab.ctm.writer.utility.CTMTokens.END;
import static de.topicmapslab.ctm.writer.utility.CTMTokens.TABULATOR;
import static de.topicmapslab.ctm.writer.utility.CTMTokens.TOPICVARIABLE;
import static de.topicmapslab.ctm.writer.utility.CTMTokens.WHITESPACE;

import java.io.IOException;
import java.util.Collection;

import de.topicmapslab.ctm.writer.exception.SerializerException;
import de.topicmapslab.ctm.writer.templates.entry.AssociationEntry;
import de.topicmapslab.ctm.writer.templates.entry.TopicEntry;
import de.topicmapslab.ctm.writer.templates.entry.base.EntryImpl;
import de.topicmapslab.ctm.writer.templates.entry.base.IEntry;
import de.topicmapslab.ctm.writer.utility.CTMStreamWriter;

/**
 * Serializer implementation for {@link Template}s.
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 * 
 */
public class TemplateSerializer {

	/**
	 * Method to convert the internal template to its specific CTM string. The
	 * result should be written to the given output buffer.
	 * 
	 * @param template
	 *            the template to serialize
	 * @param buffer
	 *            the output buffer
	 * @return <code>true</code> if new content was written into buffer,
	 *         <code>false</code> otherwise
	 * @throws SerializerException
	 *             Thrown if serialization failed.
	 */
	@SuppressWarnings("unchecked")
	public static boolean serialize(Template template, CTMStreamWriter buffer)
			throws SerializerException, IOException {

		/*
		 * create template-definition-head
		 */
		boolean firstVariable = true;
		buffer.append(DEF, template.getTemplateName(), BRO);
		/*
		 * create argument list
		 */
		if (!template.containsOnlyInstanceOf(AssociationEntry.class,
				TopicEntry.class)) {
			buffer.append(TOPICVARIABLE);
			firstVariable = false;
		}

		for (String variable : template.getVariables()) {
			if (!firstVariable) {
				buffer.append(COMMA, WHITESPACE);
			}
			buffer.append(variable);
			firstVariable = false;
		}
		buffer.appendLine(BRC);

		/*
		 * create template-definition-body
		 */
		boolean first = true;
		boolean addTail = false;
		for (IEntry entry : template.getEntries()) {
			/*
			 * create topic-definition-entry
			 */
			if (entry instanceof EntryImpl && !(entry instanceof TopicEntry)) {
				if (first) {
					buffer.appendLine(TABULATOR, TOPICVARIABLE);
					first = false;
				}
				buffer.append(TABULATOR);
				entry.serialize(buffer);
				addTail = true;
			}
			/*
			 * create association-entry
			 */
			else {
				/*
				 * check if old topic-block is stored at the CTM buffer
				 */
				if (!first) {
					/*
					 * end topic-definition block append to global buffer
					 */
					if ( addTail ){
						buffer.appendTailLine();
						addTail = false;
					}					
				}
				buffer.append(TABULATOR);
				entry.serialize(buffer);
			}
		}

		/*
		 * check if old topic-block is stored at the CTM buffer
		 */
		if (addTail) {
			/*
			 * end topic-definition block append to global buffer
			 */
			buffer.appendTailLine();
			addTail = false;
		}

		/*
		 * create template-definition-end
		 */
		buffer.appendLine(END);
		return true;
	}

	/**
	 * Method to convert the internal template to its specific CTM string
	 * representing a template-invocation call by using the given arguments. The
	 * result should be written to the given output buffer.
	 * 
	 * @param template
	 *            the template to serialize
	 * @param buffer
	 *            the output buffer
	 * @param arguments
	 *            a list of arguments
	 * @return <code>true</code> if new content was written into buffer,
	 *         <code>false</code> otherwise
	 * @throws SerializerException
	 *             Thrown if serialization failed.
	 */
	public static boolean serialize(Template template, CTMStreamWriter buffer,
			Collection<String> arguments) throws SerializerException, IOException {
		return serialize(template, buffer, arguments.toArray(new String[0]));
	}

	/**
	 * Method to convert the internal template to its specific CTM string
	 * representing a template-invocation call by using the given arguments. The
	 * result should be written to the given output buffer.
	 * 
	 * @param template
	 *            the template to serialize
	 * @param buffer
	 *            the output buffer
	 * @param arguments
	 *            a list of arguments
	 * @return <code>true</code> if new content was written into buffer,
	 *         <code>false</code> otherwise
	 * @throws SerializerException
	 *             Thrown if serialization failed.
	 */
	public static boolean serialize(Template template, CTMStreamWriter buffer,
			String... arguments) throws SerializerException, IOException {
		/*
		 * generate template-invocation-begin --> write template name
		 */
		buffer.append(false, template.getTemplateName(), BRO);
		boolean first = true;
		/*
		 * add argument list
		 */
		for (String argument : arguments) {
			if (!first) {
				buffer.append(COMMA);
			}
			first = false;
			buffer.append(argument);
		}
		/*
		 * finishing tail-line
		 */
		buffer.append(BRC);
		return true;
	}
}
