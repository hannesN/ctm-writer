/**
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/    
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 *
 */
package de.topicmapslab.ctm.writer.templates.entry;

import static de.topicmapslab.ctm.writer.utility.CTMTokens.QUOTE;
import static de.topicmapslab.ctm.writer.utility.CTMTokens.TRIPPLEQUOTE;
import static de.topicmapslab.ctm.writer.utility.CTMTokens.WHITESPACE;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.tmapi.core.Name;
import org.tmapi.core.Topic;

import de.topicmapslab.ctm.writer.core.serializer.NameSerializer;
import de.topicmapslab.ctm.writer.exception.NoIdentityException;
import de.topicmapslab.ctm.writer.exception.SerializerException;
import de.topicmapslab.ctm.writer.properties.CTMTopicMapWriterProperties;
import de.topicmapslab.ctm.writer.templates.entry.base.EntryImpl;
import de.topicmapslab.ctm.writer.utility.CTMBuffer;
import de.topicmapslab.ctm.writer.utility.CTMIdentity;

/**
 * Class representing a template-entry definition of an name-entry.
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 * 
 */
public class NameEntry extends EntryImpl {

	/**
	 * properties for CTM topic map writer
	 */
	private final CTMTopicMapWriterProperties properties;
	/**
	 * the name type
	 */
	private final Topic type;
	/**
	 * the scope entry
	 */
	private final ScopeEntry scopeEntry;
	/**
	 * the reifier entry
	 */
	private final ReifierEntry reifierEntry;
	
	/**
	 * identity utility (cache and generator)
	 */
	private final CTMIdentity ctmIdentity;

	/**
	 * constructor
	 * 
	 * <br />
	 * <br />
	 * Constructor is calling
	 * {@link NameEntry#NameEntry(CTMTopicMapWriterProperties,String, Topic, ScopeEntry, ReifierEntry)}
	 * , the missing argument are <code>null</code>.
	 * 
	 * @param properties
	 *            the properties
	 * @param valueOrVariable
	 *            the value or variable definition of the template-entry
	 */
	public NameEntry(CTMTopicMapWriterProperties properties, CTMIdentity ctmIdentity,
			String valueOrVariable) {
		this(properties, ctmIdentity, valueOrVariable, null, null, null);
	}

	/**
	 * constructor
	 * 
	 * <br />
	 * <br />
	 * Constructor is calling
	 * {@link NameEntry#NameEntry(CTMTopicMapWriterProperties,String, Topic, ScopeEntry, ReifierEntry)}
	 * , the missing argument are <code>null</code>.
	 * 
	 * @param properties
	 *            the properties
	 * @param valueOrVariable
	 *            the value or variable definition of the template-entry
	 * @param type
	 *            the name type
	 */
	public NameEntry(CTMTopicMapWriterProperties properties, CTMIdentity ctmIdentity,
			String valueOrVariable, final Topic type) {
		this(properties, ctmIdentity, valueOrVariable, type, null, null);
	}

	/**
	 * constructor
	 * 
	 * <br />
	 * <br />
	 * Constructor is calling
	 * {@link NameEntry#NameEntry(CTMTopicMapWriterProperties,String, Topic, ScopeEntry, ReifierEntry)}
	 * , the missing argument are <code>null</code>.
	 * 
	 * @param properties
	 *            the properties
	 * @param valueOrVariable
	 *            the value or variable definition of the template-entry
	 * @param type
	 *            the name type
	 * @param scopeEntry
	 *            a scope entry
	 */
	public NameEntry(CTMTopicMapWriterProperties properties, CTMIdentity ctmIdentity,
			String valueOrVariable, final Topic type,
			final ScopeEntry scopeEntry) {
		this(properties, ctmIdentity, valueOrVariable, type, scopeEntry, null);
	}

