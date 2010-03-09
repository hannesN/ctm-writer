import junit.framework.TestCase;
import de.topicmapslab.ctm.writer.templates.Template;
import de.topicmapslab.ctm.writer.templates.TemplateSerializer;
import de.topicmapslab.ctm.writer.templates.entry.ItemIdentifierEntry;
import de.topicmapslab.ctm.writer.templates.entry.SubjectIdentifierEntry;
import de.topicmapslab.ctm.writer.templates.entry.SubjectLocatorEntry;
import de.topicmapslab.ctm.writer.utility.CTMBuffer;
import de.topicmapslab.ctm.writer.utility.CTMIdentity;

/**
 * TMQL4J - Javabased TMQL Engine
 * 
 * Copyright: Copyright 2009 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/    
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
public class TemplateSerializerTest extends TestCase {

	public void testname() throws Exception {
		CTMIdentity id = new CTMIdentity();
		Template template = new Template("my-template");
		template.add(new SubjectIdentifierEntry("$uri", id));
		template.add(new SubjectLocatorEntry("http://this-is-it", id));
		template.add(new ItemIdentifierEntry("is-it", id));

		CTMBuffer buffer = new CTMBuffer();
		new TemplateSerializer(template).serialize(buffer);
		System.out.println(buffer.getBuffer().toString());
	}

}
