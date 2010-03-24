/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/    
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 *
 */
package de.topicmapslab.ctm.writer.core;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.tmapi.core.Construct;
import org.tmapi.core.TopicMap;
import org.tmapix.io.TopicMapWriter;

import de.topicmapslab.ctm.writer.core.serializer.TopicMapSerializer;
import de.topicmapslab.ctm.writer.exception.SerializerException;
import de.topicmapslab.ctm.writer.properties.CTMTopicMapWriterProperties;
import de.topicmapslab.ctm.writer.templates.Template;
import de.topicmapslab.ctm.writer.templates.TemplateFactory;
import de.topicmapslab.ctm.writer.utility.CTMBuffer;
import de.topicmapslab.ctm.writer.utility.CTMIdentity;

/**
 * Implementation of {@link TopicMapWriter} interface to provide a CTM topic map
 * writer.
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 * 
 */
public class CTMTopicMapWriter implements TopicMapWriter {

	/**
	 * the {@link OutputStream}
	 */
	private final OutputStream outputStream;
	/**
	 * the base-URI
	 */
	private final String baseURI;
	/**
	 * the internal instance of a topic map writer
	 */
	private final TopicMapSerializer serializer;

	/**
	 * the property instance
	 */
	private final CTMTopicMapWriterProperties properties;

	/**
	 * the prefix handler
	 */
	private final PrefixHandler prefixHandler;

	/**
	 * the identity class
	 */
	private final CTMIdentity ctmIdentity;

	/**
	 * the template factory
	 */
	private final TemplateFactory factory;
	
	/**
	 * the list of includes
	 */
	private List<String> includes;
	
	/**
	 * The map containging iris to topic maps as keys and the notation as value.
	 * The notation may only be CTM or XTM
	 */
	private Map<String, String> mergeMaps;
	
	/**
	 * constructor
	 * 
	 * @param outputStream
	 *            the {@link OutputStream} as target for serialized topic map
	 * @param baseURI
	 *            the base URI used to create a default identify for topics
	 *            without item-identifier, subject-identifier and
	 *            subject-locator
	 */
	public CTMTopicMapWriter(final OutputStream outputStream,
			final String baseURI) {
		this(outputStream, baseURI, null);
	}

	/**
	 * constructor
	 * 
	 * @param outputStream
	 *            the {@link OutputStream} as target for serialized topic map
	 * @param baseURI
	 *            the base URI used to create a default identify for topics
	 *            without item-identifier, subject-identifier and
	 *            subject-locator
	 * @param propertyLine
	 *            a argument line, containing internal system properties to set.
	 *            See also: ( {@link CTMTopicMapWriterProperties#parse(String)}
	 */
	public CTMTopicMapWriter(final OutputStream outputStream,
			final String baseURI, final String propertyLine) {
		this.outputStream = outputStream;
		this.baseURI = baseURI;
		this.properties = new CTMTopicMapWriterProperties();
		this.prefixHandler = new PrefixHandler();
		this.ctmIdentity = new CTMIdentity(prefixHandler);
		this.serializer = new TopicMapSerializer(this, prefixHandler);
		if (propertyLine != null) {
			this.properties.parse(propertyLine);
		}

		factory = new TemplateFactory(this);
	}

	/**
	 * Adding a new {@link Template} to the internal {@link TopicMapSerializer}.
	 * Templates are used to extract knowledge about ontology from topic items.
	 * 
	 * @param template
	 *            the template to add
	 */
	public void addTemplate(Template template) {
		this.serializer.add(template);
	}

	/**
	 * Serialize the given topic map to CTM and write it into the given
	 * {@link OutputStream}.
	 * 
	 * @param topicMap
	 *            the topic map to serialize
	 * @throws Exception
	 *             thrown if serialization failed.
	 */
	public void write(TopicMap topicMap) throws IOException {

		CTMBuffer buffer = new CTMBuffer();

		try {
			serializer.serialize(topicMap, buffer);
		} catch (SerializerException e) {
			throw new IOException("Serialization failed, because of "
					+ e.getLocalizedMessage());
		}

		PrintWriter writer = new PrintWriter(outputStream);
		writer.write(buffer.getBuffer().toString());
		writer.flush();
		writer.close();
	}

	/**
	 * Returns the internal baseURI used to create a default identify for topics
	 * without item-identifier, subject-identifier and subject-locator.
	 * 
	 * @return the baseURI
	 */
	public String getBaseURI() {
		return baseURI;
	}

