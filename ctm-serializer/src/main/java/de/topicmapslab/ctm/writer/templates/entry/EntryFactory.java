package de.topicmapslab.ctm.writer.templates.entry;

import org.tmapi.core.Topic;

import de.topicmapslab.ctm.writer.core.CTMTopicMapWriter;
import de.topicmapslab.ctm.writer.exception.SerializerException;
import de.topicmapslab.ctm.writer.templates.entry.param.IEntryParam;

/**
 * Factory class to create new entries for existing templates.
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 */
public class EntryFactory {

	/**
	 * the parent topic map writer
	 */
	private final CTMTopicMapWriter writer;

	/**
	 * constructor
	 * 
	 * @param writer
	 *            the parent topic map writer
	 */
	public EntryFactory(CTMTopicMapWriter writer) {
		this.writer = writer;
	}

	/**
	 * Creates a new 'kind-of' entry representing a supertype-subtype relation.
	 * 
	 * @param param
	 *            the parameter
	 * @return the created entry
	 */
	public AKindOfEntry newAKindOfEntry(final IEntryParam param) {
		return new AKindOfEntry(writer, param);
	}

	/**
	 * Creates a new 'instance-of' entry representing a type-instance
	 * relationship.
	 * 
	 * @param param
	 *            the parameter
	 * @return the created entry
	 */
	public IsInstanceOfEntry newIsInstanceOfEntry(final IEntryParam param) {
		return new IsInstanceOfEntry(writer, param);
	}

	/**
	 * Creates a new name entry representing a name item of the topic
	 * definition.
	 * 
	 * @param valueOrVariable
	 *            the value or a variable name used as placeholder
	 * @param type
	 *            the name type
	 * @return the created entry
	 */
	public NameEntry newNameEntry(String valueOrVariable, final Topic type) {
		return new NameEntry(writer, valueOrVariable, type);
	}

	/**
	 * Creates a new occurrence entry representing an occurrence item of the
	 * topic definition. The default datatype 'xsd:string' is used.
	 * 
	 * @param valueOrVariable
	 *            the value or a variable name used as placeholder
	 * @param type
	 *            the occurrence type
	 * @return the created entry
	 */
	public OccurrenceEntry newOccurrenceEntry(String valueOrVariable,
			final Topic type) {
		return new OccurrenceEntry(writer, valueOrVariable, type);
	}

	/**
	 * Creates a new occurrence entry representing an occurrence item of the
	 * topic definition.
	 * 
	 * @param valueOrVariable
	 *            the value or a variable name used as place-holder
	 * @param type
	 *            the occurrence type
	 * @param datatypeAsTopicOrString
	 *            the datatype of the occurrence
	 * @return the created entry
	 */
	public OccurrenceEntry newOccurrenceEntry(String valueOrVariable,
			final Topic type, final Object datatypeAsTopicOrString) {
		return new OccurrenceEntry(writer, valueOrVariable, type,
				datatypeAsTopicOrString);
	}

	/**
	 * Creates a new reifier entry representing the reification of a reifiable
	 * item.
	 * 
	 * @param variableOrTopic
	 *            the value or a variable name used as place-holder
	 * @return the created entry
	 */
	public ReifierEntry newReifierEntry(Object variableOrTopic) {
		return new ReifierEntry(writer, variableOrTopic);
	}

	/**
	 * Creates a new scoping entry representing the scope of a scoped item.
	 * 
	 * @param themes
	 *            an array of topic items used as themes
	 * @param variables
	 *            an arrays of variable names used as place-holders
	 * @return the created entry
	 */
	public ScopeEntry newScopeEntry(Topic[] themes, String... variables)
			throws SerializerException {
		return new ScopeEntry(writer, themes, variables);
	}

	/**
	 * Creates a new scoping entry representing the scope of a scoped item.
	 * 
	 * @param themes
	 *            an array of topic items used as themes
	 * @return the created entry
	 */
	public ScopeEntry newScopeEntry(Topic... themes) throws SerializerException {
		return new ScopeEntry(writer, themes);
	}

	/**
	 * Creates a new association entry representing a played-by relationship of
	 * a topic item
	 * 
	 * @param associationTypethe
	 *            association type
	 * @param roleEntries
	 *            an arrays of role entries
	 * @return the created entry
	 */
	public AssociationEntry newAssociationEntry(Topic associationType,
			RoleEntry... roleEntries) {
		return new AssociationEntry(writer, associationType, roleEntries);
	}

	/**
	 * Creates a new role entry used as role constraint in the context of an
	 * association entry.
	 * 
	 * @param roleType
	 *            the role type
	 * @param param
	 *            the parameter representing the player of this role
	 * @return the created entry
	 */
	public RoleEntry newRoleEntry(Topic roleType, IEntryParam param) {
		return new RoleEntry(writer, roleType, param);
	}

	public TopicEntry newTopicEntry(IEntryParam param) {
		return new TopicEntry(param);
	}
}
