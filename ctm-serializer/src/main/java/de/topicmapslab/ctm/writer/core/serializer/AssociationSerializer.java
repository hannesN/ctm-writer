/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/    
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 *
 */
package de.topicmapslab.ctm.writer.core.serializer;

import static de.topicmapslab.ctm.writer.utility.CTMTokens.BRC;
import static de.topicmapslab.ctm.writer.utility.CTMTokens.BRO;
import static de.topicmapslab.ctm.writer.utility.CTMTokens.COMMA;
import static de.topicmapslab.ctm.writer.utility.CTMTokens.TABULATOR;

import java.util.HashSet;
import java.util.Set;

import org.tmapi.core.Association;
import org.tmapi.core.Role;

import de.topicmapslab.ctm.writer.core.CTMTopicMapWriter;
import de.topicmapslab.ctm.writer.exception.SerializerException;
import de.topicmapslab.ctm.writer.templates.Template;
import de.topicmapslab.ctm.writer.templates.TemplateInvocationSerializer;
import de.topicmapslab.ctm.writer.utility.CTMBuffer;

/**
 * Class to realize the serialization of the following CTM grammar rule. <br />
 * <br />
 * <code>association ::=	assoc-type '(' roles ')' scope?  reifier? </code> <br />
 * <br />
 * The serialized CTM string represents a association item, with all roles,
 * players and optional reification and scope within the topic map block.
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 * 
 */
public class AssociationSerializer implements ISerializer<Association> {

	/**
	 * internal templates replacing all association items representing by this
	 * serializer.
	 */
	private final Set<Template> adaptiveTemplates;

	/**
	 * the parent topic map writer
	 */
	private final CTMTopicMapWriter writer;

	/**
	 * constructor
	 * 
	 * @param writer
	 *            the parent writer
	 */
	public AssociationSerializer(CTMTopicMapWriter writer) {
		this(writer, new HashSet<Template>());
	}

	/**
	 * constructor
	 * 
	 * @param writer
	 *            the parent writer
	 * 
	 * @param adaptiveTemplates
	 *            a set of templates replacing the association item
	 */
	public AssociationSerializer(CTMTopicMapWriter writer,
			Set<Template> adaptiveTemplates) {
		this.adaptiveTemplates = adaptiveTemplates;
		this.writer = writer;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean serialize(Association association, CTMBuffer buffer)
			throws SerializerException {

		/*
		 * if templates are not empty, use template-invocation instead of
		 * association-definition
		 */
		if (!adaptiveTemplates.isEmpty()) {
			/*
			 * iterate over templates
			 */
			for (Template template : adaptiveTemplates) {
				/*
				 * redirect to template-invocation-serializer
				 */
				new TemplateInvocationSerializer(template).serialize(
						association, buffer);
			}
		}
		/*
		 * templates are empty
		 */
		else {
			/*
			 * create association definition block
			 */
			buffer.appendLine(true, writer.getCtmIdentity().getMainIdentifier(
					writer.getProperties(), association.getType()).toString(),
					BRO);

			boolean first = true;
			/*
			 * add all role-player-definitions
			 */
			for (Role role : association.getRoles()) {
				CTMBuffer bufferoles = new CTMBuffer();
				new RoleSerializer(writer).serialize(role, bufferoles);
				if (!first) {
					buffer.appendLine(COMMA);
				}
				buffer.append(true, TABULATOR, bufferoles.getBuffer()
						.toString());
				first = false;
			}

			/*
			 * end role-player-list
			 */
			buffer.appendLine();
			buffer.appendLine(BRC);
			CTMBuffer ctmBuffer = null;

			/*
			 * add scope-definition if exists
			 */
			ctmBuffer = new CTMBuffer();
			if (new ScopedSerializer(writer).serialize(association, ctmBuffer)) {
				buffer.appendLine();
				buffer.append(TABULATOR);
				buffer.append(ctmBuffer);
			}

			/*
			 * add reifier-definition if exists
			 */
			ctmBuffer = new CTMBuffer();
			if (new ReifiableSerializer(writer).serialize(association,
					ctmBuffer)) {
				buffer.appendLine();
				buffer.append(TABULATOR);
				buffer.append(ctmBuffer);
			}
		}
		return true;
	}

}
