/* 
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/    
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 *
 */
package de.topicmapslab.ctm.writer.templates;

import java.util.Set;

import org.tmapi.core.TopicMap;

/**
 * Interface definition of a template scanner. A template scanner will be called
 * by the topic map writer during the serialization process. The scanner has to
 * return all possible template matchings for the corresponding template.
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 * 
 */
public interface ITemplateScanner {

	/**
	 * Scanner call method to get all template matchings of the current
	 * template.
	 * 
	 * @param topicMap
	 *            the topic map
	 * @return a set of all matchings
	 */
	public Set<TemplateMatching> getAdaptiveConstructs(TopicMap topicMap);

}
