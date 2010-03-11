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

import java.util.Collection;

import de.topicmapslab.ctm.writer.exception.SerializerException;
import de.topicmapslab.ctm.writer.templates.entry.AssociationEntry;
import de.topicmapslab.ctm.writer.templates.entry.base.EntryImpl;
import de.topicmapslab.ctm.writer.templates.entry.base.IEntry;
import de.topicmapslab.ctm.writer.utility.CTMBuffer;

/**
 * Serializer implementation for {@link Template}s.
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 * 
 */
public class TemplateSerializer {

	/**
	 * the template to serialize
	 */
	private final Template template;

	/**
	 * constructor
	 * 
	 * @param template
	 *            the template to serialize
	 */
	public TemplateSerializer(Template template) {
		this.template = template;
	}

	/**
	 * Method to convert the internal template to its specific CTM string. The
	 * result should be written to the given output buffer.
	 * 
	 * @param buffer
	 *            the output buffer
	 * @return <code>true</code> if new content was written into buffer,
	 *         <code>false</code> otherwise
	 * @throws SerializerException
	 *             Thrown if serialization failed.
	 */
	public boolean serialize(CTMBuffer buffer) throws SerializerException {

		/*
		 * create template-definition-head
		 */
		boolean firstVariable = true;
		buffer.append(DEF, template.getTemplateName(), BRO);
		/*
		 * create argument list
		 */
		if (!template.containsOnlyInstanceOf(AssociationEntry.class)) {
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
		CTMBuffer topicDef = new CTMBuffer();
		for (IEntry entry : template.getEntries()) {
			/*
			 * create topic-definition-entry
			 */
			if (entry instanceof EntryImpl) {
				if (topicDef.getBuffer().length() == 0) {
					topicDef.appendLine(TABULATOR, TOPICVARIABLE);
				}
				topicDef.append(TABULATOR);
				entry.serialize(topicDef);
			}
			/*
			 * create association-entry
			 */
			else {
				/*
				 * check if old topic-block is stored at the CTM buffer
				 */
				if (topicDef.getBuffer().length() != 0) {
					/*
					 * end topic-definition block append to global buffer
					 */
					topicDef.clearCTMTail();
					buffer.append(topicDef);
					topicDef = new CTMBuffer();
				}
				buffer.append(TABULATOR);
				entry.serialize(buffer);
			}
		}

		/*
		 * check if old topic-block is stored at the CTM buffer
		 */
		if (topicDef.getBuffer().length() != 0) {
			/*
			 * end topic-definition block append to global buffer
			 */
			topicDef.clearCTMTail();
			buffer.append(topicDef);
			topicDef = new CTMBuffer();
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
	 * @param buffer
	 *            the output buffer
	 * @param arguments
	 *            a list of arguments
	 * @return <code>true</code> if new content was written into buffer,
	 *         <code>false</code> otherwise
	 * @throws SerializerException
	 *             Thrown if serialization failed.
	 */
	public boolean serialize(CTMBuffer buffer, Collection<String> arguments)
			throws SerializerException {
		return serialize(buffer, arguments.toArray(new String[0]));
	}

	/**
	 * Method to convert the internal template to its specific CTM string
	 * representing a template-invocation call by using the given arguments. The
	 * result should be written to the given output buffer.
	 * 
	 * @param buffer
	 *            the output buffer
	 * @param arguments
	 *            a list of arguments
	 * @return <code>true</code> if new content was written into buffer,
	 *         <code>false</code> otherwise
	 * @throws SerializerException
	 *             Thrown if serialization failed.
	 */
	public boolean serialize(CTMBuffer buffer, String... arguments)
			throws SerializerException {
		/*
		 * generate template-invocation-begin --> write template name
		 */
		buffer.append(false,template.getTemplateName(), BRO);
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