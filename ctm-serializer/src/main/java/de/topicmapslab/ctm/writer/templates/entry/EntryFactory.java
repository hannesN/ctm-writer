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
	 * @param value
	 *            the value or a variable name used as placeholder
	 * @param type
	 *            the name type
	 * @return the created entry
	 */
	public NameEntry newNameEntry(IEntryParam value, final IEntryParam type) {
		return new NameEntry(writer, value, type);
	}

	/**
	 * Creates a new variant entry representing a variant constraint in the
	 * context of a name entry.
	 * 
	 * @param value
	 *            the value of the variant
	 * @return the created variant
	 */
	public VariantEntry newVariantEntry(IEntryParam value) {
		return new VariantEntry(writer, value);
	}

	/**
	 * Creates a new variant entry representing a variant constraint in the
	 * context of a name entry.
	 * 
	 * @param value
	 *            the value of the variant
	 * @param datatype
	 *            the datatype
	 * @return the created variant
	 */
	public VariantEntry newVariantEntry(IEntryParam value, final Object datatype) {
		return new VariantEntry(writer, value, datatype);
	}

	/**
	 * Creates a new occurrence entry representing an occurrence item of the
	 * topic definition. The default datatype 'xsd:string' is used.
	 * 
	 * @param value
	 *            the value or a variable name used as placeholder
	 * @param type
	 *            the occurrence type
	 * @return the created entry
	 */
	public OccurrenceEntry newOccurrenceEntry(IEntryParam value,
			final IEntryParam type) {
		return new OccurrenceEntry(writer, value, type);
	}

	/**
	 * Creates a new occurrence entry representing an occurrence item of the
	 * topic definition.
	 * 
	 * @param value
	 *            the value or a variable name used as place-holder
	 * @param type
	 *            the occurrence type
	 * @param datatypeAsTopicOrString
	 *            the datatype of the occurrence
	 * @return the created entry
	 */
	public OccurrenceEntry newOccurrenceEntry(IEntryParam value,
			final IEntryParam type, final Object datatypeAsTopicOrString) {
		return new OccurrenceEntry(writer, value, type, datatypeAsTopicOrString);
	}

	/**
	 * Creates a new reifier entry representing the reification of a reifiable
	 * item.
	 * 
	 * @param reifier
	 *            the value or a variable name used as place-holder
	 * @return the created entry
	 */
	public ReifierEntry newReifierEntry(IEntryParam reifier) {
		return new ReifierEntry(writer, reifier);
	}

	/**
	 * Creates a new scoping entry representing the scope of a scoped item.
	 * 
	 * @param params
	 *            an array of parameters acts as themes
	 * @return the created entry
	 */
	public ScopeEntry newScopeEntry(IEntryParam... params)
			throws SerializerException {
		return new ScopeEntry(writer, params);
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

	/**
	 * Creates a new topic entry used as embed topic definition in the context
	 * of the template.
	 * 
	 * @param param
	 *            the topic identifier
	 * @return the created entry
	 */
	public TopicEntry newTopicEntry(IEntryParam param) {
		return new TopicEntry(param);
	}
}
