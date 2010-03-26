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
import static de.topicmapslab.ctm.writer.utility.CTMTokens.COLON;
import static de.topicmapslab.ctm.writer.utility.CTMTokens.COMMA;
import static de.topicmapslab.ctm.writer.utility.CTMTokens.TABULATOR;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.tmapi.core.Association;
import org.tmapi.core.Construct;
import org.tmapi.core.Role;
import org.tmapi.core.Topic;
import org.tmapi.index.TypeInstanceIndex;

import de.topicmapslab.ctm.writer.core.CTMTopicMapWriter;
import de.topicmapslab.ctm.writer.exception.SerializerException;
import de.topicmapslab.ctm.writer.templates.entry.base.IEntry;
import de.topicmapslab.ctm.writer.templates.entry.param.IEntryParam;
import de.topicmapslab.ctm.writer.templates.entry.param.VariableParam;
import de.topicmapslab.ctm.writer.templates.entry.param.WildcardParam;
import de.topicmapslab.ctm.writer.utility.CTMBuffer;
import de.topicmapslab.ctm.writer.utility.TraversalUtilis;

/**
 * Class representing a template-entry definition of a association-entry.
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 * 
 */
public class AssociationEntry implements IEntry {

	/**
	 * the parent topic map writer
	 */
	private final CTMTopicMapWriter writer;

	/**
	 * the association type
	 */
	private final Topic associationType;
	/**
	 * the role-entries, as part of entry definition
	 */
	private final Set<RoleEntry> roleEntries = new HashSet<RoleEntry>();
	/**
	 * a list of all variables
	 */
	private final Set<String> variables = new HashSet<String>();

	/**
	 * constructor
	 * 
	 * @param writer
	 *            the parent topic map writer
	 * @param associationType
	 *            the association type supported by this template
	 * @param roleEntries
	 *            a possible empty list of all role-entries
	 */
	protected AssociationEntry(CTMTopicMapWriter writer, Topic associationType,
			RoleEntry... roleEntries) {
		this.associationType = associationType;
		this.writer = writer;
		for (RoleEntry entry : roleEntries) {
			this.roleEntries.add(entry);
			if (entry.getParameterAsString().startsWith("$")) {
				variables.add(entry.getParameterAsString());
			}
		}
	}

	/**
	 * Returns the association type of the associations which are adaptive for
	 * this entry.
	 * 
	 * @return the associationType the association type
	 */
	public Topic getAssociationType() {
		return associationType;
	}

