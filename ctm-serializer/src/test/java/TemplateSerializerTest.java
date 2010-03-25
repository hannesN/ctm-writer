import junit.framework.TestCase;

import org.tmapi.core.Topic;
import org.tmapi.core.TopicMap;
import org.tmapi.core.TopicMapSystemFactory;

import de.topicmapslab.ctm.writer.core.CTMTopicMapWriter;
import de.topicmapslab.ctm.writer.templates.Template;
import de.topicmapslab.ctm.writer.templates.TemplateFactory;
import de.topicmapslab.ctm.writer.templates.entry.AssociationEntry;
import de.topicmapslab.ctm.writer.templates.entry.RoleEntry;
import de.topicmapslab.ctm.writer.templates.entry.TopicEntry;
import de.topicmapslab.ctm.writer.templates.entry.param.TopicTypeParam;
import de.topicmapslab.ctm.writer.templates.entry.param.VariableParam;
import de.topicmapslab.ctm.writer.templates.entry.param.WildcardParam;

/**
 * CTM Writer
 * 
 * Copyright: Copyright 2009 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/    
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 *
 */

/**
 * @author Sven Krosse
 * 
 */
public class TemplateSerializerTest extends TestCase {

	 public void testUsingWildcards() throws Exception {
	
	 /*
	 * the topic map to serialize
	 */
	 TopicMap map = TopicMapSystemFactory.newInstance().newTopicMapSystem()
	 .createTopicMap("http://code.google.com/p/ctm-writer");
	
	 /*
	 * define topic types used in template context
	 */
	 Topic overlapDecType = map
	 .createTopicBySubjectIdentifier(map
	 .createLocator("http://www.isotopicmaps.org/tmcl/overlap-declaration"));
	 Topic overlapsType = map.createTopicBySubjectIdentifier(map
	 .createLocator("http://www.isotopicmaps.org/tmcl/overlaps"));
	 Topic allowsType = map.createTopicBySubjectIdentifier(map
	 .createLocator("http://www.isotopicmaps.org/tmcl/allows"));
	 Topic allowedType = map.createTopicBySubjectIdentifier(map
	 .createLocator("http://www.isotopicmaps.org/tmcl/allowed"));
	
	 /*
	 * create the topic map writer
	 */
	 CTMTopicMapWriter writer = new CTMTopicMapWriter(System.out,
	 "http://code.google.com/p/ctm-writer/");
	 /*
	 * get the template factory
	 */
	 TemplateFactory factory = writer.getFactory();
	
	 /*
	 * create the template by name
	 */
	 Template t = factory.newTemplate("overlaps");
	
	 /*
	 * create entry representing the embed topic definition
	 */
	 TopicEntry t1 = factory.getEntryFactory().newTopicEntry(
	 new WildcardParam("?c"));
	 /*
	 * add the is-instance-of entry
	 */
	 t1.add(factory.getEntryFactory().newIsInstanceOfEntry(
	 new TopicTypeParam(overlapDecType)));
	 /*
	 * add entry to template
	 */
	 t.add(t1);
	
	 /*
	 * create the role constraints for association entry
	 */
	 RoleEntry r1 = factory.getEntryFactory().newRoleEntry(allowsType,
	 new WildcardParam("?c"));
	 RoleEntry r2 = factory.getEntryFactory().newRoleEntry(allowedType,
	 new VariableParam("$tt1"));
	 /*
	 * create association entry
	 */
	 AssociationEntry a1 = factory.getEntryFactory().newAssociationEntry(
	 overlapsType, r1, r2);
	 /*
	 * add to template
	 */
	 t.add(a1);
	
	 /*
	 * create the role constraints for association entry
	 */
	 RoleEntry r3 = factory.getEntryFactory().newRoleEntry(allowsType,
	 new WildcardParam("?c"));
	 RoleEntry r4 = factory.getEntryFactory().newRoleEntry(allowedType,
	 new VariableParam("$tt2"));
	 /*
	 * create association entry
	 */
	 AssociationEntry a2 = factory.getEntryFactory().newAssociationEntry(
	 overlapsType, r3, r4);
	 /*
	 * add to template
	 */
	 t.add(a2);
	
	 /*
	 * add the template to writer context
	 */
	 writer.addTemplate(t);
	
	 /*
	 * serialize topic map
	 */
	 writer.write(map);
	 }

//	public void testNameTemplates() throws Exception {
//
//		/*
//		 * the topic map to serialize
//		 */
//		TopicMap map = TopicMapSystemFactory.newInstance().newTopicMapSystem()
//				.createTopicMap("http://code.google.com/p/ctm-writer");
//
//		/*
//		 * create the topic map writer
//		 */
//		CTMTopicMapWriter writer = new CTMTopicMapWriter(System.out,
//				"http://code.google.com/p/ctm-writer/");
//		/*
//		 * get the template factory
//		 */
//		TemplateFactory factory = writer.getFactory();
//		EntryFactory entryFactory = factory.getEntryFactory();
//
//		/*
//		 * create the template by name
//		 */
//		Template t = factory.newTemplate("name-template");
//
//		/*
//		 * create entry representing the embed topic definition
//		 */
//		TopicEntry t1 = factory.getEntryFactory().newTopicEntry(
//				new WildcardParam("?c"));
//		/*
//		 * add the is-instance-of entry
//		 */
//		t1.add(factory.getEntryFactory().newAKindOfEntry(
//				new VariableParam("$type")));
//		/*
//		 * add entry to template
//		 */
//		t.add(t1);
//
//		NameEntry n = entryFactory.newNameEntry(new VariableParam("$name"),
//				new WildcardParam("?c"));
//		n.setScopeEntry(entryFactory.newScopeEntry(
//				new VariableParam("$theme1"), new VariableParam("$theme2"),
//				new VariableParam("$theme3")));
//		n.setReifierEntry(entryFactory.newReifierEntry(new VariableParam(
//				"$reifier")));
//		n.add(entryFactory.newVariantEntry(new VariableParam("$variant1")));
//		VariantEntry v = entryFactory.newVariantEntry(new VariableParam(
//				"$variant2"));
//		v.setReifierEntry(entryFactory
//				.newReifierEntry(new VariableParam("$r2")));
//		v.setScopeEntry(entryFactory.newScopeEntry(
//				new VariableParam("$theme1"), new VariableParam("$theme2"),
//				new VariableParam("$theme3")));
//		n.add(v);
//		t.add(n);
//
//		OccurrenceEntry e = entryFactory.newOccurrenceEntry(new VariableParam(
//				"$occurrence"), new WildcardParam("?c"), "<xsd:integer>");
//
//		e.setScopeEntry(entryFactory.newScopeEntry(new WildcardParam("?c"),
//				new VariableParam("$theme")));
//		e
//				.setReifierEntry(entryFactory
//						.newReifierEntry(new WildcardParam("?c")));
//
//		t.add(e);
//
//		/*
//		 * add the template to writer context
//		 */
//		writer.addTemplate(t);
//
//		/*
//		 * serialize topic map
//		 */
//		writer.write(map);
//	}

}
