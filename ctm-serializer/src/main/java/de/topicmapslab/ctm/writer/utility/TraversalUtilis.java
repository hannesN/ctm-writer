package de.topicmapslab.ctm.writer.utility;

import java.util.HashSet;
import java.util.Set;

import org.tmapi.core.Association;
import org.tmapi.core.Role;
import org.tmapi.core.Topic;
import org.tmapi.index.TypeInstanceIndex;

public class TraversalUtilis {

	public static Set<Topic> getTraversalPlayers(Topic topic, Topic type) {
		/*
		 * check if construct is a topic
		 */
		Set<Association> associations = new HashSet<Association>();
		Set<Association> associations_ = new HashSet<Association>();

		TypeInstanceIndex index = topic.getTopicMap().getIndex(TypeInstanceIndex.class);
		if ( !index.isOpen()){
			index.open();
		}
		associations_.addAll(index.getAssociations(type));

		/*
		 * iterate over all associations and extract associations played by
		 * given topic
		 */
		for (Association a : associations_) {
			for (Role role : a.getRoles()) {
				if (role.getPlayer().equals(topic)) {
					associations.add(a);
					break;
				}
			}
		}
		/*
		 * create new instance of tuple-sequence
		 */
		Set<Topic> set = new HashSet<Topic>();
		/*
		 * iterate over all associations and extract players
		 */
		for (Association association : associations) {
			for (Role role : association.getRoles()) {
				if (!role.getPlayer().equals(topic)) {
					set.add(role.getPlayer());
				}
			}
		}
		return set;
	}
	
	public static Set<Association> getTraversalAssociations(Topic topic, Association association) {
		/*
		 * check if construct is a topic
		 */
		Set<Association> associations = new HashSet<Association>();
		Set<Association> associations_ = new HashSet<Association>();
		TypeInstanceIndex index = topic.getTopicMap().getIndex(TypeInstanceIndex.class);
		if ( !index.isOpen()){
			index.open();
		}
		associations_.addAll(index.getAssociations(association.getType()));

		/*
		 * iterate over all associations and extract associations played by
		 * given topic
		 */
		for (Association a : associations_) {
			for (Role role : a.getRoles()) {
				if (role.getPlayer().equals(topic)) {
					associations.add(a);
					break;
				}
			}
		}
		associations.remove(association);
		return associations;
	}

}
