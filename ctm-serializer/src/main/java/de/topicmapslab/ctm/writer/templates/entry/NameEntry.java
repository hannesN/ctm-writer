/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/    
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 *
 */
package de.topicmapslab.ctm.writer.templates.entry;

import static de.topicmapslab.ctm.writer.utility.CTMTokens.BRC;
import static de.topicmapslab.ctm.writer.utility.CTMTokens.BRO;
import static de.topicmapslab.ctm.writer.utility.CTMTokens.COMMA;
import static de.topicmapslab.ctm.writer.utility.CTMTokens.QUOTE;
import static de.topicmapslab.ctm.writer.utility.CTMTokens.TRIPPLEQUOTE;
import static de.topicmapslab.ctm.writer.utility.CTMTokens.WHITESPACE;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.tmapi.core.Name;
import org.tmapi.core.Topic;
import org.tmapi.core.Variant;

import de.topicmapslab.ctm.writer.core.CTMTopicMapWriter;
import de.topicmapslab.ctm.writer.core.serializer.NameSerializer;
import de.topicmapslab.ctm.writer.exception.NoIdentityException;
import de.topicmapslab.ctm.writer.exception.SerializerException;
import de.topicmapslab.ctm.writer.templates.entry.base.IEntry;
import de.topicmapslab.ctm.writer.templates.entry.base.ScopedEntry;
import de.topicmapslab.ctm.writer.templates.entry.param.IEntryParam;
import de.topicmapslab.ctm.writer.templates.entry.param.ParamFactory;
import de.topicmapslab.ctm.writer.templates.entry.param.TopicTypeParam;
import de.topicmapslab.ctm.writer.templates.entry.param.VariableParam;
import de.topicmapslab.ctm.writer.templates.entry.param.WildcardParam;
import de.topicmapslab.ctm.writer.utility.CTMBuffer;

/**
 * Class representing a template-entry definition of an name-entry.
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 * 
 */
public class NameEntry extends ScopedEntry {

	/**
	 * the parent topic map writer
	 */
	private final CTMTopicMapWriter writer;
	/**
	 * the name type
	 */
	private final IEntryParam type;

	/**
	 * the variants definitions
	 */
	private final List<VariantEntry> variants = new LinkedList<VariantEntry>();

	/**
	 * constructor
	 * 
	 * @param writer
	 *            the parent topic map writer
	 * @param valueOrVariable
	 *            the value or variable definition of the template-entry
	 */
	protected NameEntry(CTMTopicMapWriter writer, IEntryParam value) {
		this(writer, value, null);
	}

	/**
	 * constructor
	 * 
	 * 
	 * @param writer
	 *            the parent topic map writer
	 * @param value
	 *            the value of the name of the template-entry
	 * @param type
	 *            the name type
	 */
	protected NameEntry(CTMTopicMapWriter writer, IEntryParam value,
			final IEntryParam type) {
		super(value);
		this.type = type;
		this.writer = writer;
	}

