

/**
 * Interface containing the subject identifier of TMCL
 * 
 * @author Hannes Niederhausen
 * 
 */
public interface Namespaces {
	
	public static interface TMDM {
		/**
		 * General prefix for the types.
		 */
		public final static String PREFIX = "http://psi.topicmaps.org/iso13250/model/";
		/**
		 * subject-identifier of the name-type of the topic maps data model
		 */
		public static final String TYPE = PREFIX + "type";
		/**
		 * subject-identifier of the name-type of the topic maps data model
		 */
		public static final String INSTANCE = PREFIX + "instance";
		/**
		 * subject-identifier of the name-type of the topic maps data model
		 */
		public static final String SUBTYPE = PREFIX + "subtype";
		/**
		 * subject-identifier of the name-type of the topic maps data model
		 */
		public static final String SUPERTYPE = PREFIX + "supertype";
		/**
		 * subject-identifier of the name-type of the topic maps data model
		 */
		public static final String TYPE_INSTANCE = PREFIX + "type-instance";
		/**
		 * subject-identifier of the name-type of the topic maps data model
		 */
		public static final String SUPERTYPE_SUBTYPE = PREFIX + "supertype-subtype";
		/**
		 * subject-identifier of the default name-type of the topic maps data model
		 */
		public static final String TOPIC_NAME = PREFIX + "topic-name";
		/**
		 * subject-identifier of the tmdm:subject specified in TMCL
		 */
		public static final String SUBJECT = PREFIX + "subject";
		
		
	}
	
	public static interface XSD {
		/**
		 * Base identifier of all XML Scheme Definition data-types <br />
		 * <br />
		 * <code>http://www.w3.org/2001/XMLSchema#</code>
		 */
		public static final String PREFIX = "http://www.w3.org/2001/XMLSchema#";

		/**
		 * QName of all XML Scheme Definition data-types <br />
		 * <br />
		 * <code>xsd</code>
		 */
		public static final String QNAME = "xsd";

		/**
		 * XML Scheme Definition data-types of string <br />
		 * <br />
		 * <code>http://www.w3.org/2001/XMLSchema#string</code>
		 */
		public static final String STRING = PREFIX + "string";

		/**
		 * QNamed XML Scheme Definition data-types of string <br />
		 * <br />
		 * <code>xsd:string</code>
		 */
		public static final String QSTRING = QNAME + ":" + "string";

		/**
		 * XML Scheme Definition data-types of URI <br />
		 * <br />
		 * <code>http://www.w3.org/2001/XMLSchema#anyURI</code>
		 */
		public static final String ANYURI = PREFIX + "anyURI";

		/**
		 * QNamed XML Scheme Definition data-types of URI <br />
		 * <br />
		 * <code>xsd:anyURI</code>
		 */
		public static final String QANYURI = QNAME + ":" + "anyURI";

		/**
		 * XML Scheme Definition data-types of decimal <br />
		 * <br />
		 * <code>http://www.w3.org/2001/XMLSchema#decimal</code>
		 */
		public static final String DECIMAL = PREFIX + "decimal";

		/**
		 * QNamed XML Scheme Definition data-types of decimal <br />
		 * <br />
		 * <code>xsd:decimal</code>
		 */
		public static final String QDECIMAL = QNAME + ":" + "decimal";
		
		/**
		 * XML Scheme Definition data-types of integer <br />
		 * <br />
		 * <code>http://www.w3.org/2001/XMLSchema#integer</code>
		 */
		public static final String INTEGER = PREFIX + "integer";

		/**
		 * QNamed XML Scheme Definition data-types of integer <br />
		 * <br />
		 * <code>xsd:integer</code>
		 */
		public static final String QINTEGER = QNAME + ":" + "integer";

		/**
		 * XML Scheme Definition data-types of long numbers <br />
		 * <br />
		 * <code>http://www.w3.org/2001/XMLSchema#long</code>
		 */
		public static final String LONG = PREFIX + "long";

