/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/    
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 *
 */
package de.topicmapslab.ctm.writer.utility;

import static de.topicmapslab.ctm.writer.utility.CTMTokens.PREFIXBEGIN;
import static de.topicmapslab.ctm.writer.utility.CTMTokens.PREFIXEND;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.tmapi.core.Locator;
import org.tmapi.core.Topic;

import de.topicmapslab.common.tools.URIEncoder;
import de.topicmapslab.common.tools.prefix.core.Prefixer;
import de.topicmapslab.ctm.writer.core.PrefixHandler;
import de.topicmapslab.ctm.writer.core.serializer.PrefixesSerializer;
import de.topicmapslab.ctm.writer.exception.NoIdentityException;
import de.topicmapslab.ctm.writer.properties.CTMTopicMapWriterProperties;
import de.topicmapslab.ctm.writer.utility.CTMMainIdentifier.IdentifierType;
import de.topicmapslab.java.tmdm.TmdmSubjectIdentifier;

/**
 * Utility class to transform TMAPI identifier to CTM identifier
 * representations.
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 * 
 */
public class CTMIdentity {

	/**
	 * internal cache to store the first identifier generated for a topic
	 */
	private Map<Topic, CTMMainIdentifier> idMap;

	private final PrefixHandler prefixHandler;

	/**
	 * base constructor
	 * 
	 * @param prefixHandler
	 *            the prefix handler
	 */
	public CTMIdentity(final PrefixHandler prefixHandler) {
		this.prefixHandler = prefixHandler;
	}

	/**
	 * Returns the main identifier used to identify the topic map construct in
	 * the CTM file.
	 * 
	 * @param properties
	 *            the internal {@link CTMTopicMapWriterProperties}
	 * @param topic
	 *            the {@link Topic}
	 * 
	 * @return the generated CTM item-identifier
	 * @throws NoIdentityException
	 */
	public CTMMainIdentifier getMainIdentifier(
			final CTMTopicMapWriterProperties properties, Topic topic)
			throws NoIdentityException {

		if (idMap == null)
			idMap = new HashMap<Topic, CTMMainIdentifier>();

		CTMMainIdentifier prefixedIdentity = getPrefixedIdentity(properties,
				topic);

		idMap.put(topic, prefixedIdentity);

		return prefixedIdentity;
	}

