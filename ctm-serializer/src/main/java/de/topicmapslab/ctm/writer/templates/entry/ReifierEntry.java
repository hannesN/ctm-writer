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

import de.topicmapslab.ctm.writer.core.CTMTopicMapWriter;
import de.topicmapslab.ctm.writer.exception.SerializerException;
import de.topicmapslab.ctm.writer.templates.entry.param.IEntryParam;
import de.topicmapslab.ctm.writer.templates.entry.param.TopicTypeParam;
import de.topicmapslab.ctm.writer.templates.entry.param.VariableParam;
import de.topicmapslab.ctm.writer.templates.entry.param.WildcardParam;
import de.topicmapslab.ctm.writer.utility.CTMBuffer;

/**
 * Class representing a template-entry definition of an reifier-entry.
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 * 
 */
public class ReifierEntry {

	/**
	 * the parent writer
	 */
	private final CTMTopicMapWriter writer;
	/**
	 * the reifier param
	 */
	private final IEntryParam reifier;

	/**
	 * constructor
	 * 
	 * @param writer
	 *            the parent topic map writer
	 * @param reifier
	 *            the variable representing the value for reification
	 * @throws SerializerException
	 */
	protected ReifierEntry(CTMTopicMapWriter writer, IEntryParam reifier) {
		this.reifier = reifier;
		this.writer = writer;
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
		if (reifier instanceof TopicTypeParam) {
			buffer.append(REIFIER, writer.getCtmIdentity().getMainIdentifier(
					writer.getProperties(),
					((TopicTypeParam) reifier).getTopic()).toString());
		} else if (reifier instanceof WildcardParam) {
			buffer.append(REIFIER, reifier.getCTMRepresentation());
		} else if (reifier instanceof VariableParam) {
			buffer.append(REIFIER, reifier.getCTMRepresentation());
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
		if (reifier instanceof TopicTypeParam) {
			if (reifiable.getReifier() == null) {
				return false;
			} else {
				return reifiable.getReifier().equals(
						((TopicTypeParam) reifier).getTopic());
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
		if (reifier instanceof TopicTypeParam) {
			return ((TopicTypeParam) reifier).getTopic();
		}
		return null;
	}

	/**
	 * Method returns the internal value
	 * 
	 * @return the reifierOrVariable
	 */
	public IEntryParam getReifierParameter() {
		return reifier;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ReifierEntry) {
			return super.equals(obj)
					&& reifier.equals(((ReifierEntry) obj).reifier);
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