		/**
		 * QNamed XML Scheme Definition data-types of long numbers <br />
		 * <br />
		 * <code>xsd:long</code>
		 */
		public static final String QLONG = QNAME + ":" + "long";
		
		/**
		 * XML Scheme Definition data-types of floating point numbers <br />
		 * <br />
		 * <code>http://www.w3.org/2001/XMLSchema#float</code>
		 */
		public static final String FLOAT = PREFIX + "float";

		/**
		 * QNamed XML Scheme Definition data-types of floating point numbers <br />
		 * <br />
		 * <code>xsd:float</code>
		 */
		public static final String QFLOAT = QNAME + ":" + "float";	

		/**
		 * XML Scheme Definition data-types of boolean <br />
		 * <br />
		 * <code>http://www.w3.org/2001/XMLSchema#boolean</code>
		 */
		public static final String BOOLEAN = PREFIX + "boolean";

		/**
		 * QNamed XML Scheme Definition data-types of boolean <br />
		 * <br />
		 * <code>xsd:boolean</code>
		 */
		public static final String QBOOLEAN = QNAME + ":" + "boolean";

		/**
		 * XML Scheme Definition data-types of date <br />
		 * <br />
		 * <code>http://www.w3.org/2001/XMLSchema#date</code>
		 */
		public static final String DATE = PREFIX + "date";
		
		/**
		 * QNamed XML Scheme Definition data-types of date <br />
		 * <br />
		 * <code>xsd:date</code>
		 */
		public static final String QDATE = QNAME + ":" + "date";
		
		/**
		 * XML Scheme Definition data-types of time <br />
		 * <br />
		 * <code>http://www.w3.org/2001/XMLSchema#time</code>
		 */
		public static final String TIME = PREFIX + "time";

		/**
		 * QNamed XML Scheme Definition data-types of time <br />
		 * <br />
		 * <code>xsd:time</code>
		 */
		public static final String QTIME = QNAME + ":" + "time";
		
		/**
		 * XML Scheme Definition data-types of date-time <br />
		 * <br />
		 * <code>http://www.w3.org/2001/XMLSchema#dateTime</code>
		 */
		public static final String DATETIME = PREFIX + "dateTime";

		/**
		 * QNamed XML Scheme Definition data-types of date-time <br />
		 * <br />
		 * <code>xsd:dateTime</code>
		 */
		public static final String QDATETIME = QNAME + ":" + "dateTime";

		/**
		 * XML Scheme Definition data-types of any <br />
		 * <br />
		 * <code>http://www.w3.org/2001/XMLSchema#any</code>
		 */
		public static final String ANY = PREFIX + "any";

		/**
		 * QNamed XML Scheme Definition data-types of any <br />
		 * <br />
		 * <code>xsd:any</code>
		 */
		public static final String QANY = QNAME + ":" + "any";
	}
	
	
	public static interface TMCL {
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

		public static String ASSOCIATION_ROLE_CONSTRAINT = PREFIX + "association-role-constraint";

		public static String OCCURRENCE_DATATYPE_CONSTRAINT = PREFIX + "occurrence-datatype-constraint";

		public static String ITEM_IDENTIFIER_CONSTRAINT = PREFIX + "item_identifier-constraint";

		public static String REGULAR_EXPRESSION_CONSTRAINT = PREFIX + "regular-expression-constraint";

		public static String SCOPE_REQUIRED_CONSTRAINT = PREFIX + "scope-required-constraint";

		public static String SUBJECT_IDENTIFIER_CONSTRAINT = PREFIX + "subject-identifier-constraint";

		public static String SUBJECT_LOCATOR_CONSTRAINT = PREFIX + "subject-locator-constraint";

		public static String TOPIC_NAME_CONSTRAINT = PREFIX + "topic-name-constraint";

		public static String TOPIC_OCCURRENCE_CONSTRAINT = PREFIX + "topic-occurrence-constraint";

		public static String TOPIC_REIFIES_CONSTRAINT = PREFIX + "topic-reifies-constraint";

		public static String TOPIC_ROLE_CONSTRAINT = PREFIX + "topic-role-constraint";

