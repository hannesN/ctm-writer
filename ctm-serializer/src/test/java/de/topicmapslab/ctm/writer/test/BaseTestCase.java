/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/    
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 *  
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 *
 */
package de.topicmapslab.ctm.writer.test;

import junit.framework.TestCase;

import org.tmapi.core.Association;
import org.tmapi.core.Locator;
import org.tmapi.core.Topic;
import org.tmapi.core.TopicMap;
import org.tmapi.core.TopicMapSystemFactory;

import de.topicmapslab.identifier.TmdmSubjectIdentifier;

/**
 * Abstract base test class for TMQL4J test suite
 * 
 * @author Sven Krosse
 * 
 */
public abstract class BaseTestCase extends TestCase {
	protected TopicMap topicMap;
	protected TopicMapSystemFactory factory;
	protected final String base = "http://psi.example.org/test/";

	/**
	 * {@inheritDoc}
	 */
	protected void setUp() throws Exception {
		factory = TopicMapSystemFactory.newInstance();
		topicMap = factory.newTopicMapSystem().createTopicMap(base);		
	}

	/**
	 * {@inheritDoc}
	 */
	protected void tearDown() throws Exception {
		topicMap.close();
	}

	/**
	 * Create a locator
	 * 
	 * @param reference
	 *            the locator reference
	 * @return the locator
	 */
	protected Locator createLocator(final String reference) {
		return topicMap.createLocator(base + reference);
	}

	/**
	 * create a topic without any identity
	 * 
	 * @return the topic
	 */
	protected Topic createTopic() {
		return topicMap.createTopic();
	}

	/**
	 * create a topic with an item-identifier
	 * 
	 * @param reference
	 *            the reference of the item-identifier
	 * @return the created topic
	 */
	protected Topic createTopicByII(String reference) {
		return topicMap.createTopicByItemIdentifier(createLocator(reference));
	}

	/**
	 * create a topic with an subject-identifier
	 * 
	 * @param reference
	 *            the reference of the subject-identifier
	 * @return the created topic
	 */
	protected Topic createTopicBySI(String reference) {
		return topicMap
				.createTopicBySubjectIdentifier(createLocator(reference));
	}

	/**
	 * create a topic with an subject-locator
	 * 
	 * @param reference
	 *            the reference of the subject-locator
	 * @return the created topic
	 */
	protected Topic createTopicBySL(String reference) {
		return topicMap.createTopicBySubjectLocator(createLocator(reference));
	}

	/**
	 * create an association
	 * 
	 * @param type
	 *            the association type
	 * @return the created association
	 */
	protected Association createAssociation(final Topic type) {
		return topicMap.createAssociation(type, new Topic[0]);
	}

	/**
	 * create an association with a generated type
	 * 
	 * @return the created association
	 */
	protected Association createAssociation() {
		return topicMap.createAssociation(createTopic(), new Topic[0]);
	}

	protected void addSupertype(Topic type, Topic supertype) {
		Association association = createAssociation(topicMap
				.createTopicBySubjectIdentifier(topicMap
						.createLocator(TmdmSubjectIdentifier.TMDM_SUPERTYPE_SUBTYPE_ASSOCIATION)));
		association
				.createRole(
						topicMap
								.createTopicBySubjectIdentifier(topicMap
										.createLocator(TmdmSubjectIdentifier.TMDM_SUPERTYPE_ROLE_TYPE)),
						supertype);
		association.createRole(topicMap.createTopicBySubjectIdentifier(topicMap
				.createLocator(TmdmSubjectIdentifier.TMDM_SUBTYPE_ROLE_TYPE)),
				type);
	}
}
