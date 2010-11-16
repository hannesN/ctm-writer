/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/    
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 *
 */
package de.topicmapslab.ctm.writer.core.serializer;

import static de.topicmapslab.ctm.writer.utility.CTMTokens.*;
import static de.topicmapslab.ctm.writer.utility.CTMTokens.BRO;
import static de.topicmapslab.ctm.writer.utility.CTMTokens.COMMA;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.tmapi.core.Association;
import org.tmapi.core.Role;

import de.topicmapslab.ctm.writer.core.CTMTopicMapWriter;
import de.topicmapslab.ctm.writer.exception.SerializerException;
import de.topicmapslab.ctm.writer.templates.Template;
import de.topicmapslab.ctm.writer.utility.CTMStreamWriter;

/**
 * Class to realize the serialization of the following CTM grammar rule. <br />
 * <br />
 * <code>association ::=	assoc-type '(' roles ')' scope?  reifier? </code> <br />
 * <br />
 * The serialized CTM string represents a association item, with all roles, players and optional reification and scope
 * within the topic map block.
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 * 
 */
public class AssociationSerializer implements ISerializer<Association> {

	/**
	 * Method to convert the given construct to its specific CTM string. The result should be written to the given
	 * output buffer.
	 * 
	 * @param writer
	 *            the CTM writer
	 * @param adaptiveTemplates
	 *            the templates which can be used for the current association item
	 * @param association
	 *            the association to serialize
	 * @param buffer
	 *            the output buffer
	 * @return the affected constructs by using any template definition, used to avoid twice exports
	 * @throws SerializerException
	 *             Thrown if serialization failed.
	 */
	public static Set<Object> serialize(CTMTopicMapWriter writer, Set<Template> adaptiveTemplates,
			Association association, CTMStreamWriter buffer) throws SerializerException, IOException {

		Set<Object> affectedConstructs = new HashSet<Object>();

		/*
		 * if templates are not empty, use template-invocation instead of association-definition
		 */
		if (!adaptiveTemplates.isEmpty()) {
			// /*
			// * iterate over templates
			// */
			// for (Template template : adaptiveTemplates) {
			// /*
			// * redirect to template-invocation-serializer
			// */
			// affectedConstructs.addAll(TemplateInvocationSerializer
			// .serialize(template, association, buffer));
			// }
			throw new UnsupportedOperationException("not implemented yet");
		}
		/*
		 * templates are empty
		 */
		else {
			/*
			 * create association definition block
			 */
			buffer.appendLine(
					true,
					writer.getCtmIdentity().getMainIdentifier(writer.getProperties(), association.getType()).toString(),
					BRO);

			boolean addComma = false;
			/*
			 * add all role-player-definitions
			 */
			for (Role role : association.getRoles()) {
				if (addComma) {
					buffer.appendLine(COMMA);
					addComma = false;
				}
				buffer.append(TABULATOR);
				RoleSerializer.serialize(writer, role, buffer);	
				addComma = true;
			}

			/*
			 * end role-player-list
			 */
			buffer.appendLine();
			buffer.appendLine(BRC);

			/*
			 * add scope-definition if exists
			 */
			if (ScopedSerializer.serialize(writer, association, buffer)) {
				buffer.appendLine();
			}

			/*
			 * add reifier-definition if exists
			 */
			if (ReifiableSerializer.serialize(writer, association, buffer)) {
				buffer.appendLine();
			}
		}
		return affectedConstructs;
	}

	// /**
	// * Returns a set of affected constructs, which will already exported
	// because of templates using wildcards. Please note the calling association
	// instance will never be contained.
	// * @return the set of affected constructs
	// */
	// public Set<Object> getAffectedConstructs() {
	// return affectedConstructs;
	// }
}
