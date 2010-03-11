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

import de.topicmapslab.ctm.writer.exception.SerializerException;
import de.topicmapslab.ctm.writer.properties.CTMTopicMapWriterProperties;
import de.topicmapslab.ctm.writer.utility.CTMBuffer;
import de.topicmapslab.ctm.writer.utility.CTMIdentity;

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
	 * properties for CTM topic map writer
	 */
	private final CTMTopicMapWriterProperties properties;
	
	/**
	 * identity utility (cache and generator)
	 */
	private final CTMIdentity ctmIdentity;
	
	/**
	 * constructor
	 * 
	 * @param properties
	 *            the internal {@link CTMTopicMapWriterProperties} *
	 */
	public RoleSerializer(CTMTopicMapWriterProperties properties, CTMIdentity ctmIdentity) {
		this.properties = properties;
		this.ctmIdentity = ctmIdentity;
	}
	/**
	 * {@inheritDoc}
	 */
	public boolean serialize(Role role, CTMBuffer buffer)
			throws SerializerException {

		buffer.append(true, ctmIdentity.getMainIdentifier(properties,role.getType()).toString(),
				COLON, ctmIdentity.getMainIdentifier(properties,role.getPlayer()).toString());

		new ReifiableSerializer(properties, ctmIdentity).serialize(role, buffer);

		return true;
	}

}
