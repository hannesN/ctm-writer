import java.io.File;

import junit.framework.TestCase;

import org.tmapi.core.TopicMap;
import org.tmapi.core.TopicMapSystem;
import org.tmapi.core.TopicMapSystemFactory;
import org.tmapix.io.LTMTopicMapReader;

import de.topicmapslab.ctm.writer.properties.CTMTopicMapWriterProperties;
import de.topicmapslab.ctm.writer.templates.Template;
import de.topicmapslab.ctm.writer.templates.TemplateMerger;
import de.topicmapslab.ctm.writer.templates.TemplateSerializer;
import de.topicmapslab.ctm.writer.templates.autodetection.TemplateDetection;
import de.topicmapslab.ctm.writer.utility.CTMBuffer;
import de.topicmapslab.ctm.writer.utility.CTMIdentity;

/** 
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/    
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 *
 */

/**
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 * 
 */
public class TemplateMergerTestCase extends TestCase {

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
	 * {@link de.topicmapslab.ctm.writer.templates.TemplateMerger#mergeTemplates(java.util.Set)}
	 * .
	 * @deprecated
	 */
	public final void testMergeTemplates() throws Exception {
//		CTMTopicMapWriterProperties properties = new CTMTopicMapWriterProperties();
//		TemplateDetection detection = new TemplateDetection(properties,new CTMIdentity(), topicMap);
//		for (Template template : new TemplateMerger(properties).mergeTemplates(detection
//				.tryToDetectTemplates())) {
//			CTMBuffer buffer = new CTMBuffer();
//			new TemplateSerializer(template).serialize(buffer);
//			System.out.println(buffer.getBuffer().toString());
//		}
	}

}
