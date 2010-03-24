/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/    
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 *
 */
package de.topicmapslab.ctm.writer.templates.entry;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.tmapi.core.Construct;
import org.tmapi.core.Topic;

import de.topicmapslab.ctm.writer.exception.SerializerException;
import de.topicmapslab.ctm.writer.templates.Template;
import de.topicmapslab.ctm.writer.templates.TemplateSerializer;
import de.topicmapslab.ctm.writer.templates.entry.base.IEntry;
import de.topicmapslab.ctm.writer.templates.entry.param.IEntryParam;
import de.topicmapslab.ctm.writer.templates.entry.param.ValueParam;
import de.topicmapslab.ctm.writer.utility.CTMBuffer;

/**
 * Class representing a template-entry definition of a template-entry.
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 * 
 */
public class TemplateEntry implements IEntry {

	/**
	 * the template
	 */
	private final Template template;
	/**
	 * values and variables for containing template
	 */
	private final List<String> valuesOrVariables;

	/**
	 * constructor
	 * 
	 * @param template
	 *            the template
	 * @param arguments
	 *            a list of arguments for the template, used as parameters for
	 *            template-invocation
	 */
	public TemplateEntry(final Template template, final String... arguments) {
		this.template = template;
		this.valuesOrVariables = new LinkedList<String>();
		for (String argument : arguments) {
			valuesOrVariables.add(argument);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void serialize(CTMBuffer buffer) throws SerializerException {
		new TemplateSerializer(template).serialize(buffer, valuesOrVariables);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isAdaptiveFor(Construct construct) {
		return template.isAdaptiveFor(construct);
	}

	/**
	 * {@inheritDoc}
	 */
	public IEntryParam getParameter() {
		return new ValueParam(template.getTemplateName());
	}

	/**
	 * Method returns a list of values and variables as parameters for the
	 * internal template.
	 * 
	 * @return the parameter list
	 */
	public List<String> getValuesOrVariables() {
		return valuesOrVariables;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isDependentFromVariable() {
		for (String valuesOrVariable : valuesOrVariables) {
			if (valuesOrVariable.startsWith("$")) {
				return true;
			}
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<String> extractArguments(Topic type, Construct construct,
			Set<Object> affectedConstructs) throws SerializerException {
		if (!isAdaptiveFor(type)) {
			throw new SerializerException(
					"template entry is not adaptive for given topic.");
		}

		List<String> arguments = new LinkedList<String>();
		for (IEntry entry : template.getEntries()) {
			arguments.addAll(entry.extractArguments(type, construct,
					affectedConstructs));
		}
		return arguments;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return super.hashCode();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TemplateEntry) {
			return super.equals(obj)
					&& template.equals(((TemplateEntry) obj).template);
		}
		return false;
	}
	
	@Override
	public List<String> getVariables() {
		List<String> variables = new LinkedList<String>();
		for ( IEntry entry : template.getEntries()){
			variables.addAll(entry.getVariables());
		}
		return variables;
	}
}
