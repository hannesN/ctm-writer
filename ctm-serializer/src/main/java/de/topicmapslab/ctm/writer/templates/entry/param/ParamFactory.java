package de.topicmapslab.ctm.writer.templates.entry.param;

import org.tmapi.core.Topic;

/**
 * Internal factory class to create entry parameters.
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 */
public class ParamFactory {

	/**
	 * Create a new entry parameter representing a value as variable. The '$'
	 * prefix will added automatically.
	 * 
	 * @param name
	 *            the variable name without '$'
	 * @return the created parameter
	 */
	public IEntryParam newVariableParam(final String name) {
		return new VariableParam(name);
	}

	/**
	 * Create a new entry parameter representing a value as wildcard. The '?'
	 * prefix will added automatically.
	 * 
	 * @param name
	 *            the variable name without '?'
	 * @return the created parameter
	 */
	public IEntryParam newWildcardParam(final String name) {
		return new WildcardParam(name);
	}

	/**
	 * Create a new entry parameter representing a value as literal.
	 * 
	 * @param value
	 *            the value
	 * @return the created parameter
	 */
	public IEntryParam newValueParam(final String value) {
		return new ValueParam(value);
	}

	/**
	 * Create a new entry parameter representing a value as topic type item.
	 * 
	 * @param type
	 *            the topic type representing the value parameter
	 * @return the created parameter
	 */
	public IEntryParam newTopicTypeParam(final Topic type) {
		return new TopicTypeParam(type);
	}

}
