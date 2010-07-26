import java.io.File;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.tmapi.core.Topic;
import org.tmapi.core.TopicMap;
import org.tmapi.core.TopicMapSystemFactory;
import org.tmapix.io.CTMTopicMapReader;

import de.topicmapslab.ctm.writer.core.CTMTopicMapWriter;
import de.topicmapslab.ctm.writer.templates.Template;
import de.topicmapslab.ctm.writer.templates.TemplateFactory;
import de.topicmapslab.ctm.writer.templates.entry.AssociationEntry;
import de.topicmapslab.ctm.writer.templates.entry.EntryFactory;
import de.topicmapslab.ctm.writer.templates.entry.OccurrenceEntry;
import de.topicmapslab.ctm.writer.templates.entry.RoleEntry;
import de.topicmapslab.ctm.writer.templates.entry.TopicEntry;
import de.topicmapslab.ctm.writer.templates.entry.param.IEntryParam;

/* 
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/    
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 *
 */

/**
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 * 
 */
public class TestTMCL extends TestCase {

	public void testTemplate() throws Exception {
		TopicMap map = TopicMapSystemFactory.newInstance().newTopicMapSystem()
				.createTopicMap("http://psi.example.org");
		File file = new File("src/test/resources/test.ctm");
		CTMTopicMapReader reader = new CTMTopicMapReader(map, file);
		reader.read();

		CTMTopicMapWriter writer = new CTMTopicMapWriter(System.out,
				"http://psi.example.org");
		TMCLTemplateDefinitions definitions = new TMCLTemplateDefinitions(
				writer, map);
		for (Template template : definitions.getTemplates()) {
			writer.addTemplate(template);
		}

		writer.write(map);
	}

}

/**
 * A class which creates {@link Template}s for the CTM Serializer.
 * 
 * @author Lorenz B�hmann
 * 
 */
class TMCLTemplateDefinitions {

	/**
	 * General prefix for the types.
	 */
	public final static String TMDMPREFIX = "http://psi.topicmaps.org/iso13250/model/";
	/**
	 * subject-identifier of the name-type of the topic maps data model
	 */
	public static final String TYPE = TMDMPREFIX + "type";
	/**
	 * subject-identifier of the name-type of the topic maps data model
	 */
	public static final String INSTANCE = TMDMPREFIX + "instance";
	/**
	 * subject-identifier of the name-type of the topic maps data model
	 */
	public static final String SUBTYPE = TMDMPREFIX + "subtype";
	/**
	 * subject-identifier of the name-type of the topic maps data model
	 */
	public static final String SUPERTYPE = TMDMPREFIX + "supertype";
	/**
	 * subject-identifier of the name-type of the topic maps data model
	 */
	public static final String TYPE_INSTANCE = TMDMPREFIX + "type-instance";
	/**
	 * subject-identifier of the name-type of the topic maps data model
	 */
	public static final String SUPERTYPE_SUBTYPE = TMDMPREFIX
			+ "supertype-subtype";
	/**
	 * subject-identifier of the default name-type of the topic maps data model
	 */
	public static final String TOPIC_NAME = TMDMPREFIX + "topic-name";
	/**
	 * subject-identifier of the tmdm:subject specified in TMCL
	 */
	public static final String SUBJECT = TMDMPREFIX + "subject";

	/**
	 * General prefix for the types.
	 */
	public final static String PREFIX = "http://psi.topicmaps.org/tmcl/";

	// types
	public static String TOPIC_TYPE = PREFIX + "topic-type";

	public static String NAME_TYPE = PREFIX + "name-type";

	public static String OCCURRENCE_TYPE = PREFIX + "occurrence-type";

	public static String ROLE_TYPE = PREFIX + "role-type";

	public static String TOPIC_MAP = PREFIX + "topic-map";

	// constraints

	public static String CONSTRAINT = PREFIX + "constraint";

	public static String ABSTRACT_CONSTRAINT = PREFIX + "abstract-constraint";

	public static String DENIAL_CONSTRAINT = PREFIX + "denial-constraint";

	public static String ASSOCIATION_ROLE_CONSTRAINT = PREFIX
			+ "association-role-constraint";

	public static String OCCURRENCE_DATATYPE_CONSTRAINT = PREFIX
			+ "occurrence-datatype-constraint";

	public static String ITEM_IDENTIFIER_CONSTRAINT = PREFIX
			+ "item_identifier-constraint";

	public static String REGULAR_EXPRESSION_CONSTRAINT = PREFIX
			+ "regular-expression-constraint";

	public static String SCOPE_REQUIRED_CONSTRAINT = PREFIX
			+ "scope-required-constraint";

	public static String SUBJECT_IDENTIFIER_CONSTRAINT = PREFIX
			+ "subject-identifier-constraint";

	public static String SUBJECT_LOCATOR_CONSTRAINT = PREFIX
			+ "subject-locator-constraint";

	public static String TOPIC_NAME_CONSTRAINT = PREFIX
			+ "topic-name-constraint";

	public static String TOPIC_OCCURRENCE_CONSTRAINT = PREFIX
			+ "topic-occurrence-constraint";

	public static String TOPIC_REIFIES_CONSTRAINT = PREFIX
			+ "topic-reifies-constraint";

	public static String TOPIC_ROLE_CONSTRAINT = PREFIX
			+ "topic-role-constraint";

	public static String ROLE_COMBINATION_CONSTRAINT = PREFIX
			+ "role-combination-constraint";

	public static String OVERLAP_DECLARATION = PREFIX + "overlap-declaration";