	public final String getEscapedCTMIdentity(final String identity,
			final Locator locator) {

		String id = identity;

		try {
			id = URIEncoder.encodeURI(id, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		if ((!identity.equals(locator.toExternalForm()))
				&& (identity.contains(":")) && !id.contains("%")
				&& !id.contains(",")) {
			return id;
		}
		return PREFIXBEGIN + id + PREFIXEND;
	}

	/**
	 * method to transform the given locator to a prefixed CTM identity. Method
	 * is calling {@link Prefixer#toPrefixedIri(String, java.util.Map)} with
	 * arguments {@link Locator#toExternalForm()} and
	 * {@link PrefixesSerializer#knownPrefixes}
	 * 
	 * @param locator
	 *            the locator of a topic
	 * @return the prefixed identifier.
	 */
	public String getPrefixedIdentity(Locator locator) {
		return Prefixer.toPrefixedIri(locator.toExternalForm(), prefixHandler
				.getPrefixMap());
	}

	/**
	 * method to transform the given topic to a prefixed CTM identity. Method is
	 * calling {@link Prefixer#toPrefixedIri(String, java.util.Map)))} with
	 * arguments {@link Locator#toExternalForm()} and
	 * {@link PrefixesSerializer#knownPrefixes}
	 * 
	 * 
	 * @param properties
	 *            the internal {@link CTMTopicMapWriterProperties}
	 * @param topic
	 *            the topic
	 * @return the prefixed identifier.
	 * @throws NoIdentityException
	 *             thrown if topic has no identity ( no item-identifier, no
	 *             subject-locator and no subject-locator )
	 */
	public CTMMainIdentifier getPrefixedIdentity(
			final CTMTopicMapWriterProperties properties, Topic topic)
			throws NoIdentityException {
		CTMMainIdentifier identifier = getIdentity(properties, topic);

		String iri = Prefixer.toPrefixedIri(identifier.getIdentifier(),
				prefixHandler.getPrefixMap());

		identifier.setIdentifier(iri);
		return identifier;
	}

	/**
	 * method to extract one identity of the given topic. The method is looking
	 * for subject-identifier, subject-locator and item-identifier. The method
	 * ignores default TMDM identifiers and internal identifiers of the topic
	 * maps engine.
	 * 
	 * @param properties
	 *            the internal {@link CTMTopicMapWriterProperties}
	 * @param topic
	 *            the topic
	 * 
	 * 
	 * @return one identity of the given topic and never <code>null</code>
	 * @throws NoIdentityException
	 *             thrown if topic has no identity ( no item-identifier, no
	 *             subject-locator and no subject-locator )
	 */
	public CTMMainIdentifier getIdentity(
			final CTMTopicMapWriterProperties properties, Topic topic)
			throws NoIdentityException {
		/*
		 * store system locator
		 */
		Locator fallback = null;
		/*
		 * check subject-identifier
		 */
		for (Locator locator : topic.getSubjectIdentifiers()) {
			/*
			 * check if subject-identifier is a default TMDM identifier
			 */
			if (!TmdmSubjectIdentifier.isTmdmSubjectIdentifier(locator
					.toExternalForm())) {
				return new CTMMainIdentifier(prefixHandler, locator
						.toExternalForm(), IdentifierType.SUBJECT_IDENTIFIER);
			}
			/*
			 * store as fall-back
			 */
			else {
				fallback = locator;
			}
		}

		/*
		 * check subject-locator
		 */
		if (!topic.getSubjectLocators().isEmpty()) {
			Locator locator = topic.getSubjectLocators().iterator().next();
			return new CTMMainIdentifier(prefixHandler, locator
					.toExternalForm(), IdentifierType.SUBJECT_LOCATOR);
		}

		/*
		 * check item-identifier
		 */
		for (Locator locator : topic.getItemIdentifiers()) {
			if (!isSystemItemIdentifier(properties, locator)) {
				return new CTMMainIdentifier(prefixHandler, locator
						.toExternalForm(), IdentifierType.ITEM_IDENTIFIER);
			}
			/*
			 * store as fall-back
			 */
			else {
				fallback = locator;
			}
		}

		/*
		 * topic has only the auto generated
		 */
		if (fallback != null) {
			return new CTMMainIdentifier(prefixHandler, fallback
					.toExternalForm(), IdentifierType.SUBJECT_IDENTIFIER);
		}

		throw new NoIdentityException("topic has no identity.");
	}

	/**
	 * method to check if the given locator is an internal locator created by
	 * the topic map engine. Method is calling
	 * {@link CTMIdentity#isSystemItemIdentifier(String, CTMTopicMapWriterProperties)
	 * )} with the argument {@link Locator#toExternalForm()}.
	 * 
	 * @param properties
	 *            the internal {@link CTMTopicMapWriterProperties}
	 * @param locator
	 *            the locator to check
	 * @return <code>true</code> if the given locator is an internal topic map
	 *         engine locator, <code>false</code> otherwise
	 */
	public boolean isSystemItemIdentifier(
			final CTMTopicMapWriterProperties properties, final Locator locator) {
		return isSystemItemIdentifier(properties, locator.toExternalForm());
	}

	/**
	 * method to check if the given locator is an internal locator created by
	 * the topic map engine.
	 * 
	 * @param properties
	 *            the internal {@link CTMTopicMapWriterProperties}
	 * @param iri
	 *            the IRI to check
	 * @return <code>true</code> if the given IRI is an internal topic map
	 *         engine IRI, <code>false</code> otherwise
	 */
	public boolean isSystemItemIdentifier(
			final CTMTopicMapWriterProperties properties, final String iri) {
		return iri.startsWith(properties.getEnginePrefix());
	}

}
