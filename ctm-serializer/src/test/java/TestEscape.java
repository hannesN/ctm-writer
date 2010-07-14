import java.io.File;
import java.io.FileOutputStream;

import junit.framework.TestCase;

import org.tmapi.core.TopicMap;
import org.tmapi.core.TopicMapSystem;
import org.tmapi.core.TopicMapSystemFactory;

import de.topicmapslab.ctm.writer.core.CTMTopicMapWriter;

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
public class TestEscape extends TestCase {

	private TopicMap topicMap;
	private TopicMapSystem topicMapSystem;

	public void testHashEscape() throws Exception {
		topicMapSystem = TopicMapSystemFactory.newInstance()
				.newTopicMapSystem();
		topicMap = topicMapSystem
				.createTopicMap("http://de.topicmapslab/tmql4j/tests/opera-ltm");
		
		topicMap.createTopicBySubjectIdentifier(topicMap.createLocator("http://psi.oasis-open.org/iso/639/#jpn"));

		File file = new File("src/test/resources/escape.ctm");
		if (!file.exists()) {
			file.createNewFile();
		}
		final String line = "writer.features.export.itemidentifier = false, "
				+ "writer.features.prefixDetection.enabled = false, "
				+ "writer.features.templateDetection.enabled = false, "
				+ "writer.features.templateDetection.topicTemplates = false , "
				+ "writer.features.templateDetection.associationTemplates = false, "
				+ "writer.features.templateMerger.enabled = false";
		CTMTopicMapWriter writer = new CTMTopicMapWriter(new FileOutputStream(
				file), "www.topicmapslab.de", line);
		writer.write(topicMap);
	}
}
