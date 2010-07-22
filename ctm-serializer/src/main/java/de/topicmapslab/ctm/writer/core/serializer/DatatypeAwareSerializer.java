/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/    
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 *
 */
package de.topicmapslab.ctm.writer.core.serializer;

import static de.topicmapslab.ctm.writer.utility.CTMTokens.DATATYPE;
import static de.topicmapslab.ctm.writer.utility.CTMTokens.PREFIXBEGIN;
import static de.topicmapslab.ctm.writer.utility.CTMTokens.PREFIXEND;
import static de.topicmapslab.ctm.writer.utility.CTMTokens.QUOTE;
import static de.topicmapslab.ctm.writer.utility.CTMTokens.TRIPPLEQUOTE;

import java.net.MalformedURLException;
import java.net.URL;

import org.tmapi.core.DatatypeAware;
import org.tmapi.core.Topic;

import de.topicmapslab.ctm.writer.core.CTMTopicMapWriter;
import de.topicmapslab.ctm.writer.exception.SerializerException;
import de.topicmapslab.ctm.writer.utility.CTMBuffer;
import de.topicmapslab.identifier.XmlSchemeDatatypes;

/**
 * Class to realize the serialization of the following CTM grammar rule. <br />
 * <br />
 * <code>literal ::=  	iri	  
					|	number
					|	date	  
					|	date-time	  
					|	string	  
					|	string '^^' iri-ref
					| 	variable <br />
 * The serialized CTM string represents the literal of a occurrence or variant within the
 * occurrence or name definition.
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 * 
 */
public class DatatypeAwareSerializer implements ISerializer<DatatypeAware> {

	/**
	 * Method to convert the given construct to its specific CTM string. The
	 * result should be written to the given output buffer.
	 * 
	 * @param writer
	 *            the CTM writer
	 * @param datatypeAware
	 *            the datatypeAware to serialize
	 * @param buffer
	 *            the output buffer
	 * @return <code>true</code> if new content was written into buffer,
	 *         <code>false</code> otherwise
	 * @throws SerializerException
	 *             Thrown if serialization failed.
	 */
	public static boolean serialize(CTMTopicMapWriter writer,
			DatatypeAware datatypeAware, CTMBuffer buffer)
			throws SerializerException {
		final String value = datatypeAware.getValue();
		final String datatype = writer.getCtmIdentity().getPrefixedIdentity(
				datatypeAware.getDatatype());
		return serialize(writer, datatype, value, buffer);
	}

	/**
	 * Method to convert the given values to a specific CTM literal. The result
	 * should be written to the given output buffer.
	 * 
	 * @param writer
	 *            the CTM writer
	 * @param datatype
	 *            the data-type of the {@link DatatypeAware} as {@link Topic} or
	 *            as {@link String}
	 * @param value_
	 *            the value of the {@link DatatypeAware}
	 * @param buffer
	 *            the output buffer
	 * @return <code>true</code> if new content was written into buffer,
	 *         <code>false</code> otherwise
	 * @throws SerializerException
	 *             Thrown if serialization failed.
	 */
	public static boolean serialize(CTMTopicMapWriter writer,
			final Object datatype, final String value_, CTMBuffer buffer)
			throws SerializerException {
		/*
		 * extract reference of the data-type as string
		 */
		final String datatype_;
		if (datatype instanceof Topic) {
			datatype_ = writer.getCtmIdentity().getPrefixedIdentity(
					writer.getProperties(), (Topic) datatype).toString();
		} else {
			datatype_ = (String) datatype;
		}

		/*
		 * convert value representation by type
		 */
		String value = value_;
		/*
		 * type is xsd:string
		 */
		if (XmlSchemeDatatypes.XSD_STRING.equalsIgnoreCase(datatype_)
				|| XmlSchemeDatatypes.XSD_QSTRING.equalsIgnoreCase(datatype_)
				|| datatype_ == null) {
			/*
			 * string is a variable
			 */
			if (isVariable(value)) {
				buffer.append(false, value);
			}
			/*
			 * string contains tripple quotes
			 */
			else if (value.contains(TRIPPLEQUOTE)){
				buffer
				.append(false, TRIPPLEQUOTE, value.replaceAll(QUOTE, "\\\\"+QUOTE),
						TRIPPLEQUOTE);
			}
			/*
			 * string contains quotes
			 */
			else if (value.contains(QUOTE)) {
				buffer
						.append(false, TRIPPLEQUOTE, value,
								TRIPPLEQUOTE);
			}
			/*
			 * string does not contain any quotes
			 */
			else {
				buffer.append(false, QUOTE, value, QUOTE);
			}
		}
		/*
		 * type is xsd:anyURI
		 */
		else if (XmlSchemeDatatypes.XSD_ANYURI.equalsIgnoreCase(datatype_)
				|| XmlSchemeDatatypes.XSD_QANYURI.equalsIgnoreCase(datatype_)) {
			/*
			 * URI is variable
			 */
			if (isVariable(value)) {
				buffer.append(false, value);
			} else {
				String uri = value.replaceAll(" ", "%20");
				buffer.append(false, PREFIXBEGIN + uri + PREFIXEND);
			}
		}
		/*
		 * type is xsd:integer
		 */
		else if (XmlSchemeDatatypes.XSD_INTEGER.equalsIgnoreCase(datatype_)
				|| XmlSchemeDatatypes.XSD_QINTEGER.equalsIgnoreCase(datatype_)) {
			buffer.append(false, value);
		}
		/*
		 * type is non-specific
		 */
		else {
			if (isVariable(value)) {
				buffer.append(false, value);
			} else if (value.contains(QUOTE)) {
				buffer.append(false, TRIPPLEQUOTE, value, TRIPPLEQUOTE);
			} else {
				buffer.append(false, QUOTE, value, QUOTE);
			}
			buffer.append(false, DATATYPE, datatype_);
		}
		return true;
	}

