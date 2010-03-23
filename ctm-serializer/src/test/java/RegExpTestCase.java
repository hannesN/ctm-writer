import junit.framework.TestCase;

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
public class RegExpTestCase extends TestCase {

	public void testRegExp() throws Exception {
		String s = "template-opera-invoc--3934104995650977629";
		String regexp = ".*-invoc--?[0-9]+";
		System.out.println(s.matches(regexp));
	}

}