	/**
	 * constructor
	 * 
	 * <br />
	 * <br />
	 * Constructor is calling
	 * {@link NameEntry#NameEntry(CTMTopicMapWriterProperties,String, Topic, ScopeEntry, ReifierEntry)}
	 * , the missing argument are <code>null</code>.
	 * 
	 * @param properties
	 *            the properties
	 * @param valueOrVariable
	 *            the value or variable definition of the template-entry
	 * @param type
	 *            the name type
	 * @param reifierEntry
	 *            a reifier entry
	 */
	public NameEntry(CTMTopicMapWriterProperties properties, CTMIdentity ctmIdentity,
			String valueOrVariable, final Topic type,
			final ReifierEntry reifierEntry) {
		this(properties, ctmIdentity, valueOrVariable, type, null, reifierEntry);
	}

	/**
	 * constructor
	 * 
	 * <br />
	 * <br />
	 * Constructor is calling
	 * {@link NameEntry#NameEntry(CTMTopicMapWriterProperties,String, Topic, ScopeEntry, ReifierEntry)}
	 * , the missing argument are <code>null</code>.
	 * 
	 * @param properties
	 *            the properties
	 * @param valueOrVariable
	 *            the value or variable definition of the template-entry
	 * @param scopeEntry
	 *            a scope entry
	 */
	public NameEntry(CTMTopicMapWriterProperties properties, CTMIdentity ctmIdentity,
			String valueOrVariable, final ScopeEntry scopeEntry) {
		this(properties, ctmIdentity, valueOrVariable, null, scopeEntry, null);
	}

	/**
	 * constructor
	 * 
	 * <br />
	 * <br />
	 * Constructor is calling
	 * {@link NameEntry#NameEntry(CTMTopicMapWriterProperties,String, Topic, ScopeEntry, ReifierEntry)}
	 * , the missing argument are <code>null</code>.
	 * 
	 * @param properties
	 *            the properties
	 * @param valueOrVariable
	 *            the value or variable definition of the template-entry
	 * @param reifierEntry
	 *            a reifier entry
	 */
	public NameEntry(CTMTopicMapWriterProperties properties, CTMIdentity ctmIdentity,
			String valueOrVariable, final ReifierEntry reifierEntry) {
		this(properties, ctmIdentity, valueOrVariable, null, null, reifierEntry);
	}

	/**
	 * constructor
	 * 
	 * <br />
	 * <br />
	 * Constructor is calling
	 * {@link NameEntry#NameEntry(CTMTopicMapWriterProperties,String, Topic, ScopeEntry, ReifierEntry)}
	 * , the missing argument are <code>null</code>.
	 * 
	 * @param properties
	 *            the properties
	 * @param valueOrVariable
	 *            the value or variable definition of the template-entry
	 * @param scopeEntry
	 *            a scope entry
	 * @param reifierEntry
	 *            a reifier entry
	 */
	public NameEntry(CTMTopicMapWriterProperties properties, CTMIdentity ctmIdentity,
			String valueOrVariable, final ScopeEntry scopeEntry,
			final ReifierEntry reifierEntry) {
		this(properties, ctmIdentity, valueOrVariable, null, scopeEntry, reifierEntry);
	}

	/**
	 * constructor
	 * 
	 * @param properties
	 *            the properties
	 * @param valueOrVariable
	 *            the value or variable definition of the template-entry
	 * @param type
	 *            the name type
	 * @param scopeEntry
	 *            a scope entry
	 * @param reifierEntry
	 *            a reifier entry
	 */
	public NameEntry(CTMTopicMapWriterProperties properties, CTMIdentity ctmIdentity,
			String valueOrVariable, final Topic type,
			final ScopeEntry scopeEntry, final ReifierEntry reifierEntry) {
		super(valueOrVariable);
		this.type = type;
		this.ctmIdentity = ctmIdentity;
		this.scopeEntry = scopeEntry;
		this.reifierEntry = reifierEntry;
		this.properties = properties;
	}

