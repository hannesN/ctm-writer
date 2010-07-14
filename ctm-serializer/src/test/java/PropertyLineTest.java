import junit.framework.Assert;
import junit.framework.TestCase;
import de.topicmapslab.ctm.writer.properties.CTMTopicMapWriterProperties;

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
public class PropertyLineTest extends TestCase {

	public void testPropertyLineParser() throws Exception {
		CTMTopicMapWriterProperties properties = new CTMTopicMapWriterProperties();

		Assert
				.assertEquals(false, properties
						.isExportOfItemIdentifierEnabled());
		Assert.assertEquals(false, properties.isPrefixDetectionEnabled());
		Assert.assertEquals(false, properties.isTemplateDetectionEnabled());
		Assert.assertEquals(false, properties.isTemplateMergerEnabled());
		Assert.assertEquals(false, properties
				.isTopicTemplateDetectionSupported());
		Assert.assertEquals(false, properties
				.isAssociationTemplateDetectionSupported());

		final String line = "writer.features.export.itemidentifier = true, "
				+ "writer.features.prefixDetection.enabled = false, "
				+ "writer.features.templateDetection.enabled = false, "
				+ "writer.features.templateDetection.topicTemplates = false , "
				+ "writer.features.templateDetection.associationTemplates = false, "
				+ "writer.features.templateMerger.enabled = false";
		properties.parse(line);

		Assert.assertEquals(true, properties.isExportOfItemIdentifierEnabled());
		Assert.assertEquals(false, properties.isPrefixDetectionEnabled());
		Assert.assertEquals(false, properties.isTemplateDetectionEnabled());
		Assert.assertEquals(false, properties.isTemplateMergerEnabled());
		Assert.assertEquals(false, properties
				.isTopicTemplateDetectionSupported());
		Assert.assertEquals(false, properties
				.isAssociationTemplateDetectionSupported());
	}

}
