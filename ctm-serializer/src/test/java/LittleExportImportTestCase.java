import java.io.File;
import java.io.FileOutputStream;

import junit.framework.Assert;
import junit.framework.TestCase;
import net.ontopia.topicmaps.core.TopicMapIF;
import net.ontopia.topicmaps.utils.ctm.CTMTopicMapReader;

import org.tmapi.core.Association;
import org.tmapi.core.Locator;
import org.tmapi.core.Name;
import org.tmapi.core.Occurrence;
import org.tmapi.core.Topic;
import org.tmapi.core.TopicMap;
import org.tmapi.core.TopicMapSystem;
import org.tmapi.core.TopicMapSystemFactory;
import org.tmapi.core.Variant;

import de.topicmapslab.identifier.TmdmSubjectIdentifier;

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
public class LittleExportImportTestCase extends TestCase {

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

		Topic reifier = topicMap.createTopicBySubjectIdentifier(topicMap
				.createLocator("http://reifier"));
		topicMap.setReifier(reifier);

		Topic occType = topicMap.createTopic();
		occType.addSubjectIdentifier(topicMap
				.createLocator("http://de.topicmapslab/occ-type"));

		Topic typeA = topicMap.createTopic();
		typeA.addItemIdentifier(topicMap
				.createLocator("http://de.topicmapslab/type"));
		typeA.addSubjectIdentifier(topicMap
				.createLocator("http://de.topicmapslab/si-type"));
		typeA.addSubjectLocator(topicMap
				.createLocator("http://de.topicmapslab/sl-type"));

		Topic theme = topicMap.createTopicBySubjectIdentifier(topicMap
				.createLocator("http://de.topicmapslab/theme"));
		Topic theme2 = topicMap.createTopicBySubjectIdentifier(topicMap
				.createLocator("http://de.topicmapslab/theme2"));

		Topic instanceA = topicMap.createTopicBySubjectIdentifier(topicMap
				.createLocator("http://de.topicmapslab/instanceA"));
		instanceA.addSubjectIdentifier(topicMap
				.createLocator("http://de.topicmapslab/instanceA"));
		instanceA.addSubjectIdentifier(topicMap
				.createLocator("http://de.topicmapslab/si-instanceA"));
		instanceA.addSubjectLocator(topicMap
				.createLocator("http://de.topicmapslab/sl-instanceA"));
		instanceA.addType(typeA);
		Topic reifierOcc = topicMap.createTopicBySubjectIdentifier(topicMap
				.createLocator("http://de.topicmapslab/reifier-occ"));
		Occurrence occ = instanceA
				.createOccurrence(
						occType,
						"http://google.de/occurrence",
						topicMap
								.createLocator("http://www.w3.org/2001/XMLSchema#anyURI"),
						theme);
		instanceA
				.createOccurrence(
						occType,
						"This is a quoted \"occurrence\".",
						topicMap
								.createLocator("http://www.w3.org/2001/XMLSchema#string"),
						theme);
		instanceA
				.createOccurrence(
						occType,
						"äüößÜÄÖ€@âéè",
						topicMap
								.createLocator("http://www.w3.org/2001/XMLSchema#string"),
						theme);
		occ.setReifier(reifierOcc);

		Topic reifierName = topicMap.createTopicBySubjectIdentifier(topicMap
				.createLocator("http://de.topicmapslab/reifier-name"));
		Name name = instanceA.createName("name", theme, theme2);
		name.setReifier(reifierName);

		Topic reifierVariant = topicMap.createTopicBySubjectIdentifier(topicMap
				.createLocator("http://de.topicmapslab/reifier-variant"));
		Name name2 = instanceA.createName("name2", new Topic[0]);
		Variant v = name2
				.createVariant(
						"http://google.de/variant.jpg",
						topicMap
								.createLocator("http://www.w3.org/2001/XMLSchema#anyURI"),
						theme);
		v.setReifier(reifierVariant);

		Topic roleA = topicMap.createTopic();
		roleA.addSubjectIdentifier(topicMap
				.createLocator("http://de.topicmapslab/role-type-a"));

		Topic playerA = topicMap.createTopic();
		playerA.addSubjectIdentifier(topicMap
				.createLocator("http://de.topicmapslab/player-a"));