	/**
	 * Searches for the property with the specified key in this property list.
	 * If the key is not found in this property list, the default property list,
	 * and its defaults, recursively, are then checked. The method returns null
	 * if the property is not found.
	 * 
	 * @param key
	 *            the property key
	 * @return the value in this property list with the specified key value.
	 */
	public String getProperty(final String key) {
		return properties.getProperty(key);
	}

	/**
	 * Checks the plausibility of values before setting it.
	 * 
	 * @param key
	 *            the property key
	 * @param value
	 *            the property value
	 */
	public void setProperty(final String key, final String value) {
		this.properties.setProperty(key, value);
	}

	/**
	 * Get the prefix IRI for the given name-space identifier.
	 * 
	 * @param namespace
	 *            the identifier
	 * @return the prefix IRI or <code>null</code> if no prefix is registered
	 *         for given name-space
	 */
	public String getPrefix(final String namespace) {
		return prefixHandler.getPrefix(namespace);
	}

	/**
	 * Register a new prefix definition.
	 * 
	 * @param namespace
	 *            the name-space
	 * @param prefix
	 *            the prefix IRI
	 */
	public void setPrefix(final String namespace, final String prefix) {
		this.prefixHandler.setPrefix(namespace, prefix);
	}

	/**
	 * Serialize the given topic map to CTM and write it into the given
	 * {@link OutputStream}.
	 * 
	 * @param constructs
	 *            a collection containing all constructs to serialize
	 * @throws Exception
	 *             thrown if serialization failed.
	 */
	public void write(Construct... constructs) throws IOException {
		Collection<Construct> constructs_ = new HashSet<Construct>();
		for (Construct c : constructs) {
			constructs_.add(c);
		}
		write(constructs_);
	}

	/**
	 * Serialize the given topic map to CTM and write it into the given
	 * {@link OutputStream}.
	 * 
	 * @param constructs
	 *            a collection containing all constructs to serialize
	 * @throws Exception
	 *             thrown if serialization failed.
	 */
	public void write(Collection<Construct> constructs) throws IOException {
		CTMBuffer buffer = new CTMBuffer();

		try {
			serializer.serialize(constructs, buffer);
		} catch (SerializerException e) {
			throw new IOException("Serialization failed, because of "
					+ e.getLocalizedMessage());
		}

		PrintWriter writer = new PrintWriter(outputStream);
		writer.write(buffer.getBuffer().toString());
		writer.flush();
		writer.close();
	}

	/**
	 * Returns the internal instance of the template factory
	 * 
	 * @return the template factory
	 */
	public TemplateFactory getFactory() {
		return factory;
	}

	/**
	 * Return the identity utility class instance
	 * 
	 * @return the utility class
	 */
	public CTMIdentity getCtmIdentity() {
		return ctmIdentity;
	}

	/**
	 * Returns the properties handler reference.
	 * 
	 * @return the properties handler
	 */
	public CTMTopicMapWriterProperties getProperties() {
		return properties;
	}
	
	/**
	 * Adds an uri to the include list
	 * 
	 * @param uri the uri for an include directive
	 */
	public void addInclude(String uri) {
		if (includes==null)
			includes = new ArrayList<String>();
		includes.add(uri);
	}
	
	/**
	 * Adds an uri to the include list
	 * 
	 * @param uri the uri for an include directive
	 */
	public void removeInclude(String uri) {
		if (includes!=null)
			includes.remove(uri);
	}

	/**
	 * Returns the list of IRIs for include directives
	 * 
	 * @return
	 */
	public List<String> getIncludes() {
		if (includes==null)
			return Collections.emptyList();
		
		return includes;
	}
	
	public void addMergeXTMMap(String iri) {
		if (mergeMaps==null)
			mergeMaps = new HashMap<String, String>();
		
		mergeMaps.put(iri, "XTM");
	}
	
	public void addMergeCTMMap(String iri) {
		if (mergeMaps==null)
			mergeMaps = new HashMap<String, String>();
		
		mergeMaps.put(iri, "CTM");
	}
	
	public void removeMergeMap(String iri) {
		if (mergeMaps!=null)
			mergeMaps.remove(iri);
	}
	
	public Map<String, String> getMergeMaps() {
		if (mergeMaps==null)
			mergeMaps = new HashMap<String, String>();
		return mergeMaps;
	}
}