	/**
	 * {@inheritDoc}
	 */
	public void serialize(CTMBuffer buffer) throws SerializerException {

		String identifier = null;
		if (type instanceof TopicTypeParam) {
			try {
				identifier = writer.getCtmIdentity().getMainIdentifier(
						writer.getProperties(),
						((TopicTypeParam) type).getTopic()).toString();
			} catch (NoIdentityException e) {
				throw new SerializerException(e);
			}
		} else if (type instanceof WildcardParam) {
			identifier = type.getCTMRepresentation();
		} else if (type instanceof VariableParam) {
			identifier = type.getCTMRepresentation();
		}

		NameSerializer.serialize(writer, getParameter().getCTMRepresentation(),
				identifier, buffer);
		if (getScopeEntry() != null) {
			buffer.append(WHITESPACE);
			getScopeEntry().serialize(buffer);
		}
		if (getReifierEntry() != null) {
			buffer.append(WHITESPACE);
			getReifierEntry().serialize(buffer);
		}

		if (!variants.isEmpty()) {
			buffer.append(BRO);
			boolean first = true;
			for (VariantEntry variant : variants) {
				if (!first) {
					buffer.append(COMMA, WHITESPACE);
				}
				first = false;
				variant.serialize(buffer);
			}
			buffer.append(BRC);
		}

		buffer.appendTailLine();
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isAdaptiveFor(Topic topic) {
		boolean result = false;
		if (type == null || type instanceof WildcardParam
				|| type instanceof VariableParam) {
			result = true;
		} else if (type instanceof TopicTypeParam) {
			for (Name name : topic.getNames(((TopicTypeParam) type).getTopic())) {
				result = true;
				if (result && getScopeEntry() != null) {
					result = name.getScope().containsAll(
							Arrays.asList(getScopeEntry().getThemes()));
				}

				if (result && getReifierEntry() != null) {
					result = getReifierEntry().getReifier().equals(
							name.getReifier());
				}

				for (VariantEntry variant : variants) {
					result &= variant.isAdaptiveFor(name);
				}

				result &= name.getVariants().size() == variants.size();

				if (result) {
					break;
				}
			}
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<String> extractArguments(Topic topic,
			Set<Object> affectedConstructs) throws SerializerException {
		if (!isAdaptiveFor(topic)) {
			throw new SerializerException(
					"template entry is not adaptive for given topic.");
		}

		List<String> arguments = new LinkedList<String>();

		/*
		 * get name entity
		 */
		Name name = tryToExtractNameEntity(topic);
		affectedConstructs.add(name);

		/*
		 * get type information
		 */
		if (type instanceof VariableParam) {
			arguments.add(writer.getCtmIdentity().getMainIdentifier(
					writer.getProperties(), name.getType()).toString());
		}

		/*
		 * if value is a variable
		 */
		if (isDependentFromVariable()) {
			final String value = name.getValue();
			if (value.contains(QUOTE)) {
				arguments.add(TRIPPLEQUOTE + value + TRIPPLEQUOTE);
			} else {
				arguments.add(QUOTE + value + QUOTE);
			}
		}
		/*
		 * if value is a constant
		 */
		else {
			final String value = getParameter().getCTMRepresentation();
			if (value.contains(QUOTE)) {
				arguments.add(TRIPPLEQUOTE + value + TRIPPLEQUOTE);
			} else {
				arguments.add(QUOTE + value + QUOTE);
			}

		}

		for (VariantEntry variantEntry : variants) {
			variantEntry.extractArguments(topic, name, affectedConstructs);
		}

		return arguments;
	}

	/**
	 * Internal method to extract the name from topic matching this instance of
	 * template-entry. Method checks the type information, the scope and the
	 * reifier entry.
	 * 
	 * @param topic
	 *            the topic to extract the name
	 * @return the extracted name if exists and never <code>null</code>
	 * 
	 * @throws SerializerException
	 *             thrown if name can not be extracted
	 */
	private Name tryToExtractNameEntity(final Topic topic)
			throws SerializerException {
		for (Name name : topic.getNames()) {
			boolean isAdaptive = true;
			if (type instanceof TopicTypeParam) {
				Topic t = ((TopicTypeParam) type).getTopic();
				isAdaptive &= name.getType().equals(t);
			}
			if (getScopeEntry() != null) {
				isAdaptive &= getScopeEntry().isAdaptiveFor(name);
			}
			if (getReifierEntry() != null) {
				isAdaptive &= getReifierEntry().isAdaptiveFor(name);
			}
			if (isAdaptive) {
				return name;
			}
		}
		throw new SerializerException(
				"template entry is not adaptive for given topic.");
	}

	/**
	 * Static method to create a name-entry by given name construct of a topic.
	 * All information needed to define the entry are extracted from the given
	 * construct.
	 * 
	 * @param writer
	 *            the parent topic map writer
	 * @param name
	 *            the name construct
	 * @return the generate name-entry
	 * @throws SerializerException
	 *             thrown if generation failed
	 */
	public static NameEntry buildFromConstruct(final CTMTopicMapWriter writer,
			final Name name) throws SerializerException {
		/*
		 * parameter factory
		 */
		ParamFactory factory = new ParamFactory();
		/*
		 * the type
		 */
		Topic type = name.getType();
		/*
		 * generate variable name
		 */
		String variable = "name";
		/*
		 * generate scope entry
		 */
		ScopeEntry scopeEntry = null;
		if (!name.getScope().isEmpty()) {
			List<IEntryParam> params = new LinkedList<IEntryParam>();
			for (Topic theme : name.getScope()) {
				params.add(factory.newTopicTypeParam(theme));
			}
			scopeEntry = writer.getFactory().getEntryFactory().newScopeEntry(
					params.toArray(new IEntryParam[0]));
		}

		/*
		 * generate reifier entry
		 */
		ReifierEntry reifierEntry = null;
		if (name.getReifier() != null) {
			reifierEntry = writer.getFactory().getEntryFactory()
					.newReifierEntry(
							factory.newTopicTypeParam(name.getReifier()));
		}

		/*
		 * create name entry
		 */
		NameEntry entry = new NameEntry(writer, factory
				.newVariableParam(variable), factory.newTopicTypeParam(type));
		entry.setReifierEntry(reifierEntry);
		entry.setScopeEntry(scopeEntry);

		/*
		 * add variants
		 */
		int index = 0;
		for (Variant variant : name.getVariants()) {
			VariantEntry v = VariantEntry.buildFromConstruct(writer, variant);
			v
					.setValueOrVariable(factory.newVariableParam("variant"
							+ index++));
			entry.add(v);
		}

		return entry;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof NameEntry) {
			/*
			 * value or variable name must be equal
			 */
			boolean result = super.equals(obj);
			/*
			 * type must be equal
			 */
			if (type != null) {
				result &= type.equals(((NameEntry) obj).type);
			} else {
				result &= ((NameEntry) obj).type == null;
			}
			/*
			 * reifier must be equal
			 */
			if (getReifierEntry() != null) {
				result &= getReifierEntry().equals(
						((NameEntry) obj).getReifierEntry());
			} else {
				result &= ((NameEntry) obj).getReifierEntry() == null;
			}
			/*
			 * scope must be equal
			 */
			if (getScopeEntry() != null) {
				result &= getScopeEntry().equals(
						((NameEntry) obj).getScopeEntry());
			} else {
				result &= ((NameEntry) obj).getScopeEntry() == null;
			}
			return result;
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getVariables() {
		List<String> variables = new LinkedList<String>();
		if (type instanceof VariableParam) {
			variables.add(type.getCTMRepresentation());
		}
		variables.addAll(super.getVariables());
		for (IEntry variant : variants) {
			variables.addAll(variant.getVariables());
		}
		return variables;
	}

	/**
	 * Add a new variant entry to the internal list
	 * 
	 * @param entry
	 *            the new variant entry
	 */
	public void add(VariantEntry entry) {
		variants.add(entry);
	}

}