	/**
	 * Method is called to extract the arguments the value argument from the
	 * given {@link DatatypeAware}. The value is convert by type.
	 * 
	 * @param writer
	 *            the parent topic map writer
	 * @param datatypeAware
	 *            the {@link DatatypeAware}
	 * @return the converted value
	 * @throws SerializerException
	 *             Thrown if serialization failed.
	 */
	public static String toArgument(

	final CTMTopicMapWriter writer, final DatatypeAware datatypeAware)
			throws SerializerException {
		final String value = datatypeAware.getValue();
		final String datatype = writer.getCtmIdentity().getPrefixedIdentity(
				datatypeAware.getDatatype()).toString();
		return toArgument(writer, datatype, value);
	}

	/**
	 * Method is called to convert the given value to a argument. The value is
	 * convert by type.
	 * 
	 * @param writer
	 *            the parent topic map writer
	 * @param datatype
	 *            the data-type of the {@link DatatypeAware} as {@link Topic} or
	 *            as {@link String}
	 * @param value
	 *            the value of the {@link DatatypeAware}
	 * @return the converted value
	 * @throws SerializerException
	 *             Thrown if serialization failed.
	 */
	public static String toArgument(final CTMTopicMapWriter writer,
			final Object datatype, final String value)
			throws SerializerException {
		/*
		 * extract reference of the data-type as string
		 */
		final String datatype_;
		if (datatype instanceof Topic) {
			datatype_ = writer.getCtmIdentity().getPrefixedIdentity(
					writer.getProperties(), (Topic) datatype).toString();
		} else {
			datatype_ = (String) datatype;
		}
		/*
		 * type is xsd:string
		 */
		if (XmlSchemeDatatypes.XSD_STRING.equalsIgnoreCase(datatype_)
				|| XmlSchemeDatatypes.XSD_QSTRING.equalsIgnoreCase(datatype_)) {
			/*
			 * string is variable
			 */
			if (isVariable(value)) {
				return value;
			}
			/*
			 * string contains quotes
			 */
			else if (value.contains(QUOTE)) {
				return TRIPPLEQUOTE + value + TRIPPLEQUOTE;
			}
			/*
			 * string does not contain any quotes
			 */
			else {
				return QUOTE + value + QUOTE;
			}
		}
		/*
		 * type is xsd:anyURI
		 */
		else if (XmlSchemeDatatypes.XSD_ANYURI.equalsIgnoreCase(datatype_)
				|| XmlSchemeDatatypes.XSD_QANYURI.equalsIgnoreCase(datatype_)) {
			/*
			 * URI is variable
			 */
			if (isVariable(value)) {
				return value;
			}
			return PREFIXBEGIN + value + PREFIXEND;
		}
		/*
		 * type is xsd:integer
		 */
		else if (XmlSchemeDatatypes.XSD_INTEGER.equalsIgnoreCase(datatype_)
				|| XmlSchemeDatatypes.XSD_QINTEGER.equalsIgnoreCase(datatype_)) {
			return value;
		}
		/*
		 * type is non-specific
		 */
		else {
			/*
			 * value is variable
			 */
			if (isVariable(value)) {
				return value;
			}
			/*
			 * value contains quotes
			 */
			else if (value.contains(QUOTE)) {
				return TRIPPLEQUOTE + value + TRIPPLEQUOTE;
			}
			/*
			 * value does not contain any quotes
			 */
			else {
				return QUOTE + value + QUOTE;
			}
		}
	}

	/**
	 * Method is called to convert the given value to a argument. The method try
	 * to detect the type of the given value.
	 * 
	 * @param value
	 *            the value of the {@link DatatypeAware}
	 * @return the converted value
	 * @throws SerializerException
	 *             Thrown if serialization failed.
	 */
	public static String toArgument(final String value)
			throws SerializerException {

		if (isVariable(value)) {
			return value;
		}

		try {
			Integer.parseInt(value);
			return value;
		} catch (NumberFormatException e) {
			try {
				new URL(value);
				return PREFIXBEGIN + value + PREFIXEND;
			} catch (MalformedURLException ex) {
				if (value.contains(QUOTE)) {
					return TRIPPLEQUOTE + value + TRIPPLEQUOTE;
				} else {
					return QUOTE + value + QUOTE;
				}
			}
		}
	}

	/**
	 * Internal method to check if given value is a variable.
	 * 
	 * @param value
	 *            the value
	 * @return <code>true</code> if value starts with a dollar and does not
	 *         contain any white-spaces, <code>false</code> otherwise.
	 */
	private static boolean isVariable(final String value) {
		return value.startsWith("$") && !value.contains(" ");
	}

}
