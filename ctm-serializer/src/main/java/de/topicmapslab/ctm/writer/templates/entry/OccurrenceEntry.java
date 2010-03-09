/**
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/    
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 *
 */
package de.topicmapslab.ctm.writer.templates.entry;

import static de.topicmapslab.ctm.writer.utility.CTMTokens.WHITESPACE;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.tmapi.core.Occurrence;
import org.tmapi.core.Topic;

import de.topicmapslab.ctm.writer.core.serializer.DatatypeAwareSerializer;
import de.topicmapslab.ctm.writer.core.serializer.OccurrenceSerializer;
import de.topicmapslab.ctm.writer.exception.SerializerException;
import de.topicmapslab.ctm.writer.properties.CTMTopicMapWriterProperties;
import de.topicmapslab.ctm.writer.templates.entry.base.EntryImpl;
import de.topicmapslab.ctm.writer.utility.CTMBuffer;
import de.topicmapslab.ctm.writer.utility.CTMIdentity;

/**
 * Class representing a template-entry definition of an occurrence-entry.
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 * 
 */
public class OccurrenceEntry extends EntryImpl {

	/**
	 * properties for CTM topic map writer
	 */
	private final CTMTopicMapWriterProperties properties;
	/**
	 * the occurrence type
	 */
	private final Topic type;
	/**
	 * the data-type defined as {@link Topic} or {@link String}
	 */
	private final Object datatypeAsTopicOrString;
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
	 * {@link OccurrenceEntry#OccurrenceEntry(CTMTopicMapWriterProperties,String, Topic, Object, ScopeEntry, ReifierEntry)}
	 * , the missing argument are <code>null</code>.
	 * 
	 * @param properties
	 *            the properties
	 * @param valueOrVariable
	 *            the value or variable definition of the template-entry
	 * @param type
	 *            the type of the occurrence
	 */
	public OccurrenceEntry(CTMTopicMapWriterProperties properties, CTMIdentity ctmIdentity,
			String valueOrVariable, final Topic type) {
		this(properties, ctmIdentity, valueOrVariable, type, null, null, null);
	}

	/**
	 * constructor
	 * 
	 * <br />
	 * <br />
	 * Constructor is calling
	 * {@link OccurrenceEntry#OccurrenceEntry(CTMTopicMapWriterProperties,String, Topic, Object, ScopeEntry, ReifierEntry)}
	 * , the missing argument are <code>null</code>.
	 * 
	 * @param properties
	 *            the properties
	 * @param valueOrVariable
	 *            the value or variable definition of the template-entry
	 * @param type
	 *            the type of the occurrence
	 * @param datatypeAsTopicOrString
	 *            the data-type of the occurrence, given as a {@link Topic} or a
	 *            {@link String}
	 */
	public OccurrenceEntry(CTMTopicMapWriterProperties properties, CTMIdentity ctmIdentity,
			String valueOrVariable, final Topic type,
			final Object datatypeAsTopicOrString) {
		this(properties, ctmIdentity, valueOrVariable, type, datatypeAsTopicOrString, null,
				null);
	}

	/**
	 * constructor
	 * 
	 * <br />
	 * <br />
	 * Constructor is calling
	 * {@link OccurrenceEntry#OccurrenceEntry(CTMTopicMapWriterProperties,String, Topic, Object, ScopeEntry, ReifierEntry)}
	 * , the missing argument are <code>null</code>.
	 * 
	 * @param properties
	 *            the properties
	 * @param valueOrVariable
	 *            the value or variable definition of the template-entry
	 * @param type
	 *            the type of the occurrence
	 * @param datatypeAsTopicOrString
	 *            the data-type of the occurrence, given as a {@link Topic} or a
	 *            {@link String}
	 * @param scopeEntry
	 *            a scope entry
	 */
	public OccurrenceEntry(CTMTopicMapWriterProperties properties, CTMIdentity ctmIdentity,
			String valueOrVariable, final Topic type,
			final Object datatypeAsTopicOrString, final ScopeEntry scopeEntry) {
		this(properties, ctmIdentity, valueOrVariable, type, datatypeAsTopicOrString,
				scopeEntry, null);
	}

	/**
	 * constructor
	 * 
	 * <br />
	 * <br />
	 * Constructor is calling
	 * {@link OccurrenceEntry#OccurrenceEntry(CTMTopicMapWriterProperties,String, Topic, Object, ScopeEntry, ReifierEntry)}
	 * , the missing argument are <code>null</code>.
	 * 
	 * @param properties
	 *            the properties
	 * @param valueOrVariable
	 *            the value or variable definition of the template-entry
	 * @param type
	 *            the type of the occurrence
	 * @param datatypeAsTopicOrString
	 *            the data-type of the occurrence, given as a {@link Topic} or a
	 *            {@link String}
	 * @param reifierEntry
	 *            a reifier entry
	 */
	public OccurrenceEntry(CTMTopicMapWriterProperties properties, CTMIdentity ctmIdentity,
			String valueOrVariable, final Topic type,
			final Object datatypeAsTopicOrString,
			final ReifierEntry reifierEntry) {
		this(properties, ctmIdentity, valueOrVariable, type, datatypeAsTopicOrString, null,
				reifierEntry);
	}

	/**
	 * constructor
	 * 
	 * <br />
	 * <br />
	 * Constructor is calling
	 * {@link OccurrenceEntry#OccurrenceEntry(CTMTopicMapWriterProperties,String, Topic, Object, ScopeEntry, ReifierEntry)}
	 * , the missing argument are <code>null</code>.
	 * 
	 * @param properties
	 *            the properties
	 * @param valueOrVariable
	 *            the value or variable definition of the template-entry
	 * @param type
	 *            the type of the occurrence
	 * @param scopeEntry
	 *            a scope entry
	 */
	public OccurrenceEntry(CTMTopicMapWriterProperties properties, CTMIdentity ctmIdentity,
			String valueOrVariable, final Topic type,
			final ScopeEntry scopeEntry) {
		this(properties, ctmIdentity, valueOrVariable, type, null, scopeEntry, null);
	}

	/**
	 * constructor
	 * 
	 * <br />
	 * <br />
	 * Constructor is calling
	 * {@link OccurrenceEntry#OccurrenceEntry(CTMTopicMapWriterProperties,String, Topic, Object, ScopeEntry, ReifierEntry)}
	 * , the missing argument are <code>null</code>.
	 * 
	 * @param properties
	 *            the properties
	 * @param valueOrVariable
	 *            the value or variable definition of the template-entry
	 * @param type
	 *            the type of the occurrence
	 * @param reifierEntry
	 *            a reifier entry
	 */
	public OccurrenceEntry(CTMTopicMapWriterProperties properties, CTMIdentity ctmIdentity,
			String valueOrVariable, final Topic type,
			final ReifierEntry reifierEntry) {
		this(properties, ctmIdentity, valueOrVariable, type, null, null, reifierEntry);
	}

	/**
	 * constructor
	 * 
	 * <br />
	 * <br />
	 * Constructor is calling
	 * {@link OccurrenceEntry#OccurrenceEntry(CTMTopicMapWriterProperties,String, Topic, Object, ScopeEntry, ReifierEntry)}
	 * , the missing argument are <code>null</code>.
	 * 
	 * @param properties
	 *            the properties
	 * @param valueOrVariable
	 *            the value or variable definition of the template-entry
	 * @param type
	 *            the type of the occurrence
	 * @param scopeEntry
	 *            a scope entry
	 * @param reifierEntry
	 *            a reifier entry
	 */
	public OccurrenceEntry(CTMTopicMapWriterProperties properties, CTMIdentity ctmIdentity,
			String valueOrVariable, final Topic type,
			final ScopeEntry scopeEntry, final ReifierEntry reifierEntry) {
		this(properties, ctmIdentity, valueOrVariable, type, null, scopeEntry, reifierEntry);
	}

	/**
	 * constructor
	 * 
	 * @param properties
	 *            the properties
	 * @param valueOrVariable
	 *            the value or variable definition of the template-entry
	 * @param type
	 *            the type of the occurrence
	 * @param datatypeAsTopicOrString
	 *            the data-type of the occurrence, given as a {@link Topic} or a
	 *            {@link String}
	 * @param scopeEntry
	 *            a scope entry
	 * @param reifierEntry
	 *            a reifier entry
	 */
	public OccurrenceEntry(CTMTopicMapWriterProperties properties, CTMIdentity ctmIdentity,
			String valueOrVariable, final Topic type,
			final Object datatypeAsTopicOrString, final ScopeEntry scopeEntry,
			final ReifierEntry reifierEntry) {
		super(valueOrVariable);
		this.type = type;
		this.datatypeAsTopicOrString = datatypeAsTopicOrString;
		this.scopeEntry = scopeEntry;
		this.reifierEntry = reifierEntry;
		this.properties = properties;
		this.ctmIdentity = ctmIdentity;
	}

	/**
	 * {@inheritDoc}
	 */
	public void serialize(CTMBuffer buffer) throws SerializerException {
		OccurrenceSerializer.serialize(properties, ctmIdentity, getValueOrVariable(),
				datatypeAsTopicOrString, type, buffer);
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
		Set<Occurrence> occurrences = topic.getOccurrences(type);
		boolean result = false;

		if (datatypeAsTopicOrString == null) {
			result = !occurrences.isEmpty();
		} else {
			for (Occurrence occurrence : occurrences) {
				final String reference = ctmIdentity
						.getPrefixedIdentity(occurrence.getDatatype());
				if (datatypeAsTopicOrString instanceof Topic
						&& datatypeAsTopicOrString.equals(occurrence
								.getDatatype())) {
					result = true;
				} else if (datatypeAsTopicOrString.toString().equalsIgnoreCase(
						reference)) {
					result = true;
				}

				if (result && scopeEntry != null) {
					result = occurrence.getScope().containsAll(
							Arrays.asList(scopeEntry.getThemes()));
				}

				if (result && reifierEntry != null) {
					result = reifierEntry.getReifier().equals(
							occurrence.getReifier());
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
			/*
			 * try to extract occurrence entity
			 */
			Occurrence occurrence = tryToExtractOccurrenceEntity(topic);
			/*
			 * extract values and transform by type
			 */
			arguments.add(DatatypeAwareSerializer.toArgument(properties, ctmIdentity, occurrence));
			/*
			 * add affected construct
			 */
			affectedConstructs.add(occurrence);
		}
		/*
		 * if value is a constant
		 */
		else {
			/*
			 * extract values and transform by type
			 */
			arguments.add(DatatypeAwareSerializer
					.toArgument(getValueOrVariable()));

			/*
			 * add affected construct
			 */
			affectedConstructs.add(tryToExtractOccurrenceEntity(topic));
		}
		return arguments;
	}

	/**
	 * Internal method to extract the occurrence from topic matching this
	 * instance of template-entry. Method checks the type information, the
	 * data-type, the scope and the reifier entry.
	 * 
	 * @param topic
	 *            the topic to extract the occurrence
	 * @return the extracted occurrence if exists and never <code>null</code>
	 * 
	 * @throws SerializerException
	 *             thrown if occurrence can not be extracted
	 */
	private Occurrence tryToExtractOccurrenceEntity(final Topic topic)
			throws SerializerException {
		for (Occurrence occurrence : topic.getOccurrences(type)) {
			boolean isAdaptive = true;
			final String reference = ctmIdentity.getPrefixedIdentity(occurrence
					.getDatatype());
			if (datatypeAsTopicOrString instanceof Topic
					&& datatypeAsTopicOrString.equals(occurrence.getDatatype())) {
				isAdaptive &= true;
			} else if (datatypeAsTopicOrString.toString().equalsIgnoreCase(
					reference)) {
				isAdaptive &= true;
			}
			if (scopeEntry != null) {
				isAdaptive &= scopeEntry.isAdaptiveFor(occurrence);
			}
			if (reifierEntry != null) {
				isAdaptive &= reifierEntry.isAdaptiveFor(occurrence);
			}
			if (isAdaptive) {
				return occurrence;
			}
		}
		throw new SerializerException(
				"template entry is not adaptive for given topic.");
	}

	/**
	 * Static method to create a occurrence-entry by given occurrence construct
	 * of a topic. All information needed to define the entry are extracted from
	 * the given construct.
	 * 
	 * @param properties
	 *            the internal {@link CTMTopicMapWriterProperties}
	 * @param occurrence
	 *            the occurrence construct
	 * @return the generate occurrence-entry
	 * @throws SerializerException
	 *             thrown if generation failed
	 */
	public static OccurrenceEntry buildFromConstruct(
			final CTMTopicMapWriterProperties properties, CTMIdentity ctmIdentity,
			final Occurrence occurrence) throws SerializerException {
		Topic type = occurrence.getType();
		/*
		 * generate variable name
		 */
		String variable = "$"
				+ ctmIdentity.generateItemIdentifier(properties, type);
		/*
		 * set CTM identity of data-type
		 */
		String datatype = ctmIdentity.getPrefixedIdentity(occurrence
				.getDatatype());
		/*
		 * generate scope entry if necessary
		 */
		ScopeEntry scopeEntry = null;
		if (!occurrence.getScope().isEmpty()) {
			scopeEntry = new ScopeEntry(properties, ctmIdentity, occurrence.getScope()
					.toArray(new Topic[0]));
		}

		/*
		 * generate reifier entry if necessary
		 */
		ReifierEntry reifierEntry = null;
		if (occurrence.getReifier() != null) {
			reifierEntry = new ReifierEntry(properties, ctmIdentity, occurrence.getReifier());
		}

		/*
		 * create new occurrence-entry
		 */
		return new OccurrenceEntry(properties, ctmIdentity, variable, type, datatype,
				scopeEntry, reifierEntry);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof OccurrenceEntry) {
			/*
			 * value or variable name must be equal
			 */
			boolean result = super.equals(obj)
					&& type.equals(((OccurrenceEntry) obj).type);
			/*
			 * data-type must be equal
			 */
			if (datatypeAsTopicOrString != null) {
				result &= datatypeAsTopicOrString
						.equals(((OccurrenceEntry) obj).datatypeAsTopicOrString);
			} else {
				result &= ((OccurrenceEntry) obj).datatypeAsTopicOrString == null;
			}
			/*
			 * reifier must be equal
			 */
			if (reifierEntry != null) {
				result &= reifierEntry
						.equals(((OccurrenceEntry) obj).reifierEntry);
			} else {
				result &= ((OccurrenceEntry) obj).reifierEntry == null;
			}
			/*
			 * scope must be equal
			 */
			if (scopeEntry != null) {
				result &= scopeEntry.equals(((OccurrenceEntry) obj).scopeEntry);
			} else {
				result &= ((OccurrenceEntry) obj).scopeEntry == null;
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
