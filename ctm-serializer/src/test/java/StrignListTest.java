import junit.framework.TestCase;
import de.topicmapslab.common.tools.collections.StringList;

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
public class StrignListTest extends TestCase {

	/**
	 * Test method for {@link de.topicmapslab.common.tools.collections.StringList#containsAsSubstring(java.lang.String)}.
	 */
	public final void testContainsAsSubstring() {
		StringList list = new StringList();
		list.add("http://psi.ontopedia.net/model/all/what/i/want#date_of_death");
		assertEquals(true, list.containsAsSubstring("ontopedia"));
		assertEquals(true, list.containsAsSubstring("psi.ontopedia.net"));
	}

}
