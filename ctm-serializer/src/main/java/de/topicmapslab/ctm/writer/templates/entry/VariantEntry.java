/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/    
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 *
 */
package de.topicmapslab.ctm.writer.templates.entry;

import static de.topicmapslab.ctm.writer.utility.CTMTokens.WHITESPACE;

import java.util.LinkedList;
import java.util.List;

import org.tmapi.core.Topic;
import org.tmapi.core.Variant;

import de.topicmapslab.ctm.writer.core.CTMTopicMapWriter;
import de.topicmapslab.ctm.writer.core.serializer.VariantSerializer;
import de.topicmapslab.ctm.writer.exception.SerializerException;
import de.topicmapslab.ctm.writer.templates.entry.base.ScopedEntry;
import de.topicmapslab.ctm.writer.templates.entry.param.IEntryParam;
import de.topicmapslab.ctm.writer.templates.entry.param.ParamFactory;
import de.topicmapslab.ctm.writer.utility.CTMBuffer;

/**
 * Class representing a template-entry definition of a variant-entry.
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 * 
 */
public class VariantEntry extends ScopedEntry {

	/**
	 * the parent topic map writer
	 */
	private final CTMTopicMapWriter writer;

	/**
	 * the data-type defined as {@link Topic} or {@link String}
	 */
	private final Object datatypeAsTopicOrString;

	/**
	 * constructor
	 * 
	 * @param writer
	 *            the parent topic map writer
	 * @param value
	 *            the value definition of the template-entry
	 */
	protected VariantEntry(CTMTopicMapWriter writer, IEntryParam value) {
		this(writer, value, null);
	}

	/**
	 * constructor
	 * 
	 * @param writer
	 *            the parent topic map writer
	 * @param value
	 *            the value definition of the template-entry
	 * @param datatypeAsTopicOrString
	 *            the data-type of the variant, given as a {@link String}
	 */
	protected VariantEntry(CTMTopicMapWriter writer, IEntryParam value,
			Object datatypeAsTopicOrString) {
		super(value);
		this.writer = writer;
		this.datatypeAsTopicOrString = datatypeAsTopicOrString;
	}

	/**
	 * {@inheritDoc}
	 */
	public void serialize(CTMBuffer buffer) throws SerializerException {
		VariantSerializer.serialize(writer, getParameter()
				.getCTMRepresentation(), datatypeAsTopicOrString, buffer);
		if (getScopeEntry() != null) {
			buffer.append(WHITESPACE);
			getScopeEntry().serialize(buffer);
		}
		if (getReifierEntry() != null) {
			buffer.append(WHITESPACE);
			getReifierEntry().serialize(buffer);
		}
	}