	public static String ALLOWED = PREFIX + "allowed";
	public static String ALLOWED_REIFIER = PREFIX + "allowed-reifier";
	public static String ALLOWS = PREFIX + "allows";
	public static String ASSOCIATION_TYPE = PREFIX + "association-type";
	public static String BELONGS_TO = PREFIX + "belongs-to";
	public static String CARD_MAX = PREFIX + "card-max";
	public static String CARD_MIN = PREFIX + "card-min";
	public static String COMMENT = PREFIX + "comment";
	public static String CONSTRAINED = PREFIX + "constrained";
	public static String CONSTRAINED_CONSTRUCT = PREFIX
			+ "constrained-construct";
	public static String CONSTRAINED_ROLE = PREFIX + "constrained-role";
	public static String CONSTRAINED_SCOPE = PREFIX + "constrained-scope";
	public static String CONSTRAINED_SCOPE_TOPIC = PREFIX
			+ "constrained-scope-topic";
	public static String CONSTRAINED_STATEMENT = PREFIX
			+ "constrained-statement";
	public static String CONSTRAINED_TOPIC_TYPE = PREFIX
			+ "constrained-topic-type";
	public static String CONTAINEE = PREFIX + "containee";
	public static String CONTAINER = PREFIX + "container";
	public static String DATATYPE = PREFIX + "datatype";
	public static String DESCRIPTION = PREFIX + "description";
	public static String INCLUDE_SCHEMA = PREFIX + "includes-schema";
	public static String OTHER_CONSTRAINED_ROLE = PREFIX
			+ "other-constrained-role";
	public static String OTHER_CONSTRAINED_TOPIC_TYPE = PREFIX
			+ "other-constrained-topic-type";
	public static String OVERLAPS = PREFIX + "overlaps";
	public static String REGEXP = PREFIX + "regexp";
	public static String REIFIER_CONSTRAINT = PREFIX + "reifier-constraint";
	public static String REQUIREMENT_CONSTRAINT = PREFIX
			+ "requirement-constraint";
	public static String SCHEMA = PREFIX + "schema";
	public static String SCHEMA_RESOURCE = PREFIX + "schema-resource";
	public static String SCOPE_CONSTRAINT = PREFIX + "scope-constraint";
	public static String SEE_ALSO = PREFIX + "see-also";
	public static String UNIQUE_VALUE_CONSTRAINT = PREFIX
			+ "unique-value-constraint";
	public static String USED = PREFIX + "used";
	public static String USER = PREFIX + "user";
	public static String USER_DEFINED_CONSTRAINT = PREFIX
			+ "user-defined-constraint";
	public static String USES_SCHEMA = PREFIX + "uses-schema";
	public static String VALIDATION_EXPRESSION = PREFIX
			+ "validation-expression";
	public static String VARIANT_NAME_CONSTRAINT = PREFIX
			+ "variant-name-constraint";
	public static String VERSION = PREFIX + "version";

	private List<Template> templates;
	private TopicMap topicMap;
	private final TemplateFactory templateFactory;

	private EntryFactory entryFactory;
	// -- topic types
	private Topic topicType;
	private Topic associationType;
	private Topic roleType;
	private Topic occurrenceType;
	private Topic nameType;
	private Topic scopeType;

	private Topic topicMapSchema;

	// -- associations
	private Topic constraintStatement;
	private Topic constrainedTopicType;
	private Topic constrainedRole;
	private Topic constrainedScope;
	private Topic allowedReifier;
	private Topic otherConstrainedRole;
	private Topic otherConstrainedTopicType;
	private Topic overlaps;
	private Topic belongsTo;

	// -- roles
	private Topic constraint;
	private Topic constrained;
	private Topic allows;
	private Topic allowed;
	private Topic container;
	private Topic containee;

	// -- constrains
	// private Topic constraint; // -- not needed is super type
	private Topic abstractConstraint;
	private Topic subjectIdentifierConstraint;
	private Topic subjectLocatorConstraint;
	private Topic topicNameConstraint;
	private Topic topicOccurrenceConstraint;
	private Topic topicRoleConstraint;
	private Topic scopeConstraint;
	private Topic reifierConstraint;
	private Topic topicReifiesConstraint;
	private Topic associationRoleConstraint;
	private Topic roleCombinationConstraint;
	private Topic occurrenceDatatypeConstraint;
	private Topic uniqueValueConstraint;
	private Topic regularExpressionConstraint;
	private Topic overlapDeclaration;

	// -- occurrence types
	private Topic datatype;
	private Topic cardMin;
	private Topic cardMax;
	private Topic regExp;
	private Topic comment;
	private Topic seeAlso;
	private Topic description;

	// -- tmdm
	private Topic topicName;
	private Topic subject;

	public TMCLTemplateDefinitions(CTMTopicMapWriter writer, TopicMap topicMap) {
		this.topicMap = topicMap;
		this.templateFactory = new TemplateFactory(writer);
		this.entryFactory = this.templateFactory.getEntryFactory();
	}

