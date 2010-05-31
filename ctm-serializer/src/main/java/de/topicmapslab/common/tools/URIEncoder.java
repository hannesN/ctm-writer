package de.topicmapslab.common.tools;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class URIEncoder {

	/**
	 * map of all escape sequences valid for the whole IRI
	 */
	private static Map<Character, String> escapeSequences = new HashMap<Character, String>();
	/**
	 * escape sequences only valid for IRI part without parameters
	 */
	private static Map<Character, String> iriPartEscapeSequences = new HashMap<Character, String>();
	/**
	 * escape sequences only valid for parameters part ( after first ? )
	 */
	private static Map<Character, String> parameterPartEscapeSequences = new HashMap<Character, String>();

	static {
		escapeSequences.put('Ä', "%C4");
		escapeSequences.put('Ü', "%DC");
		escapeSequences.put('Ö', "%D6");
		escapeSequences.put('ä', "%E4");
		escapeSequences.put('ü', "%FC");
		escapeSequences.put('ö', "%F6");
		escapeSequences.put('ß', "%DF");
		escapeSequences.put(' ', "%20");
		escapeSequences.put(';', "%3B");
		escapeSequences.put('"', "%22");
		escapeSequences.put('(', "%28");
		escapeSequences.put(')', "%29");
		escapeSequences.put('\'', "%2C");
		escapeSequences.put('<', "%3C");
		escapeSequences.put('>', "%3E");
		escapeSequences.put('@', "%40");
		escapeSequences.put('[', "%5B");
		escapeSequences.put(']', "%5D");
		escapeSequences.put('\\', "%5C");
		escapeSequences.put('^', "%5E");
		escapeSequences.put('{', "%7B");
		escapeSequences.put('}', "%7D");
		escapeSequences.put('|', "%7C");
		escapeSequences.put('&', "%26");
		escapeSequences.put('#', "%23");

		iriPartEscapeSequences.put('=', "%3D");

		parameterPartEscapeSequences.put('?', "%3F");
		parameterPartEscapeSequences.put('/', "%2F");
		parameterPartEscapeSequences.put('.', "%2E");
		parameterPartEscapeSequences.put(':', "%3A");

	}

	/**
	 * private hidden constructor
	 */
	private URIEncoder() {
	}

	/**
	 * Encodes the given URI to valid URI format using known escape sequences. <br />
	 * - all alphanumeric characters ( a-zA-Z0-9 ) remain the same<br />
	 * - all special characters ( . : / ? ) remain the same except as a part of
	 * parameters string <br />
	 * - all other characters will be escaped
	 * 
	 * @param uri
	 *            the URI
	 * @param encoding
	 *            the used encoding
	 * @return the escaped string
	 * @throws UnsupportedEncodingException
	 */
	public static String encodeURI(final String uri, final String encoding)
			throws UnsupportedEncodingException {
		/*
		 * create temporary string
		 */
		String encode = new String(uri.getBytes(), encoding);

		/*
		 * escape all characters valid for the whole IRI
		 */
		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < encode.length(); i++) {
			char c = encode.charAt(i);
			if (escapeSequences.containsKey(c)) {
				builder.append(escapeSequences.get(c));
			} else {
				builder.append(c);
			}
		}

		encode = builder.toString();

		/*
		 * split into parameters and IRI part if exists
		 */
		int indexParameters = encode.indexOf("?");
		if (indexParameters != -1) {
			/*
			 * encode the IRI part
			 */
			String iriPart = encode.substring(0, indexParameters);
			builder = new StringBuilder();
			for (int i = 0; i < iriPart.length(); i++) {
				char c = iriPart.charAt(i);
				if (escapeSequences.containsKey(c)) {
					builder.append(escapeSequences.get(c));
				} else {
					builder.append(c);
				}
			}

			iriPart = builder.toString();

			/*
			 * encode the parameters part
			 */
			String paramPart = encode.substring(indexParameters + 1);
			builder = new StringBuilder();
			for (int i = 0; i < paramPart.length(); i++) {
				char c = paramPart.charAt(i);
				if (escapeSequences.containsKey(c)) {
					builder.append(escapeSequences.get(c));
				} else {
					builder.append(c);
				}
			}

			paramPart = builder.toString();

			/*
			 * combine
			 */
			encode = iriPart + "?" + paramPart;
		}

		/*
		 * return
		 */
		return encode;
	}
}