	// @Override
	// public boolean isAdaptiveFor(Construct construct) {
	// boolean result = super.isAdaptiveFor(construct);
	// if (!result && construct instanceof Name) {
	// for (Variant variant : ((Name) construct).getVariants()) {
	// if (isAdaptiveFor(variant)) {
	// return result;
	// }
	// }
	// }
	// return false;
	// }
	//
	// /**
	// * Checks if the given variant can be used with this entry.
	// *
	// * @param variant
	// * the variant to check
	// * @return <code>true</code> if the variant matches this entry,
	// * <code>false</code> otherwise
	// */
	// public boolean isAdaptiveFor(Variant variant) {
	// boolean result = true;
	// if (getScopeEntry() == null) {
	// result &= variant.getScope().isEmpty();
	// } else {
	// result &= variant.getScope().contains(getScopeEntry().getThemes());
	// }
	//
	// if (getReifierEntry() == null) {
	// result &= variant.getReifier() == null;
	// } else {
	// result &= variant.getReifier().equals(
	// getReifierEntry().getReifier())
	// || getReifierEntry().getReifier() == null;
	// }
	//
	// if (datatypeAsTopicOrString instanceof String) {
	// result &= variant.getDatatype().getReference().equalsIgnoreCase(
	// datatypeAsTopicOrString.toString());
	// } else {
	// result &= ((Topic) datatypeAsTopicOrString).getSubjectIdentifiers()
	// .contains(variant.getDatatype());
	// }
	// return result;
	// }
	//
	// /**
	// * {@inheritDoc}
	// */
	// @Deprecated
	// public boolean isAdaptiveFor(Topic topic) {
	// Set<Variant> variants = new HashSet<Variant>();
	// for (Name name : topic.getNames()) {
	// variants.addAll(name.getVariants());
	// }
	// boolean result = false;
	//
	// if (datatypeAsTopicOrString == null) {
	// result = !variants.isEmpty();
	// } else {
	// for (Variant variant : variants) {
	// final String reference = writer.getCtmIdentity()
	// .getPrefixedIdentity(variant.getDatatype());
	// if (datatypeAsTopicOrString instanceof Topic
	// && datatypeAsTopicOrString
	// .equals(variant.getDatatype())) {
	// result = true;
	// } else if (datatypeAsTopicOrString.toString().equalsIgnoreCase(
	// reference)) {
	// result = true;
	// }
	//
	// if (result && getScopeEntry() != null) {
	// result = variant.getScope().containsAll(
	// Arrays.asList(getScopeEntry().getThemes()));
	// }
	//
	// if (result && getReifierEntry() != null) {
	// result = getReifierEntry().getReifier().equals(
	// variant.getReifier());
	// }
	//
	// if (result) {
	// break;
	// }
	// }
	// }
	//
	// return result;
	// }
	//
	// @Override
	// public List<String> extractArguments(Topic type, Construct construct,
	// Set<Object> affectedConstructs) throws SerializerException {
	//
	// if (construct instanceof Name) {
	// Name name = (Name) construct;
	// if (!isAdaptiveFor(name)) {
	// throw new SerializerException(
	// "template entry is not adaptive for given topic.");
	// }
	//
	// List<String> arguments = new LinkedList<String>();
	// /*
	// * if value is a variable
	// */
	// if (getParameter() instanceof VariableParam) {
	// /*
	// * try to extract occurrence entity
	// */
	// Variant variant = tryToExtractVariantEntity(name);
	//
	// /*
	// * extract values and transform by type
	// */
	// arguments.add(DatatypeAwareSerializer.toArgument(writer,
	// variant));
	//
	// /*
	// * add affected construct
	// */
	// affectedConstructs.add(variant);
	// }
	// /*
	// * if value is a constant
	// */
	// else {
	//
	// /*
	// * extract values and transform by type
	// */
	// arguments.add(DatatypeAwareSerializer.toArgument(getParameter()
	// .getCTMRepresentation()));
	//
	// /*
	// * try to extract occurrence entity
	// */
	// Variant variant = tryToExtractVariantEntity(name);
	//
	// /*
	// * extract values and transform by type
	// */
	// arguments.add(DatatypeAwareSerializer.toArgument(writer,
	// variant));
	//
	// /*
	// * add affected construct
	// */
	// affectedConstructs.add(variant);
	// }
	// return arguments;
	// }
	//
	// return super.extractArguments(type, construct, affectedConstructs);
	// }
	//
	// /**
	// * {@inheritDoc}
	// */
	// public List<String> extractArguments(Topic topic,
	// Set<Object> affectedConstructs) throws SerializerException {
	// if (!isAdaptiveFor(topic)) {
	// throw new SerializerException(
	// "template entry is not adaptive for given topic.");
	// }
	//
	// List<String> arguments = new LinkedList<String>();
	// /*
	// * if value is a variable
	// */
	// if (getParameter() instanceof VariableParam) {
	// /*
	// * try to extract occurrence entity
	// */
	// Variant variant = tryToExtractVariantEntity(topic);
	//
	// /*
	// * extract values and transform by type
	// */
	// arguments.add(DatatypeAwareSerializer.toArgument(writer, variant));
	//
	// /*
	// * add affected construct
	// */
	// affectedConstructs.add(variant);
	// }
	// /*
	// * if value is a constant
	// */
	// else {
	//
	// /*
	// * extract values and transform by type
	// */
	// arguments.add(DatatypeAwareSerializer.toArgument(getParameter()
	// .getCTMRepresentation()));
	//
	// /*
	// * try to extract occurrence entity
	// */
	// Variant variant = tryToExtractVariantEntity(topic);
	//
	// /*
	// * extract values and transform by type
	// */
	// arguments.add(DatatypeAwareSerializer.toArgument(writer, variant));
	//
	// /*
	// * add affected construct
	// */
	// affectedConstructs.add(variant);
	// }
	// return arguments;
	// }

