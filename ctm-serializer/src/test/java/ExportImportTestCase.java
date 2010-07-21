import java.io.File;
import java.io.FileOutputStream;

import junit.framework.TestCase;
import net.ontopia.topicmaps.utils.ctm.CTMTopicMapReader;

import org.tmapi.core.TopicMap;
import org.tmapi.core.TopicMapSystem;
import org.tmapi.core.TopicMapSystemFactory;
import org.tmapix.io.LTMTopicMapReader;
import org.tmapix.io.XTMTopicMapReader;

import de.topicmapslab.ctm.writer.core.CTMTopicMapWriter;
import de.topicmapslab.ctm.writer.properties.CTMTopicMapWriterProperties;
import de.topicmapslab.ctm.writer.templates.Template;
import de.topicmapslab.ctm.writer.templates.TemplateFactory;

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
public class ExportImportTestCase extends TestCase {

	private TopicMap topicMap;
	private TopicMapSystem topicMapSystem;

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		topicMapSystem = TopicMapSystemFactory.newInstance()
				.newTopicMapSystem();
		topicMap = topicMapSystem
				.createTopicMap("http://de.topicmapslab/tmql4j/tests/opera-ltm");
//		LTMTopicMapReader reader = new LTMTopicMapReader(topicMap, new File(
//				"src/test/resources/ItalianOpera.ltm"));
		XTMTopicMapReader reader = new XTMTopicMapReader(topicMap, new File("src/test/resources/marc-tm-akis-example.xtm"));
		reader.read();
	}

	public void testExportImport() throws Exception {
		File file = new File("src/test/resources/ctm-opera.ctm");		
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

		final String qname = "ctm";
		final String baseLocator = "http://www.isotopicmaps.org/ctm/";
		writer.setPrefix(qname, baseLocator);
		writer.setPrefix("tml", "http://www.topicmapslab.de/");
		writer.write(topicMap);

		File file2 = new File("src/test/resources/ctm-mio.ctm");
		if (!file2.exists()) {
			file2.createNewFile();
		}
		// org.tinytim.mio.CTMTopicMapWriter writer2 = new
		// org.tinytim.mio.CTMTopicMapWriter(
		// new FileOutputStream(file2), "www.topicmapslab.de");
		// writer2.write(topicMap);

		System.out.println("Import from CTM...");

		CTMTopicMapReader reader = new CTMTopicMapReader(file);

		reader.read();

		TopicMap reimport = topicMapSystem
				.createTopicMap("http://de.topicmapslab/tmql4j/tests/reimport");

		org.tmapix.io.CTMTopicMapReader reader2 = new org.tmapix.io.CTMTopicMapReader(
				reimport, file);
		reader2.read();

		System.out.println("finished");

	}

	public void testname() throws Exception {
		final File file = new File("./output.ctm");
		if (file.exists()) {
			file.createNewFile();
		}
		final FileOutputStream outputStream = new FileOutputStream(file);
		final String baseURI = "http://tutorials.topicmapslab.de/ctm";
		final String line = "writer.features.export.itemidentifier = false, "
				+ "writer.features.prefixDetection.enabled = true, "
				+ "writer.features.templateDetection.enabled = true, "
				+ "writer.features.templateDetection.topicTemplates = true , "
				+ "writer.features.templateDetection.associationTemplates = true, "
				+ "writer.features.templateMerger.enabled = false";
		final CTMTopicMapWriter writer = new CTMTopicMapWriter(outputStream,
				baseURI, line);
		writer.write(topicMap);

		CTMTopicMapWriterProperties properties = new CTMTopicMapWriterProperties();
		properties.setEnginePrefix("urn:ontopia");
		properties.enableAssociationTemplateDetection(true);
		properties.parse(line);

		final String templateName = "myTemplate";
		TemplateFactory factory = writer.getFactory();
		Template template = factory.newTemplate(templateName);

		// change to new source version 1.0.5a1
		// /*
		// * create role entry for role-type composer
		// */
		// Locator locatorRTA =
		// topicMap.createLocator("http://psi.ontopedia.net/Composer");
		// Topic typeRTA = topicMap.getTopicBySubjectIdentifier(locatorRTA);
		// RoleEntry roleEntryA = new RoleEntry(typeRTA, "$composer");
		// /*
		// * create role entry for role-type work
		// */
		// Locator locatorRTB =
		// topicMap.createLocator("http://psi.ontopedia.net/Work");
		// Topic typeRTB = topicMap.getTopicBySubjectIdentifier(locatorRTB);
		// RoleEntry roleEntryB = new RoleEntry(typeRTB, "$work");
		// /*
		// * create association entry by type and role entries
		// */
		// Locator locator =
		// topicMap.createLocator("http://psi.ontopedia.net/composed_by");
		// Topic type = topicMap.getTopicBySubjectIdentifier(locator);
		// AssociationEntry entry = new AssociationEntry(type,roleEntryA,
		// roleEntryB);
		// /*
		// * create internal template
		// */
		// Template invoc = new Template("template-invoc");
		// /*
		// * add entries to internal template
		// */
		// NameEntry entry = new NameEntry("$name");
		// invoc.add(entry);
		// /*
		// * create template entry
		// */
		// TemplateEntry templateEntry = new TemplateEntry(template,
		// entry.getValueOrVariable());
		// /*
		// * add to template
		// */
		// template.add(templateEntry);

		// /*
		// * the file containing template definitions
		// */
		// final File file = new File("template.ctm");
		// /*
		// * read all templates from file
		// */
		// Set<Template> templates = Template.fromCTM(file);

		writer.addTemplate(template);

		System.out.println(template);

	}
}