	/**
	 * {@inheritDoc}
	 */
	public void serialize(CTMBuffer buffer) throws SerializerException {
		buffer.appendLine(true, writer.getCtmIdentity().getMainIdentifier(
				writer.getProperties(), getAssociationType()).toString(), BRO);
		boolean first = true;
		for (RoleEntry entry : roleEntries) {
			if (!first) {
				buffer.appendLine(COMMA);
			}
			buffer.append(TABULATOR, TABULATOR, writer.getCtmIdentity()
					.getMainIdentifier(writer.getProperties(),
							entry.getRoleType()).toString(), COLON, entry
					.getParameterAsString());
			first = false;
		}
		buffer.appendLine();
		buffer.appendLine(false, TABULATOR, BRC);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isAdaptiveFor(Construct construct) {
		if (construct instanceof Topic) {
			return isAdaptiveFor((Topic) construct);
		} else if (construct instanceof Association) {
			return isAdaptiveFor((Association) construct);
		}
		return false;
	}

	/**
	 * Check if entry is adaptive for given association.
	 * 
	 * @param association
	 *            the association
	 * @return <code>true</code> if the entry can replaced a part of the given
	 *         association, this means if the type and all roles an adaptive
	 *         for.
	 */
	public boolean isAdaptiveFor(Association association) {
		boolean adaptiveFor = true;
		for (RoleEntry entry : roleEntries) {
			if (!entry.isAdaptiveFor(association)) {
				adaptiveFor = false;
				break;
			}
		}
		return adaptiveFor && associationType.equals(association.getType());
	}

	/**
	 * Check if entry is adaptive for given topic.
	 * 
	 * @param topic
	 *            the topic
	 * @return <code>true</code> if the entry can replaced a part of the given
	 *         topic, this means if the type and at least one association item
	 *         of this type is adaptive for.
	 */
	public boolean isAdaptiveFor(Topic topic) {
		TypeInstanceIndex index = topic.getTopicMap().getIndex(
				TypeInstanceIndex.class);
		for (Association association : index.getAssociations(associationType)) {
			if (!isAdaptiveFor(association)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public IEntryParam getParameter() {
		return null;
	}

	/**
	 * Return all variables defined by internal role-entries.
	 * 
	 * @return a set of all variables
	 */
	public Set<String> getValueOrVariables() {
		return variables;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isDependentFromVariable() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<String> extractArguments(Topic type, Construct construct,
			Set<Object> affectedConstructs) throws SerializerException {
		if (!isAdaptiveFor(construct)) {
			throw new SerializerException(
					"template entry is not adaptive for given construct.");
		}

		List<String> arguments = new LinkedList<String>();
		/*
		 * construct is an association
		 */
		if (construct instanceof Association) {
			Association association = (Association) construct;
			/*
			 * iterate over roles and extract players as arguments
			 */
			Set<RoleEntry> wildcards = new HashSet<RoleEntry>();
			Topic roleType = null;
			for (RoleEntry entry : roleEntries) {
				if (entry.getParameter() instanceof VariableParam) {
					Role role = association.getRoles(entry.getRoleType())
							.iterator().next();
					arguments.add(writer.getCtmIdentity().getMainIdentifier(
							writer.getProperties(), role.getPlayer())
							.toString());
					roleType = role.getType();
				} else if (entry.getParameter() instanceof WildcardParam) {
					wildcards.add(entry);
				}
			}
			for ( RoleEntry wildcard : wildcards){
				arguments.addAll(extractTraversalArgument(association,
						affectedConstructs, wildcard, roleType));
			}
		}
		/*
		 * construct is a topic type
		 */
		else if (construct instanceof Topic) {
			Topic topic = (Topic) construct;
			TypeInstanceIndex index = topic.getTopicMap().getIndex(
					TypeInstanceIndex.class);
			for (Association association : index
					.getAssociations(associationType)) {
				/*
				 * check if association is adaptive for
				 */
				if (isAdaptiveFor(association)) {
					/*
					 * return arguments
					 */
					return extractArguments(type, association,
							affectedConstructs);
				}
			}
		}
		return arguments;
	}

	private List<String> extractTraversalArgument(Association association,
			Set<Object> affectedConstructs, RoleEntry wildcard, Topic roleType)
			throws SerializerException {
		List<String> arguments = new LinkedList<String>();

		Set<Role> roles = association.getRoles(wildcard.getRoleType());

		if (roles.size() == 1) {
			Set<Association> associations = TraversalUtilis
					.getTraversalAssociations(roles.iterator().next()
							.getPlayer(), association);
			if (associations.size() == 1) {
				Association a = associations.iterator().next();
				Role role = a.getRoles(roleType).iterator()
						.next();
				arguments.add(writer.getCtmIdentity().getMainIdentifier(
						writer.getProperties(), role.getPlayer()).toString());
				affectedConstructs.add(a);
			}
		}
		return arguments;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AssociationEntry) {
			return super.equals(obj)
					&& associationType
							.equals(((AssociationEntry) obj).associationType)
					&& roleEntries
							.containsAll(((AssociationEntry) obj).roleEntries);
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
		for (RoleEntry entry : roleEntries) {
			if (entry.getParameterAsString().startsWith("$")) {
				variables.add(entry.getParameterAsString());
			}
		}
		return variables;
	}
}
