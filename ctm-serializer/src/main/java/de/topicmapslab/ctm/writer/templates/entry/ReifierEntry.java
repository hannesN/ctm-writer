/*
 * Copyright: Copyright 2010Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/    
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 *
 */
package de.topicmapslab.ctm.writer.templates.entry;

import static de.topicmapslab.ctm.writer.utility.CTMTokens.REIFIER;

import org.tmapi.core.Reifiable;
import org.tmapi.core.Topic;

import de.topicmapslab.ctm.writer.exception.SerializerException;
import de.topicmapslab.ctm.writer.properties.CTMTopicMapWriterProperties;
import de.topicmapslab.ctm.writer.utility.CTMBuffer;
import de.topicmapslab.ctm.writer.utility.CTMIdentity;

/**
 * Class representing a template-entry definition of an reifier-entry.
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 * 
 */
public class ReifierEntry {

	/**
	 * properties for CTM topic map writer
	 */
	private final CTMTopicMapWriterProperties properties;
	/**
	 * the reifierOrVariable
	 */
	private final Object reifierOrVariable;

	/**
	 * identity utility (cache and generator)
	 */
	private final CTMIdentity ctmIdentity;
	
	/**
	 * constructor
	 *  @param properties
	 *            the properties
	 * @param variable
	 *            the variable representing the value for reification
	 * @throws SerializerException
	 */
	public ReifierEntry(CTMTopicMapWriterProperties properties, CTMIdentity ctmIdentity, String variable) {
		this.reifierOrVariable = variable;
		this.properties = properties;
		this.ctmIdentity = ctmIdentity;
	}

	/**
	 * constructor
	 *  @param properties
	 *            the properties
	 * @param reifier
	 *            the reifier used for reification of
	 * @throws SerializerException
	 */
	public ReifierEntry(CTMTopicMapWriterProperties properties, CTMIdentity ctmIdentity, Topic reifier) {
		this.reifierOrVariable = reifier;
		this.properties = properties;
		this.ctmIdentity = ctmIdentity;
	}

	/**
	 * Method to convert the given reifier to its specific CTM string. The
	 * result should be written to the given output buffer.
	 * 
	 * @param buffer
	 *            the output buffer
	 * @throws SerializerException
	 *             Thrown if serialization failed.
	 */
	public void serialize(CTMBuffer buffer) throws SerializerException {
		if (reifierOrVariable instanceof Topic) {
			// buffer.append(REIFIER, CTMIdentity
			// .getPrefixedIdentity((Topic) reifierOrVariable));
			buffer.append(REIFIER, ctmIdentity
					.getMainIdentifier(properties,(Topic) reifierOrVariable).toString());
		} else {
			buffer.append(REIFIER, reifierOrVariable.toString());
		}
	}

	/**
	 * Check if entry is adaptive for given reifiable element.
	 * 
	 * @param reifiable
	 *            the reifiable element
	 * @return <code>true</code> if the entry can replaced a part of the given
	 *         reifiable element.
	 */
	public boolean isAdaptiveFor(Reifiable reifiable) {
		if (reifierOrVariable instanceof Topic) {
			if (reifiable.getReifier() == null) {
				return false;
			} else {
				return reifiable.getReifier().equals((Topic) reifierOrVariable);
			}
		}
		return true;
	}

	/**
	 * Method returns the internal reifier value
	 * 
	 * @return the reifier or <code>null</code> if internal value is a variable
	 */
	public Topic getReifier() {
		if (reifierOrVariable instanceof Topic) {
			return (Topic) reifierOrVariable;
		}
		return null;
	}

	/**
	 * Method returns the internal value
	 * 
	 * @return the reifierOrVariable
	 */
	public Object getReifierOrVariable() {
		return reifierOrVariable;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ReifierEntry) {
			return super.equals(obj)
					&& reifierOrVariable
							.equals(((ReifierEntry) obj).reifierOrVariable);
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
