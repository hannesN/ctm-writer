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
import static de.topicmapslab.ctm.writer.utility.TMDMIdentifier.INSTANCE_OF_TYPE;
import static de.topicmapslab.ctm.writer.utility.TMDMIdentifier.INSTANCE_ROLE;
import static de.topicmapslab.ctm.writer.utility.TMDMIdentifier.TYPE_ROLE;

import java.util.HashSet;
import java.util.Set;

import org.tmapi.core.Association;
import org.tmapi.core.ModelConstraintException;
import org.tmapi.core.Role;
import org.tmapi.core.Topic;
import org.tmapi.core.TopicMap;
import org.tmapi.index.TypeInstanceIndex;

import de.topicmapslab.ctm.writer.exception.SerializerException;
import de.topicmapslab.ctm.writer.properties.CTMTopicMapWriterProperties;
import de.topicmapslab.ctm.writer.utility.CTMBuffer;
import de.topicmapslab.ctm.writer.utility.CTMIdentity;

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
	 * a set of constructs which are affected by template-invocations
	 */
	private final Set<Object> affectedConstructs = new HashSet<Object>();

	/**
	 * the internal properties
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
	 * @param affectedConstructs
	 *            a set of constructs which are affected by template-invocations
	 * 
	 */
	public IsInstanceOfSerializer(CTMTopicMapWriterProperties properties, CTMIdentity ctmIdentity,
			Set<Object> affectedConstructs) {
		this.affectedConstructs.addAll(affectedConstructs);
		this.properties = properties;
		this.ctmIdentity = ctmIdentity;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean serialize(Topic instance, CTMBuffer buffer)
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
				buffer.appendTailLine(true, TABULATOR, ISA, ctmIdentity
						.getMainIdentifier(properties, type).toString());
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
		Topic instanceOf = topicMap.getTopicBySubjectIdentifier(topicMap
				.createLocator(INSTANCE_OF_TYPE));

		if (instanceOf != null) {
			/*
			 * get TMDM instance-role type
			 */
			Topic instanceRole = topicMap.getTopicBySubjectIdentifier(topicMap
					.createLocator(INSTANCE_ROLE));
			/*
			 * get TMDM type-role type
			 */
			Topic typeRole = topicMap.getTopicBySubjectIdentifier(topicMap
					.createLocator(TYPE_ROLE));

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
					// buffer.appendTailLine(true, TABULATOR, ISA, CTMIdentity
					// .getPrefixedIdentity(typePlayers.iterator().next()
					// .getPlayer()));
					buffer.appendTailLine(true, TABULATOR, ISA, ctmIdentity
							.getMainIdentifier(properties, typePlayers
									.iterator().next().getPlayer()).toString());
				}
			}
			returnValue = true;
		}

		return returnValue;
	}

}