		Topic roleB = topicMap.createTopic();
		roleB.addSubjectIdentifier(topicMap
				.createLocator("http://de.topicmapslab/role-type-b"));

		Topic playerB = topicMap.createTopic();
		playerB.addSubjectIdentifier(topicMap
				.createLocator("http://de.topicmapslab/player-b"));

		Association associationA = topicMap.createAssociation(typeA,
				new Topic[0]);

		associationA.createRole(roleA, playerA);
		associationA.createRole(roleB, playerB);

	}

	public void testExportImport() throws Exception {
		File file = new File("src/test/resources/ctm2.ctm");
		if (!file.exists()) {
			file.createNewFile();
		}
		final String line = "writer.features.export.itemidentifier = false, "
				+ "writer.features.prefixDetection.enabled = false, "
				+ "writer.features.templateDetection.enabled = false, "
				+ "writer.features.templateDetection.topicTemplates = false , "
				+ "writer.features.templateDetection.associationTemplates = false, "
				+ "writer.features.templateMerger.enabled = false";
		de.topicmapslab.ctm.writer.core.CTMTopicMapWriter writer = new de.topicmapslab.ctm.writer.core.CTMTopicMapWriter(
				new FileOutputStream(file), "www.topicmapslab.de", line);
		writer.write(topicMap);

		File file2 = new File("src/test/resources/ctm-mio.ctm");
		if (!file2.exists()) {
			file2.createNewFile();
		}
		CTMTopicMapReader reader = new CTMTopicMapReader(file);
		reader.read();

	}

//	public void testname() throws Exception {
//		File file = new File(
//				"C:/Dokumente und Einstellungen/Sven/Eigene Dateien/ICQ/263017971/ReceivedFiles/84633702 Uta/toyTM_after_spec.ctm");
//		CTMTopicMapReader reader = new CTMTopicMapReader(file);
//		TopicMapIF tmif = reader.read();
//		Assert.assertEquals(89, tmif.getTopics().size());
//		TopicMap reimport = topicMapSystem
//				.createTopicMap("http://de.topicmapslab/tmql4j/tests/reimport");
//		org.tmapix.io.CTMTopicMapReader r = new org.tmapix.io.CTMTopicMapReader(
//				reimport, file);
//		r.read();
//		Assert.assertEquals(89, reimport.getTopics().size());
//	}

	public void testFallback() throws Exception {
		TopicMap tm = topicMapSystem.createTopicMap("http://fallback-test");
		Topic t = tm.createTopic();

		Locator l = tm
				.createLocator(TmdmSubjectIdentifier.TMDM_SUPERTYPE_SUBTYPE_ASSOCIATION);
		Topic assType = tm.getTopicBySubjectIdentifier(l);
		if (assType == null) {
			assType = tm.createTopicBySubjectIdentifier(l);
		}

		Association a = tm.createAssociation(assType);
		l = tm.createLocator(TmdmSubjectIdentifier.TMDM_SUPERTYPE_ROLE_TYPE);
		Topic supert = tm.getTopicBySubjectIdentifier(l);
		if (supert == null) {
			supert = tm.createTopicBySubjectIdentifier(l);
		}
		a.createRole(supert, t);
		l = tm.createLocator(TmdmSubjectIdentifier.TMDM_SUBTYPE_ROLE_TYPE);
		Topic subt = tm.getTopicBySubjectIdentifier(l);
		if (subt == null) {
			subt = tm.createTopicBySubjectIdentifier(l);
		}
		a.createRole(subt, t);

		File file = new File("src/test/resources/ctm.ctm");
		if (!file.exists()) {
			file.createNewFile();
		}
		de.topicmapslab.ctm.writer.core.CTMTopicMapWriter writer = new de.topicmapslab.ctm.writer.core.CTMTopicMapWriter(
				new FileOutputStream(file), "www.topicmapslab.de");
		writer.write(tm);

	}

	public void testbla() throws Exception {
		File file = new File("src/test/resources/ctm.ctm");

		TopicMap tm = TopicMapSystemFactory.newInstance().newTopicMapSystem()
				.createTopicMap("http://de.topicmapslab/tmql4j/blablub");

		org.tmapix.io.CTMTopicMapReader reader = new org.tmapix.io.CTMTopicMapReader(
				tm, file);
		reader.read();

	}
}
