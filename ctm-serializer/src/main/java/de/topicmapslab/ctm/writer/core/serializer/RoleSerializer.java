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

import java.io.IOException;

import org.tmapi.core.Role;

import de.topicmapslab.ctm.writer.core.CTMTopicMapWriter;
import de.topicmapslab.ctm.writer.exception.SerializerException;
import de.topicmapslab.ctm.writer.utility.ICTMWriter;

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
	 * Method to convert the given construct to its specific CTM string. The
	 * result should be written to the given output buffer.
	 * 
	 * @param writer
	 *            the CTM writer
	 * @param role
	 *            the role to serialize
	 * @param buffer
	 *            the output buffer
	 * @return <code>true</code> if new content was written into buffer,
	 *         <code>false</code> otherwise
	 * @throws SerializerException
	 *             Thrown if serialization failed.
	 */
	public static boolean serialize(CTMTopicMapWriter writer, Role role,
			ICTMWriter buffer) throws SerializerException, IOException {

		buffer.append(true, writer.getCtmIdentity().getMainIdentifier(
				writer.getProperties(), role.getType()).toString(), COLON,
				writer.getCtmIdentity().getMainIdentifier(
						writer.getProperties(), role.getPlayer()).toString());

		ReifiableSerializer.serialize(writer, role, buffer);

		return true;
	}

}