	/**
	 * {@inheritDoc}
	 */
	public void serialize(CTMBuffer buffer) throws SerializerException {
		NameSerializer
				.serialize(properties, ctmIdentity, getValueOrVariable(), type, buffer);
		if (scopeEntry != null) {
			buffer.append(WHITESPACE);
			scopeEntry.serialize(buffer);
		}
		if (reifierEntry != null) {
			buffer.append(WHITESPACE);
			reifierEntry.serialize(buffer);
		}
		buffer.appendTailLine();
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isAdaptiveFor(Topic topic) {
		boolean result = false;
		if (type == null) {
			result = true;
		} else {
			for (Name name : topic.getNames(type)) {
				result = true;
				if (result && scopeEntry != null) {
					result = name.getScope().containsAll(
							Arrays.asList(scopeEntry.getThemes()));
				}

				if (result && reifierEntry != null) {
					result = reifierEntry.getReifier()
							.equals(name.getReifier());
				}

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
		 * if value is a variable
		 */
		if (isDependentFromVariable()) {
			Name name = tryToExtractNameEntity(topic);
			affectedConstructs.add(name);
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
			final String value = getValueOrVariable();
			if (value.contains(QUOTE)) {
				arguments.add(TRIPPLEQUOTE + value + TRIPPLEQUOTE);
			} else {
				arguments.add(QUOTE + value + QUOTE);
			}
			affectedConstructs.add(tryToExtractNameEntity(topic));
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
		for (Name name : topic.getNames(type)) {
			boolean isAdaptive = true;
			if (scopeEntry != null) {
				isAdaptive &= scopeEntry.isAdaptiveFor(name);
			}
			if (reifierEntry != null) {
				isAdaptive &= reifierEntry.isAdaptiveFor(name);
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
	 * @param properties
	 *            the internal {@link CTMTopicMapWriterProperties}
	 * @param name
	 *            the name construct
	 * @return the generate name-entry
	 * @throws SerializerException
	 *             thrown if generation failed
	 */
	public static NameEntry buildFromConstruct(
			final CTMTopicMapWriterProperties properties, CTMIdentity ctmIdentity, final Name name)
			throws SerializerException {
		Topic type = name.getType();
		/*
		 * generate variable name
		 */
		String variable = "$";
		try {
			variable += ctmIdentity.generateItemIdentifier(properties, type);
		} catch (NoIdentityException e) {
			/*
			 * could happens if name-type is a TMDM-default-type
			 */
			variable += "name";
		}
		/*
		 * generate scope entry
		 */
		ScopeEntry scopeEntry = null;
		if (!name.getScope().isEmpty()) {
			scopeEntry = new ScopeEntry(properties, ctmIdentity, name.getScope().toArray(
					new Topic[0]));
		}

		/*
		 * generate reifier entry
		 */
		ReifierEntry reifierEntry = null;
		if (name.getReifier() != null) {
			reifierEntry = new ReifierEntry(properties, ctmIdentity, name.getReifier());
		}

		/*
		 * create name entry
		 */
		return new NameEntry(properties, ctmIdentity, variable, type, scopeEntry,
				reifierEntry);
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
			if (reifierEntry != null) {
				result &= reifierEntry.equals(((NameEntry) obj).reifierEntry);
			} else {
				result &= ((NameEntry) obj).reifierEntry == null;
			}
			/*
			 * scope must be equal
			 */
			if (scopeEntry != null) {
				result &= scopeEntry.equals(((NameEntry) obj).scopeEntry);
			} else {
				result &= ((NameEntry) obj).scopeEntry == null;
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
	 * Method returns the internal {@link ScopeEntry}
	 * 
	 * @return the scopeEntry
	 */
	public ScopeEntry getScopeEntry() {
		return scopeEntry;
	}

	/**
	 * Method return the internal {@link ReifierEntry}.
	 * 
	 * @return the reifierEntry
	 */
	public ReifierEntry getReifierEntry() {
		return reifierEntry;
	}

}
