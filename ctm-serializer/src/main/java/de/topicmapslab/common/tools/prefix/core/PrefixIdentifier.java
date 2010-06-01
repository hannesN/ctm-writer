/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/    
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 *
 */
package de.topicmapslab.common.tools.prefix.core;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.tmapi.core.Locator;
import org.tmapi.core.Topic;
import org.tmapi.core.TopicMap;

import de.topicmapslab.common.tools.collections.Maps;
import de.topicmapslab.common.tools.collections.StringList;
import de.topicmapslab.common.tools.exception.ToolRuntimeException;
import de.topicmapslab.common.tools.prefix.model.IQnameProvider;
import de.topicmapslab.ctm.writer.exception.SerializerException;
import de.topicmapslab.identifier.XmlSchemeDatatypes;

/**
 * Utility class to identify prefixes of a given {@link TopicMap}. This class
 * provides methods to extract all {@link Locator} for all topic map construct,
 * extract all potential prefix candidates and identifying the frequently used
 * prefixes.
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 * 
 */
public class PrefixIdentifier {

	/**
	 * Static method to identifying all prefixes of the topic map and return
	 * them as a map of QNames and prefixes. Method is calling
	 * {@link PrefixIdentifier#prefixMap(TopicMap, IQnameProvider)} by using the
	 * default {@link IQnameProvider} - {@link DomainQnameProvider}.
	 * 
	 * 
	 * @param topicMap
	 *            the {@link TopicMap}
	 * @return a map of identified prefixes and their QNames
	 * @throws SerializerException
	 *             thrown if operation failed.
	 */
	public static Map<String, String> prefixMap(TopicMap topicMap)
			throws SerializerException {
		return prefixMap(topicMap, new DomainQnameProvider());
	}

	/**
	 * Static method to identify all prefixes of the topic map and return them
	 * as a map of QNames and prefixes. QNames are provided by the given
	 * {@link IQnameProvider}.
	 * 
	 * @param topicMap
	 *            the {@link TopicMap}
	 * @param provider
	 *            the {@link IQnameProvider} to transform the prefixes to a
	 *            QName
	 * @return a map of identified prefixes and their QNames
	 * @throws SerializerException
	 *             thrown if operation failed.
	 */
	public static Map<String, String> prefixMap(TopicMap topicMap,
			IQnameProvider provider) throws SerializerException {
		Map<String, String> prefixes = new HashMap<String, String>();

		/*
		 * iterate over all identified prefixes
		 */
		for (String prefix : identifyPrefixes(topicMap)) {
			try {
				/*
				 * get QName for prefix
				 */
				String qname = provider.generateQname(prefix);
				/*
				 * check if QName is unique
				 */
				if (prefixes.containsKey(qname)) {
					/*
					 * try to unify the QName by adding a number
					 */
					for (int i = 2; i < Integer.MAX_VALUE; i++) {
						final String tmp = qname + i;
						if (!prefixes.containsKey(tmp)) {
							qname = tmp;
							break;
						}
					}
				}
				/*
				 * store result
				 */
				prefixes.put(qname, prefix);
			} catch (ToolRuntimeException e) {
				throw new SerializerException(e);
			}
		}
		/*
		 * add XSD-data-type
		 */
		prefixes.put(XmlSchemeDatatypes.XSD_QNAME, XmlSchemeDatatypes.XSD_BASE);
		return prefixes;
	}

	/**
	 * Static method to identify all prefixes of the {@link TopicMap}.
	 * 
	 * @param topicMap
	 *            the {@link TopicMap}
	 * @return a list of all identified prefixes
	 * @throws SerializerException
	 *             thrown if operation failed.
	 */
	public static List<String> identifyPrefixes(TopicMap topicMap)
			throws SerializerException {

		/*
		 * extract all locators
		 */
		Set<Locator> locators = extractLocators(topicMap);
		/*
		 * extract all candidates
		 */
		Map<String, Integer> candidates = extractCandidates(locators);

		/*
		 * sort map entries by there frequency
		 */
		List<Entry<String, Integer>> sortedCandidates = Maps.sort(candidates,
				new Comparator<Integer>() {
					public int compare(Integer o1, Integer o2) {
						return o1.compareTo(o2);
					}
				}, true, true, true);

		/*
		 * result list of prefixes
		 */
		StringList prefixes = new StringList();

		/*
		 * iterate all candidates
		 */
		for (Entry<String, Integer> entry : sortedCandidates) {
			/*
			 * check if frequency is more than one
			 */
			if (entry.getValue() > 1) {
				String candidate = entry.getKey();
				/*
				 * check if a prefix is already contained in result list, which
				 * contains the current prefix as substring.
				 */
				if (!prefixes.containsSubstring(candidate)
						&& !prefixes.containsAsSubstring(candidate)) {
					prefixes.add(candidate);
				}
			}
		}
		return prefixes;
	}

	/**
	 * Static Method to extract all locators of a topic map used as
	 * subject-identifier or subject-locator.
	 * 
	 * @param topicMap
	 *            the {@link TopicMap}
	 * @return a {@link Set} of extracted {@link Locator}
	 */
	public static Set<Locator> extractLocators(TopicMap topicMap) {
		Set<Locator> locators = new HashSet<Locator>();
		/*
		 * iterate over all topics
		 */
		for (Topic topic : topicMap.getTopics()) {
			/*
			 * add all subject-identifier
			 */
			locators.addAll(topic.getSubjectIdentifiers());
			/*
			 * add all subject-locator
			 */
			locators.addAll(topic.getSubjectLocators());
		}
		return locators;
	}

	/**
	 * Static Method to extract all candidates, which can be used as prefixes,
	 * ordered by there frequency.
	 * 
	 * @param locators
	 *            a {@link Set} of {@link Locator}
	 * @return a map of prefix candidates and the frequency
	 */
	public static Map<String, Integer> extractCandidates(Set<Locator> locators) {
		Map<String, Integer> candidates = new HashMap<String, Integer>();

		/*
		 * iterate over locators
		 */
		for (Locator locator : locators) {
			/*
			 * split IRI of the locator to prefixes
			 */

			String iri = locator.toExternalForm();
			int indexHash = iri.lastIndexOf("#");
			int indexSlash = iri.lastIndexOf("/");

			if (indexHash > indexSlash) {
				iri = iri.substring(0, indexHash + 1);
			} else if (indexHash < indexSlash) {
				iri = iri.substring(0, indexSlash + 1);
			}

			/*
			 * check if prefix is already known
			 */
			Integer count = candidates.get(iri);
			if (count == null) {
				count = 1;
			} else {
				count++;
			}
			candidates.put(iri, count);

			// PrefixerTokenizer tokenizer = new PrefixerTokenizer(locator);
			// for (String candidate : tokenizer.getTokens()) {
			// /*
			// * check if prefix is already known
			// */
			// Integer count = candidates.get(candidate);
			// if (count == null) {
			// count = 1;
			// } else {
			// count++;
			// }
			// candidates.put(candidate, count);
			// }
		}

		return candidates;
	}

}
