/** 
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

import de.topicmapslab.ctm.writer.exception.SerializerException;
import de.topicmapslab.ctm.writer.properties.CTMTopicMapWriterProperties;
import de.topicmapslab.ctm.writer.templates.Template;
import de.topicmapslab.ctm.writer.templates.TemplateInvocationSerializer;
import de.topicmapslab.ctm.writer.utility.CTMBuffer;
import de.topicmapslab.ctm.writer.utility.CTMIdentity;

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
	 * properties for CTM topic map writer
	 */
	private final CTMTopicMapWriterProperties properties;

	/**
	 * identity utility (cache and generator)
	 */
	private final CTMIdentity ctmIdentity;
	
	/**
	 * constructor, calling the
	 * {@link AssociationSerializer#AssociationSerializer(Set)} with a new
	 * {@link HashSet}
	 */
	public AssociationSerializer(CTMTopicMapWriterProperties properties, CTMIdentity ctmIdentity) {
		this(properties, ctmIdentity, new HashSet<Template>());
	}

	/**
	 * constructor
	 * 
	 * @param adaptiveTemplates
	 *            a set of templates replacing the association item
	 * @param properties
	 *            the internal {@link CTMTopicMapWriterProperties}
	 */
	public AssociationSerializer(CTMTopicMapWriterProperties properties, CTMIdentity ctmIdentity,
			Set<Template> adaptiveTemplates) {
		this.adaptiveTemplates = adaptiveTemplates;
		this.properties = properties;
		this.ctmIdentity = ctmIdentity;
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
			buffer.appendLine(true, ctmIdentity.generateItemIdentifier(
					properties, association.getType()).toString(), BRO);

			boolean first = true;
			/*
			 * add all role-player-definitions
			 */
			for (Role role : association.getRoles()) {
				CTMBuffer bufferoles = new CTMBuffer();
				new RoleSerializer(properties, ctmIdentity).serialize(role, bufferoles);
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
			if (new ScopedSerializer(properties, ctmIdentity).serialize(association,
					ctmBuffer)) {
				buffer.appendLine();
				buffer.append(TABULATOR);
				buffer.append(ctmBuffer);
			}

			/*
			 * add reifier-definition if exists
			 */
			ctmBuffer = new CTMBuffer();
			if (new ReifiableSerializer(properties, ctmIdentity).serialize(association,
					ctmBuffer)) {
				buffer.appendLine();
				buffer.append(TABULATOR);
				buffer.append(ctmBuffer);
			}
		}
		return true;
	}

}