	public List<Template> getTemplates() {
		if (templates == null)
			try {
				init();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		return templates;
	}

	private void init() throws Exception {
		initTypes();

		templates = new ArrayList<Template>();

		// addOverlaps();
		// addHasSubjectIdentifier();
		// addHasSubjectLocator();
		addHasName();
		// addHasOccurrence();
		// addPlaysRole();
		// addHasScope();
		// addMustHaveReifier();
		// addCannotHaveReifier();
		// addMayHaveReifier();
		// addMustReify();
		// addCannotReify();
		// addMayReify();
		// addHasRole();
		// addRoleCombination();
		// addHasDatatype();
		// addIsUnique();
		// addMatchesRegExp();
		// addBelongsTo();
	}

	private void initTypes() {

		// removing TMDM types from cache
		createTopic(TYPE);
		createTopic(INSTANCE);
		createTopic(TYPE_INSTANCE);
		createTopic(SUPERTYPE);
		createTopic(SUBTYPE);
		createTopic(SUPERTYPE_SUBTYPE);
		// removing TMCL roles from cache
		createTopic(CONTAINEE);
		createTopic(CONTAINER);

		// initialize tmdm topics
		topicName = createTopic(TOPIC_NAME);
		subject = createTopic(SUBJECT + "subject");

		topicMapSchema = createTopic(SCHEMA);
		belongsTo = createTopic(BELONGS_TO);

		// init topic types
		topicType = createTopic(TOPIC_TYPE);
		nameType = createTopic(NAME_TYPE);
		associationType = createTopic(ASSOCIATION_TYPE);
		roleType = createTopic(ROLE_TYPE);
		occurrenceType = createTopic(OCCURRENCE_TYPE);

		cardMin = createTopic(CARD_MIN);
		cardMax = createTopic(CARD_MAX);
		regExp = createTopic(REGEXP);
		datatype = createTopic(DATATYPE);
		comment = createTopic(COMMENT);
		description = createTopic(DESCRIPTION);
		seeAlso = createTopic(SEE_ALSO);

		// roles
		constraint = createTopic(CONSTRAINT);
		constrained = createTopic(CONSTRAINED);
		allowed = createTopic(ALLOWED);
		allows = createTopic(ALLOWS);
		container = createTopic(CONTAINER);
		containee = createTopic(CONTAINEE);

		// associations
		constraintStatement = createTopic(CONSTRAINED_STATEMENT);
		constrainedTopicType = createTopic(CONSTRAINED_TOPIC_TYPE);
		constrainedRole = createTopic(CONSTRAINED_ROLE);
		constrainedScope = createTopic(CONSTRAINED_SCOPE);
		otherConstrainedTopicType = createTopic(OTHER_CONSTRAINED_TOPIC_TYPE);
		otherConstrainedRole = createTopic(OTHER_CONSTRAINED_ROLE);
		allowedReifier = createTopic(ALLOWED_REIFIER);
		constrainedRole = createTopic(CONSTRAINED_ROLE);
		otherConstrainedRole = createTopic(OTHER_CONSTRAINED_ROLE);
		otherConstrainedTopicType = createTopic(OTHER_CONSTRAINED_TOPIC_TYPE);
		overlaps = createTopic(OVERLAP_DECLARATION);

		// constraint = createTopic(CONSTRAINT);
		abstractConstraint = createTopic(ABSTRACT_CONSTRAINT);
		subjectIdentifierConstraint = createTopic(SUBJECT_IDENTIFIER_CONSTRAINT);
		subjectLocatorConstraint = createTopic(SUBJECT_LOCATOR_CONSTRAINT);
		topicNameConstraint = createTopic(TOPIC_NAME_CONSTRAINT);
		topicOccurrenceConstraint = createTopic(TOPIC_OCCURRENCE_CONSTRAINT);
		topicRoleConstraint = createTopic(TOPIC_ROLE_CONSTRAINT);
		scopeConstraint = createTopic(SCOPE_CONSTRAINT);
		reifierConstraint = createTopic(REIFIER_CONSTRAINT);
		topicReifiesConstraint = createTopic(TOPIC_REIFIES_CONSTRAINT);
		associationRoleConstraint = createTopic(ASSOCIATION_ROLE_CONSTRAINT);
		roleCombinationConstraint = createTopic(ROLE_COMBINATION_CONSTRAINT);
		occurrenceDatatypeConstraint = createTopic(OCCURRENCE_DATATYPE_CONSTRAINT);
		uniqueValueConstraint = createTopic(UNIQUE_VALUE_CONSTRAINT);
		regularExpressionConstraint = createTopic(REGULAR_EXPRESSION_CONSTRAINT);
		overlapDeclaration = createTopic(OVERLAP_DECLARATION);
	}

	private Topic createTopic(String si) {
		return topicMap.createTopicBySubjectIdentifier(topicMap
				.createLocator(si));
	}

	/*
	 * def overlaps($tt1, $tt2) ?c isa tmcl:overlap-declaration.
	 * tmcl:overlaps(tmcl:allows : ?c, tmcl:allowed : $tt1)
	 * tmcl:overlaps(tmcl:allows : ?c, tmcl:allowed : $tt2) end
	 */
	private void addOverlaps() throws Exception {
		Template t = templateFactory.newTemplate("overlaps");

		IEntryParam constrParam = entryFactory.newWildcardParam("c");
		TopicEntry t1 = entryFactory.newTopicEntry(constrParam);

		t1.add(entryFactory.newIsInstanceOfEntry(entryFactory
				.newTopicTypeParam(overlapDeclaration)));
		t.add(t1);

		RoleEntry r1 = entryFactory.newRoleEntry(allows, constrParam);
		RoleEntry r2 = entryFactory.newRoleEntry(allowed, entryFactory
				.newVariableParam("tt1"));
		AssociationEntry a1 = entryFactory
				.newAssociationEntry(overlaps, r1, r2);
		t.add(a1);

		r1 = entryFactory.newRoleEntry(allows, constrParam);
		r2 = entryFactory.newRoleEntry(allowed, entryFactory
				.newVariableParam("tt2"));
		AssociationEntry a2 = entryFactory
				.newAssociationEntry(overlaps, r1, r2);
		t.add(a2);

		templates.add(t);

	}

	/*
	 * def has-subject-identifier($tt, $min, $max, $regexp) ?c isa
	 * tmcl:subject-identifier-constraint; tmcl:card-min: $min; tmcl:card-max:
	 * $max; tmcl:regexp: $regexp. tmcl:constrained-topic-type(tmcl:constraint :
	 * ?c, tmcl:constrained : $tt) end
	 */
	private void addHasSubjectIdentifier() {
		Template t = templateFactory.newTemplate("has-subject-identifier");

		IEntryParam constrParam = entryFactory.newWildcardParam("c");
		TopicEntry t1 = entryFactory.newTopicEntry(constrParam);

		t1.add(entryFactory.newIsInstanceOfEntry(entryFactory
				.newTopicTypeParam(subjectIdentifierConstraint)));

		OccurrenceEntry o1 = entryFactory.newOccurrenceEntry(entryFactory
				.newVariableParam("min"), entryFactory
				.newTopicTypeParam(cardMin));
		t1.add(o1);

		OccurrenceEntry o2 = entryFactory.newOccurrenceEntry(entryFactory
				.newVariableParam("max"), entryFactory
				.newTopicTypeParam(cardMax));
		t1.add(o2);

		OccurrenceEntry o3 = entryFactory.newOccurrenceEntry(entryFactory
				.newVariableParam("regexp"), entryFactory
				.newTopicTypeParam(regExp));
		t1.add(o3);

		t.add(t1);

		RoleEntry r1 = entryFactory.newRoleEntry(constraint, constrParam);
		RoleEntry r2 = entryFactory.newRoleEntry(constrained, entryFactory
				.newVariableParam("tt"));
		AssociationEntry a1 = entryFactory.newAssociationEntry(
				constrainedTopicType, r1, r2);
		t.add(a1);

		templates.add(t);
	}

	/*
	 * def has-subject-locator($tt, $min, $max, $regexp) ?c isa
	 * tmcl:subject-locator-constraint; tmcl:card-min: $min; tmcl:card-max:
	 * $max; tmcl:regexp: $regexp. tmcl:constrained-topic-type(tmcl:constraint :
	 * ?c, tmcl:constrained : $tt) end
	 */
	private void addHasSubjectLocator() {
		Template t = templateFactory.newTemplate("has-subject-locator");

		IEntryParam constrParam = entryFactory.newWildcardParam("c");
		TopicEntry t1 = entryFactory.newTopicEntry(constrParam);

		t1.add(entryFactory.newIsInstanceOfEntry(entryFactory
				.newTopicTypeParam(subjectLocatorConstraint)));

		OccurrenceEntry o1 = entryFactory.newOccurrenceEntry(entryFactory
				.newVariableParam("min"), entryFactory
				.newTopicTypeParam(cardMin));
		t1.add(o1);

		OccurrenceEntry o2 = entryFactory.newOccurrenceEntry(entryFactory
				.newVariableParam("max"), entryFactory
				.newTopicTypeParam(cardMax));
		t1.add(o2);

		OccurrenceEntry o3 = entryFactory.newOccurrenceEntry(entryFactory
				.newVariableParam("regexp"), entryFactory
				.newTopicTypeParam(regExp));
		t1.add(o3);

		t.add(t1);

		RoleEntry r1 = entryFactory.newRoleEntry(constraint, constrParam);
		RoleEntry r2 = entryFactory.newRoleEntry(constrained, entryFactory
				.newVariableParam("tt"));
		AssociationEntry a1 = entryFactory.newAssociationEntry(
				constrainedTopicType, r1, r2);
		t.add(a1);

		templates.add(t);

	}

	/*
	 * def has-name($tt, $nt, $min, $max) ?c isa tmcl:topic-name-constraint;
	 * tmcl:card-min: $min; tmcl:card-max: $max.
	 * 
	 * tmcl:constrained-topic-type(tmcl:constraint : ?c, tmcl:constrained : $tt)
	 * tmcl:constrained-statement(tmcl:constraint : ?c, tmcl:constrained : $nt)
	 * end
	 */
	private void addHasName() {
		Template t = templateFactory.newTemplate("has-name");
		IEntryParam constrParam = entryFactory.newWildcardParam("c");

		RoleEntry r1 = entryFactory.newRoleEntry(constraint, constrParam);
		RoleEntry r2 = entryFactory.newRoleEntry(constrained, entryFactory
				.newVariableParam("tt"));
		AssociationEntry a1 = entryFactory.newAssociationEntry(
				constrainedTopicType, r1, r2);
		t.add(a1);

		r1 = entryFactory.newRoleEntry(constraint, constrParam);
		r2 = entryFactory.newRoleEntry(constrained, entryFactory
				.newVariableParam("nt"));
		AssociationEntry a2 = entryFactory.newAssociationEntry(
				constraintStatement, r1, r2);
		t.add(a2);

		TopicEntry t1 = entryFactory.newTopicEntry(constrParam);

		t1.add(entryFactory.newIsInstanceOfEntry(entryFactory
				.newTopicTypeParam(subjectLocatorConstraint)));

		OccurrenceEntry o1 = entryFactory.newOccurrenceEntry(entryFactory
				.newVariableParam("min"), entryFactory
				.newTopicTypeParam(cardMin));
		t1.add(o1);

		OccurrenceEntry o2 = entryFactory.newOccurrenceEntry(entryFactory
				.newVariableParam("max"), entryFactory
				.newTopicTypeParam(cardMax));
		t1.add(o2);

		t.add(t1);

//		t.setScanner(new HasNameTemplateScanner());

		templates.add(t);

	}

	/*
	 * # Topic Occurrence Constraint def has-occurrence($tt, $ot, $min, $max) ?c
	 * isa tmcl:topic-occurrence-constraint; tmcl:card-min: $min; tmcl:card-max:
	 * $max.
	 * 
	 * tmcl:constrained-topic-type(tmcl:constraint : ?c, tmcl:constrained : $tt)
	 * tmcl:constrained-statement(tmcl:constraint : ?c, tmcl:constrained : $ot)
	 * end
	 */
	private void addHasOccurrence() {
		Template t = templateFactory.newTemplate("has-occurence");

		IEntryParam constrParam = entryFactory.newWildcardParam("c");
		TopicEntry t1 = entryFactory.newTopicEntry(constrParam);

		t1.add(entryFactory.newIsInstanceOfEntry(entryFactory
				.newTopicTypeParam(topicOccurrenceConstraint)));

		OccurrenceEntry o1 = entryFactory.newOccurrenceEntry(entryFactory
				.newVariableParam("min"), entryFactory
				.newTopicTypeParam(cardMin));
		t1.add(o1);

		OccurrenceEntry o2 = entryFactory.newOccurrenceEntry(entryFactory
				.newVariableParam("max"), entryFactory
				.newTopicTypeParam(cardMax));
		t1.add(o2);

		t.add(t1);

		RoleEntry r1 = entryFactory.newRoleEntry(constraint, constrParam);
		RoleEntry r2 = entryFactory.newRoleEntry(constrained, entryFactory
				.newVariableParam("tt"));
		AssociationEntry a1 = entryFactory.newAssociationEntry(
				constrainedTopicType, r1, r2);
		t.add(a1);

		r1 = entryFactory.newRoleEntry(constraint, constrParam);
		r2 = entryFactory.newRoleEntry(constrained, entryFactory
				.newVariableParam("ot"));
		AssociationEntry a2 = entryFactory.newAssociationEntry(
				constraintStatement, r1, r2);
		t.add(a2);

		templates.add(t);

	}

	/*
	 * # Topic Role Constraint def plays-role($tt, $rt, $at, $min, $max) ?c isa
	 * tmcl:topic-role-constraint; tmcl:card-min: $min; tmcl:card-max: $max.
	 * 
	 * tmcl:constrained-topic-type(tmcl:constraint : ?c, tmcl:constrained : $tt)
	 * tmcl:constrained-statement(tmcl:constraint : ?c, tmcl:constrained : $at)
	 * tmcl:constrained-role(tmcl:constraint : ?c, tmcl:constrained : $rt) end
	 */
	private void addPlaysRole() {
		Template t = templateFactory.newTemplate("plays-role");

		IEntryParam constrParam = entryFactory.newWildcardParam("c");
		TopicEntry t1 = entryFactory.newTopicEntry(constrParam);

		t1.add(entryFactory.newIsInstanceOfEntry(entryFactory
				.newTopicTypeParam(topicRoleConstraint)));

		OccurrenceEntry o1 = entryFactory.newOccurrenceEntry(entryFactory
				.newVariableParam("min"), entryFactory
				.newTopicTypeParam(cardMin));
		t1.add(o1);

		OccurrenceEntry o2 = entryFactory.newOccurrenceEntry(entryFactory
				.newVariableParam("max"), entryFactory
				.newTopicTypeParam(cardMax));
		t1.add(o2);

		t.add(t1);

		RoleEntry r1 = entryFactory.newRoleEntry(constraint, constrParam);
		RoleEntry r2 = entryFactory.newRoleEntry(constrained, entryFactory
				.newVariableParam("tt"));
		AssociationEntry a1 = entryFactory.newAssociationEntry(
				constrainedTopicType, r1, r2);
		t.add(a1);

		r1 = entryFactory.newRoleEntry(constraint, constrParam);
		r2 = entryFactory.newRoleEntry(constrained, entryFactory
				.newVariableParam("at"));
		AssociationEntry a2 = entryFactory.newAssociationEntry(
				constraintStatement, r1, r2);
		t.add(a2);

		r1 = entryFactory.newRoleEntry(constraint, constrParam);
		r2 = entryFactory.newRoleEntry(constrained, entryFactory
				.newVariableParam("rt"));
		AssociationEntry a3 = entryFactory.newAssociationEntry(constrainedRole,
				r1, r2);
		t.add(a3);

		templates.add(t);

	}

	/*
	 * # Scope Constraint def has-scope($st, $tt, $min, $max) ?c isa
	 * tmcl:scope-constraint; tmcl:card-min: $min; tmcl:card-max: $max.
	 * 
	 * tmcl:constrained-statement(tmcl:constraint : ?c, tmcl:constrained : $st)
	 * tmcl:constrained-scope(tmcl:constraint : ?c, tmcl:constrained : $tt) end
	 */
	private void addHasScope() {
		Template t = templateFactory.newTemplate("has-scope");

		IEntryParam constrParam = entryFactory.newWildcardParam("c");
		TopicEntry t1 = entryFactory.newTopicEntry(constrParam);

		t1.add(entryFactory.newIsInstanceOfEntry(entryFactory
				.newTopicTypeParam(scopeConstraint)));

		OccurrenceEntry o1 = entryFactory.newOccurrenceEntry(entryFactory
				.newVariableParam("min"), entryFactory
				.newTopicTypeParam(cardMin));
		t1.add(o1);

		OccurrenceEntry o2 = entryFactory.newOccurrenceEntry(entryFactory
				.newVariableParam("max"), entryFactory
				.newTopicTypeParam(cardMax));
		t1.add(o2);

		t.add(t1);

		RoleEntry r1 = entryFactory.newRoleEntry(constraint, constrParam);
		RoleEntry r2 = entryFactory.newRoleEntry(constrained, entryFactory
				.newVariableParam("tt"));
		AssociationEntry a1 = entryFactory.newAssociationEntry(
				constraintStatement, r1, r2);
		t.add(a1);

		r1 = entryFactory.newRoleEntry(constraint, constrParam);
		r2 = entryFactory.newRoleEntry(constrained, entryFactory
				.newVariableParam("at"));
		AssociationEntry a2 = entryFactory.newAssociationEntry(
				constrainedScope, r1, r2);
		t.add(a2);

		templates.add(t);

	}

	/*
	 * # Reifier Constraint def must-have-reifier($st, $tt) ?c isa
	 * tmcl:reifier-constraint; tmcl:card-min: 1; tmcl:card-max: 1.
	 * tmcl:constrained-statement(tmcl:constraint: ?c, tmcl:constrained: $st)
	 * tmcl:allowed-reifier(tmcl:allows: ?c, tmcl:allowed: $tt) end
	 */
	private void addMustHaveReifier() {
		Template t = templateFactory.newTemplate("must-have-reifier");

		IEntryParam constrParam = entryFactory.newWildcardParam("c");
		TopicEntry t1 = entryFactory.newTopicEntry(constrParam);

		t1.add(entryFactory.newIsInstanceOfEntry(entryFactory
				.newTopicTypeParam(reifierConstraint)));

		OccurrenceEntry o1 = entryFactory.newOccurrenceEntry(entryFactory
				.newValueParam("1"), entryFactory.newTopicTypeParam(cardMin));
		t1.add(o1);

		OccurrenceEntry o2 = entryFactory.newOccurrenceEntry(entryFactory
				.newValueParam("1"), entryFactory.newTopicTypeParam(cardMax));
		t1.add(o2);

		t.add(t1);

		RoleEntry r1 = entryFactory.newRoleEntry(constraint, constrParam);
		RoleEntry r2 = entryFactory.newRoleEntry(constrained, entryFactory
				.newVariableParam("st"));
		AssociationEntry a1 = entryFactory.newAssociationEntry(
				constraintStatement, r1, r2);
		t.add(a1);

		r1 = entryFactory.newRoleEntry(allows, constrParam);
		r2 = entryFactory.newRoleEntry(allowed, entryFactory
				.newVariableParam("tt"));
		AssociationEntry a2 = entryFactory.newAssociationEntry(allowedReifier,
				r1, r2);
		t.add(a2);

		templates.add(t);

	}

	/*
	 * def cannot-have-reifier($st) ?c isa tmcl:reifier-constraint;
	 * tmcl:card-min: 0; tmcl:card-max: 0.
	 * tmcl:constrained-statement(tmcl:constraint: ?c, tmcl:constrained: $st)
	 * tmcl:allowed-reifier(tmcl:allows: ?c, tmcl:allowed: tmdm:subject) end
	 */
	private void addCannotHaveReifier() {
		Template t = templateFactory.newTemplate("cannot-have-reifier");

		IEntryParam constrParam = entryFactory.newWildcardParam("c");
		TopicEntry t1 = entryFactory.newTopicEntry(constrParam);

		t1.add(entryFactory.newIsInstanceOfEntry(entryFactory
				.newTopicTypeParam(reifierConstraint)));

		OccurrenceEntry o1 = entryFactory.newOccurrenceEntry(entryFactory
				.newValueParam("0"), entryFactory.newTopicTypeParam(cardMin));
		t1.add(o1);

		OccurrenceEntry o2 = entryFactory.newOccurrenceEntry(entryFactory
				.newValueParam("0"), entryFactory.newTopicTypeParam(cardMax));
		t1.add(o2);

		t.add(t1);

		RoleEntry r1 = entryFactory.newRoleEntry(constraint, constrParam);
		RoleEntry r2 = entryFactory.newRoleEntry(constrained, entryFactory
				.newVariableParam("st"));
		AssociationEntry a1 = entryFactory.newAssociationEntry(
				constraintStatement, r1, r2);
		t.add(a1);

		r1 = entryFactory.newRoleEntry(allows, constrParam);
		r2 = entryFactory.newRoleEntry(allowed, entryFactory
				.newTopicTypeParam(subject));
		AssociationEntry a2 = entryFactory.newAssociationEntry(allowedReifier,
				r1, r2);
		t.add(a2);

		templates.add(t);

	}

	/*
	 * def may-have-reifier($st, $tt) ?c isa tmcl:reifier-constraint;
	 * tmcl:card-min: 0; tmcl:card-max: 1.
	 * tmcl:constrained-statement(tmcl:constraint: ?c, tmcl:constrained: $st)
	 * tmcl:allowed-reifier(tmcl:allows: ?c, tmcl:allowed: $tt) end
	 */
	private void addMayHaveReifier() {
		Template t = templateFactory.newTemplate("may-have-reifier");

		IEntryParam constrParam = entryFactory.newWildcardParam("c");
		TopicEntry t1 = entryFactory.newTopicEntry(constrParam);

		t1.add(entryFactory.newIsInstanceOfEntry(entryFactory
				.newTopicTypeParam(reifierConstraint)));

		OccurrenceEntry o1 = entryFactory.newOccurrenceEntry(entryFactory
				.newValueParam("0"), entryFactory.newTopicTypeParam(cardMin));
		t1.add(o1);

		OccurrenceEntry o2 = entryFactory.newOccurrenceEntry(entryFactory
				.newValueParam("1"), entryFactory.newTopicTypeParam(cardMax));
		t1.add(o2);

		t.add(t1);

		RoleEntry r1 = entryFactory.newRoleEntry(constraint, constrParam);
		RoleEntry r2 = entryFactory.newRoleEntry(constrained, entryFactory
				.newVariableParam("st"));
		AssociationEntry a1 = entryFactory.newAssociationEntry(
				constraintStatement, r1, r2);
		t.add(a1);

		r1 = entryFactory.newRoleEntry(allows, constrParam);
		r2 = entryFactory.newRoleEntry(allowed, entryFactory
				.newVariableParam("tt"));
		AssociationEntry a2 = entryFactory.newAssociationEntry(allowedReifier,
				r1, r2);
		t.add(a2);

		templates.add(t);

	}

	/*
	 * # Topic Reifies Constraint def must-reify($tt, $st) ?c isa
	 * tmcl:topic-reifies-constraint; tmcl:card-min: 1; tmcl:card-max: 1.
	 * tmcl:constrained-topic-type(tmcl:constraint: ?c, tmcl:constrained: $tt)
	 * tmcl:constrained-statement(tmcl:constraint: ?c, tmcl:constrained: $st)
	 * end
	 */
	private void addMustReify() {
		Template t = templateFactory.newTemplate("must-reify");

		IEntryParam constrParam = entryFactory.newWildcardParam("c");
		TopicEntry t1 = entryFactory.newTopicEntry(constrParam);

		t1.add(entryFactory.newIsInstanceOfEntry(entryFactory
				.newTopicTypeParam(topicReifiesConstraint)));

		OccurrenceEntry o1 = entryFactory.newOccurrenceEntry(entryFactory
				.newValueParam("1"), entryFactory.newTopicTypeParam(cardMin));
		t1.add(o1);

		OccurrenceEntry o2 = entryFactory.newOccurrenceEntry(entryFactory
				.newValueParam("1"), entryFactory.newTopicTypeParam(cardMax));
		t1.add(o2);

		t.add(t1);

		RoleEntry r1 = entryFactory.newRoleEntry(constraint, constrParam);
		RoleEntry r2 = entryFactory.newRoleEntry(constrained, entryFactory
				.newVariableParam("tt"));
		AssociationEntry a1 = entryFactory.newAssociationEntry(
				constrainedTopicType, r1, r2);
		t.add(a1);

		r1 = entryFactory.newRoleEntry(constraint, constrParam);
		r2 = entryFactory.newRoleEntry(constrained, entryFactory
				.newVariableParam("st"));
		AssociationEntry a2 = entryFactory.newAssociationEntry(
				constraintStatement, r1, r2);
		t.add(a2);

		templates.add(t);

	}

	/*
	 * def cannot-reify($tt) ?c isa tmcl:topic-reifies-constraint;
	 * tmcl:card-min: 0; tmcl:card-max: 0.
	 * tmcl:constrained-topic-type(tmcl:constraint: ?c, tmcl:constrained: $tt)
	 * end
	 */
	private void addCannotReify() {
		Template t = templateFactory.newTemplate("cannot-reify");

		IEntryParam constrParam = entryFactory.newWildcardParam("c");
		TopicEntry t1 = entryFactory.newTopicEntry(constrParam);

		t1.add(entryFactory.newIsInstanceOfEntry(entryFactory
				.newTopicTypeParam(topicReifiesConstraint)));

		OccurrenceEntry o1 = entryFactory.newOccurrenceEntry(entryFactory
				.newValueParam("0"), entryFactory.newTopicTypeParam(cardMin));
		t1.add(o1);

		OccurrenceEntry o2 = entryFactory.newOccurrenceEntry(entryFactory
				.newValueParam("0"), entryFactory.newTopicTypeParam(cardMax));
		t1.add(o2);

		t.add(t1);

		RoleEntry r1 = entryFactory.newRoleEntry(constraint, constrParam);
		RoleEntry r2 = entryFactory.newRoleEntry(constrained, entryFactory
				.newVariableParam("tt"));
		AssociationEntry a1 = entryFactory.newAssociationEntry(
				constrainedTopicType, r1, r2);
		t.add(a1);

		templates.add(t);

	}

	/*
	 * def may-reify($tt, $st) ?c isa tmcl:topic-reifies-constraint;
	 * tmcl:card-min: 0; tmcl:card-max: 1.
	 * tmcl:constrained-topic-type(tmcl:constraint: ?c, tmcl:constrained: $tt)
	 * tmcl:constrained-statement(tmcl:constraint: ?c, tmcl:constrained: $st)
	 * end
	 */
	private void addMayReify() {
		Template t = templateFactory.newTemplate("must-reify");

		IEntryParam constrParam = entryFactory.newWildcardParam("c");
		TopicEntry t1 = entryFactory.newTopicEntry(constrParam);

		t1.add(entryFactory.newIsInstanceOfEntry(entryFactory
				.newTopicTypeParam(topicReifiesConstraint)));

		OccurrenceEntry o1 = entryFactory.newOccurrenceEntry(entryFactory
				.newValueParam("0"), entryFactory.newTopicTypeParam(cardMin));
		t1.add(o1);

		OccurrenceEntry o2 = entryFactory.newOccurrenceEntry(entryFactory
				.newValueParam("1"), entryFactory.newTopicTypeParam(cardMax));
		t1.add(o2);

		t.add(t1);

		RoleEntry r1 = entryFactory.newRoleEntry(constraint, constrParam);
		RoleEntry r2 = entryFactory.newRoleEntry(constrained, entryFactory
				.newVariableParam("tt"));
		AssociationEntry a1 = entryFactory.newAssociationEntry(
				constrainedTopicType, r1, r2);
		t.add(a1);

		r1 = entryFactory.newRoleEntry(constraint, constrParam);
		r2 = entryFactory.newRoleEntry(constrained, entryFactory
				.newVariableParam("st"));
		AssociationEntry a2 = entryFactory.newAssociationEntry(
				constraintStatement, r1, r2);
		t.add(a2);

		templates.add(t);

	}

	/*
	 * # Association Role Constraint def has-role($at, $rt, $min, $max) ?c isa
	 * tmcl:association-role-constraint; tmcl:card-min: $min; tmcl:card-max:
	 * $max.
	 * 
	 * tmcl:constrained-statement(tmcl:constraint : ?c, tmcl:constrained : $at)
	 * tmcl:constrained-role(tmcl:constraint : ?c, tmcl:constrained : $rt) end
	 */
	private void addHasRole() {
		Template t = templateFactory.newTemplate("has-role");

		IEntryParam constrParam = entryFactory.newWildcardParam("c");
		TopicEntry t1 = entryFactory.newTopicEntry(constrParam);

		t1.add(entryFactory.newIsInstanceOfEntry(entryFactory
				.newTopicTypeParam(associationRoleConstraint)));

		OccurrenceEntry o1 = entryFactory.newOccurrenceEntry(entryFactory
				.newVariableParam("min"), entryFactory
				.newTopicTypeParam(cardMin));
		t1.add(o1);

		OccurrenceEntry o2 = entryFactory.newOccurrenceEntry(entryFactory
				.newVariableParam("max"), entryFactory
				.newTopicTypeParam(cardMax));
		t1.add(o2);

		t.add(t1);

		RoleEntry r1 = entryFactory.newRoleEntry(constraint, constrParam);
		RoleEntry r2 = entryFactory.newRoleEntry(constrained, entryFactory
				.newVariableParam("at"));
		AssociationEntry a1 = entryFactory.newAssociationEntry(
				constraintStatement, r1, r2);
		t.add(a1);

		r1 = entryFactory.newRoleEntry(constraint, constrParam);
		r2 = entryFactory.newRoleEntry(constrained, entryFactory
				.newVariableParam("rt"));
		AssociationEntry a2 = entryFactory.newAssociationEntry(constrainedRole,
				r1, r2);
		t.add(a2);

		templates.add(t);

	}

	/*
	 * # Role Combination Constraint def role-combination($at, $rt, $tt, $ort,
	 * $ott) ?c isa tmcl:role-combination-constraint.
	 * tmcl:constrained-statement(tmcl:constraint: ?c, tmcl:constrained: $at)
	 * tmcl:constrained-role(tmcl:constraint: ?c, tmcl:constrained: $rt)
	 * tmcl:constrained-topic-type(tmcl:constraint: ?c, tmcl:constrained: $tt)
	 * tmcl:other-constrained-role(tmcl:constraint: ?c, tmcl:constrained: $ort)
	 * tmcl:other-constrained-topic-type(tmcl:constraint: ?c, tmcl:constrained:
	 * $ott) end
	 */
	private void addRoleCombination() {
		Template t = templateFactory.newTemplate("role-combination");

		IEntryParam constrParam = entryFactory.newWildcardParam("c");
		TopicEntry t1 = entryFactory.newTopicEntry(constrParam);

		t1.add(entryFactory.newIsInstanceOfEntry(entryFactory
				.newTopicTypeParam(roleCombinationConstraint)));
		t.add(t1);

		RoleEntry r1 = entryFactory.newRoleEntry(constraint, constrParam);
		RoleEntry r2 = entryFactory.newRoleEntry(constrained, entryFactory
				.newVariableParam("at"));
		AssociationEntry a1 = entryFactory.newAssociationEntry(
				constraintStatement, r1, r2);
		t.add(a1);

		r1 = entryFactory.newRoleEntry(constraint, constrParam);
		r2 = entryFactory.newRoleEntry(constrained, entryFactory
				.newVariableParam("rt"));
		AssociationEntry a2 = entryFactory.newAssociationEntry(constrainedRole,
				r1, r2);
		t.add(a2);

		r1 = entryFactory.newRoleEntry(constraint, constrParam);
		r2 = entryFactory.newRoleEntry(constrained, entryFactory
				.newVariableParam("tt"));
		AssociationEntry a3 = entryFactory.newAssociationEntry(
				constrainedTopicType, r1, r2);
		t.add(a3);

		r1 = entryFactory.newRoleEntry(constraint, constrParam);
		r2 = entryFactory.newRoleEntry(constrained, entryFactory
				.newVariableParam("ort"));
		AssociationEntry a4 = entryFactory.newAssociationEntry(
				otherConstrainedRole, r1, r2);
		t.add(a4);

		r1 = entryFactory.newRoleEntry(constraint, constrParam);
		r2 = entryFactory.newRoleEntry(constrained, entryFactory
				.newVariableParam("ott"));
		AssociationEntry a5 = entryFactory.newAssociationEntry(
				otherConstrainedTopicType, r1, r2);
		t.add(a5);

		templates.add(t);

	}

	/*
	 * # Occurrence Data Type Constraint def has-datatype($ot, $dt) ?c isa
	 * tmcl:occurrence-datatype-constraint; tmcl:datatype: $dt.
	 * tmcl:constrained-statement(tmcl:constraint : ?c, tmcl:constrained : $ot)
	 * end
	 */
	private void addHasDatatype() {
		Template t = templateFactory.newTemplate("has-datatype");

		IEntryParam constrParam = entryFactory.newWildcardParam("c");
		TopicEntry t1 = entryFactory.newTopicEntry(constrParam);

		t1.add(entryFactory.newIsInstanceOfEntry(entryFactory
				.newTopicTypeParam(occurrenceDatatypeConstraint)));

		OccurrenceEntry o1 = entryFactory.newOccurrenceEntry(entryFactory
				.newVariableParam("dt"), entryFactory
				.newTopicTypeParam(datatype));
		t1.add(o1);

		t.add(t1);

		RoleEntry r1 = entryFactory.newRoleEntry(constraint, constrParam);
		RoleEntry r2 = entryFactory.newRoleEntry(constrained, entryFactory
				.newVariableParam("ot"));
		AssociationEntry a1 = entryFactory.newAssociationEntry(
				constraintStatement, r1, r2);
		t.add(a1);

		templates.add(t);

	}

	/*
	 * # Unique Value Constraint def has-unique-value($st) ?c isa
	 * tmcl:unique-value-constraint. tmcl:constrained-statement(tmcl:constraint
	 * : ?c, tmcl:constrained : $st) end
	 */
	private void addIsUnique() {
		Template t = templateFactory.newTemplate("has-unique-value");

		IEntryParam constrParam = entryFactory.newWildcardParam("c");
		TopicEntry t1 = entryFactory.newTopicEntry(constrParam);

		t1.add(entryFactory.newIsInstanceOfEntry(entryFactory
				.newTopicTypeParam(uniqueValueConstraint)));
		t.add(t1);

		RoleEntry r1 = entryFactory.newRoleEntry(constraint, constrParam);
		RoleEntry r2 = entryFactory.newRoleEntry(constrained, entryFactory
				.newVariableParam("st"));
		AssociationEntry a1 = entryFactory.newAssociationEntry(
				constraintStatement, r1, r2);
		t.add(a1);

		templates.add(t);

	}

	/*
	 * # Regular Expression Constraint def matches-regexp($st, $regexp) ?c isa
	 * tmcl:regular-expression-constraint; tmcl:regexp: $regexp.
	 * 
	 * tmcl:constrained-statement(tmcl:constraint: ?c, tmcl:constrained: $st)
	 * end
	 */
	private void addMatchesRegExp() {
		Template t = templateFactory.newTemplate("matches-regexp");

		IEntryParam constrParam = entryFactory.newWildcardParam("c");
		TopicEntry t1 = entryFactory.newTopicEntry(constrParam);

		t1.add(entryFactory.newIsInstanceOfEntry(entryFactory
				.newTopicTypeParam(regularExpressionConstraint)));

		OccurrenceEntry o1 = entryFactory.newOccurrenceEntry(entryFactory
				.newVariableParam("regexp"), entryFactory
				.newTopicTypeParam(regExp));
		t1.add(o1);

		t.add(t1);

		RoleEntry r1 = entryFactory.newRoleEntry(constraint, constrParam);
		RoleEntry r2 = entryFactory.newRoleEntry(constrained, entryFactory
				.newVariableParam("st"));
		AssociationEntry a1 = entryFactory.newAssociationEntry(
				constraintStatement, r1, r2);
		t.add(a1);

		templates.add(t);

	}

	/*
	 * # The Schema Topic def belongs-to($construct, $schema)
	 * tmcl:belongs-to-schema(tmcl:container: $schema, tmcl:containee:
	 * $construct) end
	 */
	private void addBelongsTo() {
		Template t = templateFactory.newTemplate("belongs-to");

		RoleEntry r1 = entryFactory.newRoleEntry(container, entryFactory
				.newVariableParam("schema"));
		RoleEntry r2 = entryFactory.newRoleEntry(containee, entryFactory
				.newVariableParam("construct"));
		AssociationEntry a1 = entryFactory.newAssociationEntry(belongsTo, r1,
				r2);
		t.add(a1);

		templates.add(t);

	}

}
//
//class HasNameTemplateScanner implements ITemplateScanner {
//
//	public Set<TemplateMatching> getAdaptiveConstructs(TopicMap topicMap) {
//		try {
//			Set<TemplateMatching> matchings = new HashSet<TemplateMatching>();
//
//			ITMQLRuntime runtime = TMQLRuntimeFactory.newFactory().newRuntime(
//					topicMap);
//
//			String query = "FOR $c IN // tmcl:topic-name-constraint "
//					+ "WHERE tmcl:constrained-topic-type(tmcl:constraint : $c, tmcl:constrained : $tt) "
//					+ "AND tmcl:constrained-statement(tmcl:constraint : $c, tmcl:constrained : $nt) "
//					+ "RETURN (  $tt , $nt , $c / tmcl:card-min [0] , $c / tmcl:card-max [0] , $c )";
//
//			IQuery q = runtime.run(query);
//			System.out.println(q.getResults());
//			for (IResult result : q.getResults()) {
//				TemplateMatching matching = new TemplateMatching();
//				matching.setContext((Topic) result.getResults().get(0));
//				matching.addArgument(result.getResults().get(1));
//				matching.addArgument(result.getResults().get(2));
//				matching.addArgument(result.getResults().get(3));
//				matching.addAffectedConstruct((Topic) result.getResults()
//						.get(4));
//				matchings.add(matching);
//			}
//			return matchings;
//
//		} catch (TMQLRuntimeException e) {
//			e.printStackTrace();
//		}
//		return new HashSet<TemplateMatching>();
//	}
//
//}