		public static String ROLE_COMBINATION_CONSTRAINT = PREFIX + "role-combination-constraint";

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
		public static String CONSTRAINED_CONSTRUCT = PREFIX + "constrained-construct";
		public static String CONSTRAINED_ROLE = PREFIX + "constrained-role";
		public static String CONSTRAINED_SCOPE = PREFIX + "constrained-scope";
		public static String CONSTRAINED_SCOPE_TOPIC = PREFIX + "constrained-scope-topic";
		public static String CONSTRAINED_STATEMENT = PREFIX + "constrained-statement";
		public static String CONSTRAINED_TOPIC_TYPE = PREFIX + "constrained-topic-type";
		public static String CONTAINEE = PREFIX + "containee";
		public static String CONTAINER = PREFIX + "container";
		public static String DATATYPE = PREFIX + "datatype";
		public static String DESCRIPTION = PREFIX + "description";
		public static String INCLUDE_SCHEMA = PREFIX + "includes-schema";
		public static String OTHER_CONSTRAINED_ROLE = PREFIX + "other-constrained-role";
		public static String OTHER_CONSTRAINED_TOPIC_TYPE = PREFIX + "other-constrained-topic-type";
		public static String OVERLAPS = PREFIX + "overlaps";
		public static String REGEXP = PREFIX + "regexp";
		public static String REIFIER_CONSTRAINT = PREFIX + "reifier-constraint";
		public static String REQUIREMENT_CONSTRAINT = PREFIX + "requirement-constraint";
		public static String SCHEMA = PREFIX + "schema";
		public static String SCHEMA_RESOURCE = PREFIX + "schema-resource";
		public static String SCOPE_CONSTRAINT = PREFIX + "scope-constraint";
		public static String SEE_ALSO = PREFIX + "see-also";
		public static String UNIQUE_VALUE_CONSTRAINT = PREFIX + "unique-value-constraint";
		public static String USED = PREFIX + "used";
		public static String USER = PREFIX + "user";
		public static String USER_DEFINED_CONSTRAINT = PREFIX + "user-defined-constraint";
		public static String USES_SCHEMA = PREFIX + "uses-schema";
		public static String VALIDATION_EXPRESSION = PREFIX + "validation-expression";
		public static String VARIANT_NAME_CONSTRAINT = PREFIX + "variant-name-constraint";
		public static String VERSION = PREFIX + "version";
	}
	
	public static interface Onotoa {
		/**
		 * General prefix for the types.
		 */
		public final static String PREFIX = "http://onotoa.topicmapslab.de/export/";
		
		////////////////// Topic Types ///////////////////////////////
		public final static String DIAGRAM = PREFIX + "diagram";
		
		public final static String NODE = PREFIX + "node";
		
		public final static String EDGE = PREFIX + "edge";
		
		public final static String COMMENT = PREFIX + "comment";
		
		public final static String BENDPOINT = PREFIX + "bendpoint";
		
		///////////////// Occurrence Types /////////////////////////////////
		
		public final static String ID = PREFIX + "id";
		
		public final static String TYPE = PREFIX + "type";
		
		public final static String POSX = PREFIX + "posx";
		
		public final static String POSY = PREFIX + "posy";

		public final static String WIDTH = PREFIX + "width";
		
		public final static String HEIGHT = PREFIX + "height";
		
		public final static String CONTENT = PREFIX + "content";
		
		////////////////// Role Types ///////////////////////////////
		
		public final static String SOURCE = PREFIX + "source";
		
		public final static String TARGET = PREFIX + "target";
		
		public final static String CONNECTOR = PREFIX + "connector";
		
		public final static String CONTAINER = PREFIX + "container";
		
		public final static String CONTAINEE = PREFIX + "containee";
		
		public final static String REFERER = PREFIX + "referer";
		
		public final static String REFEREE = PREFIX + "referee";
		
	////////////////// Role Types ///////////////////////////////
		
		public final static String CONTAINS = PREFIX + "contains";
		
		public final static String REFERS = PREFIX + "refers";
		
		public final static String CONNECTS = PREFIX + "connects";
	}
}
