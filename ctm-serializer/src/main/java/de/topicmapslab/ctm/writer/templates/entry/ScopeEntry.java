/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/    
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 *
 */
package de.topicmapslab.ctm.writer.templates.entry;

import static de.topicmapslab.ctm.writer.utility.CTMTokens.COMMA;
import static de.topicmapslab.ctm.writer.utility.CTMTokens.SCOPE;
import static de.topicmapslab.ctm.writer.utility.CTMTokens.WHITESPACE;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.tmapi.core.Topic;

import de.topicmapslab.ctm.writer.core.CTMTopicMapWriter;
import de.topicmapslab.ctm.writer.exception.SerializerException;
import de.topicmapslab.ctm.writer.properties.CTMTopicMapWriterProperties;
import de.topicmapslab.ctm.writer.templates.entry.param.IEntryParam;
import de.topicmapslab.ctm.writer.templates.entry.param.TopicTypeParam;
import de.topicmapslab.ctm.writer.templates.entry.param.VariableParam;
import de.topicmapslab.ctm.writer.templates.entry.param.WildcardParam;
import de.topicmapslab.ctm.writer.utility.ICTMWriter;

/**
 * Class representing a template-entry definition of an role-entry.
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 * 
 */
public class ScopeEntry {

	/**
	 * the parent topic map writer
	 */
	private final CTMTopicMapWriter writer;
	/**
	 * a list of parameters
	 */
	private final IEntryParam[] params;

	/**
	 * constructor calling
	 * {@link ScopeEntry#ScopeEntry(CTMTopicMapWriterProperties,Topic[], String...)}
	 * with an empty topic array.
	 * 
	 * @param writer
	 *            the parent topic map writer
	 * @param params
	 *            a non-empty list of params
	 * @throws SerializerException
	 */
	protected ScopeEntry(CTMTopicMapWriter writer, IEntryParam... params)
			throws SerializerException {
		if (params.length == 0) {
			throw new SerializerException(
					"themes and variables can not be empty.");
		}
		this.params = params;
		this.writer = writer;
	}

	/**
	 * Method to convert the given scope to its specific CTM string. The result
	 * should be written to the given output buffer.
	 * 
	 * @param buffer
	 *            the output buffer
	 * @throws SerializerException
	 *             Thrown if serialization failed.
	 */
	public void serialize(ICTMWriter buffer) throws SerializerException, IOException {
		boolean first = true;
		for (IEntryParam param : params) {
			if (first) {
				buffer.append(SCOPE);
				first = false;
			} else {
				buffer.append(COMMA, WHITESPACE);
			}
			if (param instanceof TopicTypeParam) {
				buffer.append(writer.getCtmIdentity().getMainIdentifier(
						writer.getProperties(),
						((TopicTypeParam) param).getTopic()).toString());
			} else if (param instanceof WildcardParam) {
				buffer.append(param.getCTMRepresentation());
			} else if (param instanceof VariableParam) {
				buffer.append(param.getCTMRepresentation());
			}
		}
	}

	// /**
	// * Check if entry is adaptive for given scoped element.
	// *
	// * @param scoped
	// * the scoped element
	// * @return <code>true</code> if the entry can replaced a part of the given
	// * scoped element.
	// */
	// public boolean isAdaptiveFor(Scoped scoped) {
	// boolean adaptive = true;
	// Set<Topic> themes = scoped.getScope();
	// for (IEntryParam param : params) {
	// if (param instanceof TopicTypeParam) {
	// adaptive &= themes
	// .contains(((TopicTypeParam) param).getTopic());
	// }
	// if (!adaptive) {
	// break;
	// }
	// }
	// adaptive &= scoped.getScope().size() == params.length;
	// return adaptive;
	// }

	/**
	 * Method returns the internal list of themes
	 * 
	 * @return the list of themes
	 */
	public Topic[] getThemes() {
		List<Topic> variables = new LinkedList<Topic>();

		for (IEntryParam param : params) {
			if (param instanceof TopicTypeParam) {
				variables.add(((TopicTypeParam) param).getTopic());
			}
		}
		return variables.toArray(new Topic[0]);
	}

	/**
	 * Method returns the internal list of variables
	 * 
	 * @return the list of variables
	 */
	public List<String> getVariables() {
		List<String> variables = new LinkedList<String>();

		for (IEntryParam param : params) {
			if (param instanceof VariableParam) {
				variables.add(param.getCTMRepresentation());
			}
		}
		return variables;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ScopeEntry) {
			return super.equals(obj)
					&& Arrays.asList(params).containsAll(
							Arrays.asList(((ScopeEntry) obj).params));
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return super.hashCode();
	}

}
