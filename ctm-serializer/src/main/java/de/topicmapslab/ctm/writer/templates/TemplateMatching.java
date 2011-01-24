/* 
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/    
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 *
 */
package de.topicmapslab.ctm.writer.templates;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.tmapi.core.Construct;
import org.tmapi.core.Topic;

import de.topicmapslab.ctm.writer.core.CTMTopicMapWriter;
import de.topicmapslab.ctm.writer.exception.NoIdentityException;

/**
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 * 
 */
public class TemplateMatching {

	private List<Object> arguments = new LinkedList<Object>();
	private Set<Construct> affectedConstructs = new HashSet<Construct>();
	private Construct context;
	private Template template;

	/**
	 * Returns a list of arguments
	 * @param writer the writer 
	 * @return a list of ctm escaped string arguments
	 */
	public List<String> getArgumentsAsString(CTMTopicMapWriter writer) {
		List<String> arguments = new LinkedList<String>();
		for (Object obj : this.arguments) {
			if (obj instanceof String) {
				arguments.add("\"" + obj.toString() + "\"");
			} else if (obj instanceof Topic) {
				try {
					arguments.add(writer.getCtmIdentity().getMainIdentifier(
							writer.getProperties(), (Topic) obj).toString());
				} catch (NoIdentityException e) {
					throw new RuntimeException(e);
				}
			} else {
				arguments.add(obj.toString());
			}
		}
		return arguments;
	}

	/**
	 * @param template
	 *            the template to set
	 */
	public void setTemplate(Template template) {
		this.template = template;
	}

	/**
	 * Getting the template.
	 * 
	 * @return the template
	 */
	public Template getTemplate() {
		return template;
	}

	/**
	 * Setting the context of template invocation
	 * 
	 * @param context
	 *            the context to set
	 */
	public void setContext(Construct context) {
		this.context = context;
	}
	
	/**
	 * Return the context of template invocation of this matching.
	 * @return the context
	 */
	public Construct getContext() {
		return context;
	}

	/**
	 * Adding an argument to the internal argument list.
	 * 
	 * @param argument
	 *            the argument
	 */
	public void addArgument(Object argument) {
		this.arguments.add(argument);
	}

	/**
	 * Adding a new construct to the internal affection list.
	 * 
	 * @param construct
	 *            the construct
	 */
	public void addAffectedConstruct(Construct construct) {
		this.affectedConstructs.add(construct);
	}

	/**
	 * Get a set containing all constructs affected by the called template. The
	 * affected construct use to avoid duplicated exports.
	 * 
	 * @return the affectedConstructs the affected constructs
	 */
	public Set<Construct> getAffectedConstructs() {
		return affectedConstructs;
	}
}
