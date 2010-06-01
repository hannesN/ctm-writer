/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/    
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 *
 */
package de.topicmapslab.ctm.writer.core.serializer;

import static de.topicmapslab.ctm.writer.utility.CTMTokens.ISA;
import static de.topicmapslab.ctm.writer.utility.CTMTokens.TABULATOR;

import java.util.Set;

import org.tmapi.core.Association;
import org.tmapi.core.ModelConstraintException;
import org.tmapi.core.Role;
import org.tmapi.core.Topic;
import org.tmapi.core.TopicMap;
import org.tmapi.index.TypeInstanceIndex;

import de.topicmapslab.ctm.writer.core.CTMTopicMapWriter;
import de.topicmapslab.ctm.writer.exception.SerializerException;
import de.topicmapslab.ctm.writer.utility.CTMBuffer;
import de.topicmapslab.identifier.TmdmSubjectIdentifier;

/**
 * Class to realize the serialization of the following CTM grammar rule. <br />
 * <br />
 * <code>instance-of ::= 'isa' topic-ref</code><br />
 * <br />
 * The serialized CTM string represents the type-instance-relation of a topic
 * within the topic block.
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 * 
 */
public class IsInstanceOfSerializer implements ISerializer<Topic> {

	/**
	 * Method to convert the given construct to its specific CTM string. The
	 * result should be written to the given output buffer.
	 * 
	 * @param writer
	 *            the CTM writer
	 * @param affectedConstructs
	 *            the constructs affected by upper templates
	 * @param instance
	 *            the instance to serialize
	 * @param buffer
	 *            the output buffer
	 * @return <code>true</code> if new content was written into buffer,
	 *         <code>false</code> otherwise
	 * @throws SerializerException
	 *             Thrown if serialization failed.
	 */
	public static boolean serialize(CTMTopicMapWriter writer,
			Set<Object> affectedConstructs, Topic instance, CTMBuffer buffer)
			throws SerializerException {

		boolean returnValue = false;

		/*
		 * iterate over all types given known about getTypes
		 */
		for (Topic type : instance.getTypes()) {
			/*
			 * add to buffer
			 */
			if (!affectedConstructs.contains(type)) {
				buffer.appendTailLine(true, TABULATOR, ISA, writer
						.getCtmIdentity().getMainIdentifier(
								writer.getProperties(), type).toString());
				returnValue = true;
			}
		}

		/*
		 * check additional types by extracting the TMDM association type
		 */
		TopicMap topicMap = instance.getTopicMap();

		/*
		 * get TMDM type-instance-association type
		 */
		Topic instanceOf = topicMap
				.getTopicBySubjectIdentifier(topicMap
						.createLocator(TmdmSubjectIdentifier.TMDM_TYPE_INSTANCE_ASSOCIATION_TYPE));

		if (instanceOf != null) {
			/*
			 * get TMDM instance-role type
			 */
			Topic instanceRole = topicMap
					.getTopicBySubjectIdentifier(topicMap
							.createLocator(TmdmSubjectIdentifier.TMDM_INSTANCE_ROLE_TYPE));
			/*
			 * get TMDM type-role type
			 */
			Topic typeRole = topicMap.getTopicBySubjectIdentifier(topicMap
					.createLocator(TmdmSubjectIdentifier.TMDM_TYPE_ROLE_TYPE));

			/*
			 * check if exists
			 */
			if (instanceRole == null || typeRole == null) {
				throw new SerializerException(
						new ModelConstraintException(instanceOf,
								"Invalid association item of type 'is-instance-of' - unexprected role types."));
			}

			/*
			 * get type-instance-index
			 */
			TypeInstanceIndex index = topicMap
					.getIndex(TypeInstanceIndex.class);
			/*
			 * iterate over association items
			 */
			for (Association association : index.getAssociations(instanceOf)) {
				/*
				 * extract instance-role player
				 */
				Set<Role> instancePlayers = association.getRoles(instanceRole);
				if (instancePlayers.size() != 1) {
					throw new SerializerException(
							new ModelConstraintException(
									association,
									"Invalid association item of type 'is-instance-of' - expected number of players of role-type 'instance' is 1, but was"
											+ instancePlayers.size()));
				}
				Topic instancePlayer = instancePlayers.iterator().next()
						.getPlayer();
				if (instancePlayer.equals(instance)) {
					Set<Role> typePlayers = association.getRoles(typeRole);
					if (typePlayers.size() != 1) {
						throw new SerializerException(
								new ModelConstraintException(
										association,
										"Invalid association item of type 'is-instance-of' - expected number of players of role-type 'type' is 1, but was"
												+ typePlayers.size()));
					}
					/*
					 * add to buffer
					 */
					buffer.appendTailLine(true, TABULATOR, ISA, writer
							.getCtmIdentity().getMainIdentifier(
									writer.getProperties(),
									typePlayers.iterator().next().getPlayer())
							.toString());
				}
			}
			returnValue = true;
		}

		return returnValue;
	}

}
