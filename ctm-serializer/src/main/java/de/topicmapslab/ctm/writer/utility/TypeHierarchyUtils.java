/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/    
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 *
 */
package de.topicmapslab.ctm.writer.utility;

import java.util.HashSet;
import java.util.Set;

import org.tmapi.core.Association;
import org.tmapi.core.ModelConstraintException;
import org.tmapi.core.Role;
import org.tmapi.core.Topic;
import org.tmapi.core.TopicMap;
import org.tmapi.index.TypeInstanceIndex;

import de.topicmapslab.identifier.TmdmSubjectIdentifier;

/**
 * Utility class to handle type hierarchy of topic maps
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 * 
 */
public class TypeHierarchyUtils {

	/**
	 * hidden constructor
	 */
	private TypeHierarchyUtils() {

	}

	/**
	 * Static method to extract all super-types of the given type by looking for
	 * special association items.
	 * 
	 * @param subtype
	 *            the type
	 * @return a {@link Set} of all super-types
	 * @throws ModelConstraintException
	 *             thrown if TMDM default association type or role types not
	 *             found
	 */
	public static Set<Topic> getSupertypes(final Topic subtype)
			throws ModelConstraintException {
		Set<Topic> supertypes = new HashSet<Topic>();
		TopicMap topicMap = subtype.getTopicMap();

		/*
		 * get supertype-subtype association type
		 */
		Topic kindOf = topicMap
				.getTopicBySubjectIdentifier(topicMap
						.createLocator(TmdmSubjectIdentifier.TMDM_SUPERTYPE_SUBTYPE_ASSOCIATION));

		if (kindOf != null) {
			/*
			 * get subtype-role type
			 */
			Topic subtypeRole = topicMap
					.getTopicBySubjectIdentifier(topicMap
							.createLocator(TmdmSubjectIdentifier.TMDM_SUBTYPE_ROLE_TYPE));
			/*
			 * get supertype-role type
			 */
			Topic supertypeRole = topicMap
					.getTopicBySubjectIdentifier(topicMap
							.createLocator(TmdmSubjectIdentifier.TMDM_SUPERTYPE_ROLE_TYPE));

			/*
			 * check if exists
			 */
			if (subtypeRole == null || supertypeRole == null) {
				new ModelConstraintException(
						kindOf,
						"Invalid association item of type 'supertype-subtype' - unexprected role types.");
			}

			/*
			 * get type-instance-index
			 */
			TypeInstanceIndex index = topicMap
					.getIndex(TypeInstanceIndex.class);
			if ( !index.isOpen()){
				index.open();
			}			
			/*
			 * iterate over all association items
			 */
			for (Association association : index.getAssociations(kindOf)) {
				Set<Role> subtypePlayers = association.getRoles(subtypeRole);
				if (subtypePlayers.size() != 1) {
					throw new ModelConstraintException(
							association,
							"Invalid association item of type 'supertype-subtype' - expected number of players of role-type 'subtype' is 1, but was"
									+ subtypePlayers.size());
				}
				/*
				 * extract player of subtype-role
				 */
				Topic subtypePlayer = subtypePlayers.iterator().next()
						.getPlayer();
				/*
				 * check if player equals given type
				 */
				if (subtypePlayer.equals(subtype)) {
					Set<Role> supertypePlayers = association
							.getRoles(supertypeRole);
					if (supertypePlayers.size() != 1) {
						throw new ModelConstraintException(
								association,
								"Invalid association item of type 'supertype-subtype' - expected number of players of role-type 'supertype' is 1, but was"
										+ supertypePlayers.size());
					}
					/*
					 * add supertype-role player
					 */
					supertypes.add(supertypePlayers.iterator().next()
							.getPlayer());
				}
			}
		}
		return supertypes;
	}

}
