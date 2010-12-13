/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/    
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 *  
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 *
 */
package de.topicmapslab.ctm.writer.test.construct;



import java.io.ByteArrayOutputStream;

import org.tmapi.core.Name;
import org.tmapi.core.Occurrence;
import org.tmapi.core.Topic;

import de.topicmapslab.ctm.writer.core.CTMTopicMapWriter;
import de.topicmapslab.ctm.writer.test.BaseTestCase;

/**
 * @author Sven Krosse
 *
 */
public class TestNameExport extends BaseTestCase {

	public void testQuotedStrings() throws Exception {
		Topic type = createTopicBySI("type");
		Topic parent = createTopic();
		
		Name names[] = new Name[100];
		for ( int i = 0 ; i < names.length ; i++ ){
			names[i] = parent.createName(type, " This is simple the number \""+i+"\" as quoted string", new Topic[0]);
		}
		
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		CTMTopicMapWriter writer = new CTMTopicMapWriter(stream, base);
		writer.write(topicMap);
		String content = stream.toString();
		for ( Name name : names){
			String value = name.getValue();
			assertTrue("Writer may use tripple quotes",content.contains("\"\"\"" + value + "\"\"\""));
			assertFalse("Writer may not replace quotes by '",content.contains("\"" + value.replaceAll("\"", "'") + "\""));
		}
	}
	
	public void testTrippleQuotedStrings() throws Exception {
		Topic type = createTopicBySI("type");
		Topic parent = createTopic();
		
		Name names[] = new Name[100];
		for ( int i = 0 ; i < names.length ; i++ ){
			names[i] = parent.createName(type, "This is simple the number \""+i+"\" as quoted string", new Topic[0]);
		}
		
		String otherNameValue = "First Flight (Arranged Ace Combat 04 \"Blockade\")";
		Name otherName = parent.createName(otherNameValue, createTopic());
		
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		CTMTopicMapWriter writer = new CTMTopicMapWriter(stream, base);
		writer.write(topicMap);
		String content = stream.toString();
		for ( Name name : names){
			String value = name.getValue();
			assertTrue("Writer may use tripple quotes",content.contains("\"\"\"" + value.replaceAll("\"", "\\\"") + "\"\"\""));
			assertFalse("Writer may not replace quotes by '",content.contains("\"" + value.replaceAll("\"", "'") + "\""));
			System.out.println(value);
		}
		String value = otherName.getValue();
		assertTrue("Writer may use tripple quotes",content.contains("\"\"\"" + value.replaceAll("\"", "\\\"") + "\"\"\""));
		assertFalse("Writer may not replace quotes by '",content.contains("\"" + value.replaceAll("\"", "'") + "\""));
		System.out.println(value);
	}
	
}
