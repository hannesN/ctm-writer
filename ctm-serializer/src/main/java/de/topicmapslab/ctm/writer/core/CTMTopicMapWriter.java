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

import org.tmapi.core.TopicMap;
import org.tmapix.io.TopicMapWriter;

import de.topicmapslab.ctm.writer.core.serializer.TopicMapSerializer;
import de.topicmapslab.ctm.writer.exception.SerializerException;
import de.topicmapslab.ctm.writer.properties.CTMTopicMapWriterProperties;
import de.topicmapslab.ctm.writer.templates.Template;
import de.topicmapslab.ctm.writer.utility.CTMBuffer;

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
		this.serializer = new TopicMapSerializer(properties, prefixHandler);
		if (propertyLine != null) {
			this.properties.parse(propertyLine);
		}
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

}