	// /**
	// * Internal method to extract the occurrence from topic matching this
	// * instance of template-entry. Method checks the type information, the
	// * data-type, the scope and the reifier entry.
	// *
	// * @param topic
	// * the topic to extract the occurrence
	// * @return the extracted occurrence if exists and never <code>null</code>
	// *
	// * @throws SerializerException
	// * thrown if occurrence can not be extracted
	// */
	// private Variant tryToExtractVariantEntity(final Topic topic)
	// throws SerializerException {
	//
	// for (Name name : topic.getNames()) {
	// Variant v = tryToExtractVariantEntity(name);
	// if (v != null) {
	// return v;
	// }
	// }
	// throw new SerializerException("No variant is matching");
	//
	// }
	//
	// /**
	// * Internal method to extract the occurrence from name matching this
	// * instance of template-entry. Method checks the type information, the
	// * data-type, the scope and the reifier entry.
	// *
	// * @param name
	// * the name item to extract the occurrence
	// * @return the extracted occurrence if exists and never <code>null</code>
	// *
	// * @throws SerializerException
	// * thrown if occurrence can not be extracted
	// */
	// private Variant tryToExtractVariantEntity(final Name name)
	// throws SerializerException {
	//
	// for (Variant variant : name.getVariants()) {
	// boolean isAdaptive = true;
	// final String reference = writer.getCtmIdentity()
	// .getPrefixedIdentity(variant.getDatatype());
	// if (datatypeAsTopicOrString instanceof Topic
	// && datatypeAsTopicOrString.equals(variant.getDatatype())) {
	// isAdaptive &= true;
	// } else if (datatypeAsTopicOrString.toString().equalsIgnoreCase(
	// reference)) {
	// isAdaptive &= true;
	// }
	// if (getScopeEntry() != null) {
	// isAdaptive &= getScopeEntry().isAdaptiveFor(variant);
	// }
	// if (getReifierEntry() != null) {
	// isAdaptive &= getReifierEntry().isAdaptiveFor(variant);
	// }
	// if (isAdaptive) {
	// return variant;
	// }
	// }
	// throw new SerializerException(
	// "template entry is not adaptive for given topic.");
	// }

	/**
	 * Static method to create a occurrence-entry by given occurrence construct
	 * of a topic. All information needed to define the entry are extracted from
	 * the given construct.
	 * 
	 * @param writer
	 *            the parent topic map writer
	 * @param occurrence
	 *            the occurrence construct
	 * @return the generate occurrence-entry
	 * @throws SerializerException
	 *             thrown if generation failed
	 */
	public static VariantEntry buildFromConstruct(
			final CTMTopicMapWriter writer, final Variant variant)
			throws SerializerException {
		/*
		 * parameter factory
		 */
		ParamFactory factory = new ParamFactory();
		/*
		 * variable
		 */
		String variable = "variant";
		/*
		 * set CTM identity of data-type
		 */
		String datatype = writer.getCtmIdentity().getPrefixedIdentity(
				variant.getDatatype());
		/*
		 * generate scope entry if necessary
		 */
		ScopeEntry scopeEntry = null;
		if (!variant.getScope().isEmpty()) {
			List<IEntryParam> params = new LinkedList<IEntryParam>();
			for (Topic theme : variant.getScope()) {
				params.add(factory.newTopicTypeParam(theme));
			}
			scopeEntry = writer.getFactory().getEntryFactory().newScopeEntry(
					params.toArray(new IEntryParam[0]));
		}

		/*
		 * generate reifier entry if necessary
		 */
		ReifierEntry reifierEntry = null;
		if (variant.getReifier() != null) {
			reifierEntry = writer.getFactory().getEntryFactory()
					.newReifierEntry(
							factory.newTopicTypeParam(variant.getReifier()));
		}

		/*
		 * create new occurrence-entry
		 */
		VariantEntry entry = new VariantEntry(writer, factory
				.newVariableParam(variable), datatype);
		entry.setReifierEntry(reifierEntry);
		entry.setScopeEntry(scopeEntry);
		return entry;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof VariantEntry) {
			/*
			 * value or variable name must be equal
			 */
			boolean result = super.equals(obj);
			/*
			 * data-type must be equal
			 */
			if (datatypeAsTopicOrString != null) {
				result &= datatypeAsTopicOrString
						.equals(((VariantEntry) obj).datatypeAsTopicOrString);
			} else {
				result &= ((VariantEntry) obj).datatypeAsTopicOrString == null;
			}
			/*
			 * reifier must be equal
			 */
			if (getReifierEntry() != null) {
				result &= getReifierEntry().equals(
						((VariantEntry) obj).getReifierEntry());
			} else {
				result &= ((VariantEntry) obj).getReifierEntry() == null;
			}
			/*
			 * scope must be equal
			 */
			if (getScopeEntry() != null) {
				result &= getScopeEntry().equals(
						((VariantEntry) obj).getScopeEntry());
			} else {
				result &= ((VariantEntry) obj).getScopeEntry() == null;
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
}
