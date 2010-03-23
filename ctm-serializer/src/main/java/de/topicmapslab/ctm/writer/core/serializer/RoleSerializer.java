/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/    
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 *
 */
package de.topicmapslab.ctm.writer.core.serializer;

import static de.topicmapslab.ctm.writer.utility.CTMTokens.COLON;

import org.tmapi.core.Role;

import de.topicmapslab.ctm.writer.core.CTMTopicMapWriter;
import de.topicmapslab.ctm.writer.exception.SerializerException;
import de.topicmapslab.ctm.writer.utility.CTMBuffer;

/**
 * Class to realize the serialization of the following CTM grammar rule. <br />
 * <br />
 * <code>	role ::= type (WS ':' WS*  | WS*  ':' WS) player  reifier  ?   </code><br />
 * <br />
 * The serialized CTM string represents the a role-player definition as a part
 * of an association definition block.
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 * 
 */
public class RoleSerializer implements ISerializer<Role> {

	/**
	 * identity utility (cache and generator)
	 */
	private final CTMTopicMapWriter writer;

	/**
	 * constructor
	 * 
	 * @param writer
	 *            the parent topic map writer
	 */
	public RoleSerializer(CTMTopicMapWriter writer) {
		this.writer = writer;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean serialize(Role role, CTMBuffer buffer)
			throws SerializerException {

		buffer.append(true, writer.getCtmIdentity().getMainIdentifier(
				writer.getProperties(), role.getType()).toString(), COLON,
				writer.getCtmIdentity().getMainIdentifier(
						writer.getProperties(), role.getPlayer()).toString());

		new ReifiableSerializer(writer).serialize(role, buffer);

		return true;
	}

}
