import java.io.File;

import junit.framework.TestCase;

import org.tmapi.core.TopicMap;
import org.tmapi.core.TopicMapSystem;
import org.tmapi.core.TopicMapSystemFactory;
import org.tmapix.io.LTMTopicMapReader;

import de.topicmapslab.common.tools.prefix.core.PrefixIdentifier;

/** 
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/    
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 *
 */
/**
 * @author Sven
 * 
 */
public class CTMPrefixIdentifierTest extends TestCase {

	private TopicMap topicMap;

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		TopicMapSystem topicMapSystem = TopicMapSystemFactory.newInstance()
				.newTopicMapSystem();
		topicMap = topicMapSystem
				.createTopicMap("http://de.topicmapslab/tmql4j/tests/opera-ltm");
		LTMTopicMapReader reader = new LTMTopicMapReader(topicMap, new File(
				"src/test/resources/ItalianOpera.ltm"));
		reader.read();
	}

	/**
	 * Test method for
	 * {@link de.topicmapslab.common.tools.prefix.core.PrefixIdentifier#identifyURIs(org.tmapi.core.TopicMap)}
	 * .
	 */
	public final void testIdentifyPrefixes() throws Exception {
		System.out.println(PrefixIdentifier.identifyURIs(topicMap));
	}

	/**
	 * Test method for
	 * {@link de.topicmapslab.common.tools.prefix.core.PrefixIdentifier#prefixMap(org.tmapi.core.TopicMap)}
	 * .
	 */
	public final void testPrefixMap() throws Exception {
		System.out.println(PrefixIdentifier.prefixMap(topicMap));
	}
}
