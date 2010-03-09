/**
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/    
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 *
 */
package de.topicmapslab.common.tools.prefix.core;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import de.topicmapslab.common.tools.exception.ToolRuntimeException;
import de.topicmapslab.common.tools.prefix.model.IQnameProvider;

/**
 * Implementation of {@link IQnameProvider} interface, which generates a unique
 * QName by generates a unique MD2 hash of the given IRI.
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 * 
 */
public class HashQnameProvider implements IQnameProvider {

	/**
	 * {@inheritDoc}
	 */
	public String generateQname(String iri) throws ToolRuntimeException {
		try {
			/*
			 * initialize MD2 hash message-digest
			 */
			MessageDigest digest = MessageDigest.getInstance("MD2");
			digest.update(iri.getBytes("utf-8"), 0, iri.length());
			byte[] digests = digest.digest();

			/*
			 * convert hash to readable string
			 */
			StringBuffer buffer = new StringBuffer();
			for (int i = 0; i < digests.length; i++) {
				int halfbyte = (digests[i] >>> 4) & 0x0F;
				int two_halfs = 0;
				do {
					if ((0 <= halfbyte) && (halfbyte <= 9))
						buffer.append((char) ('0' + halfbyte));
					else
						buffer.append((char) ('a' + (halfbyte - 10)));
					halfbyte = digests[i] & 0x0F;
				} while (two_halfs++ < 1);
			}
			/*
			 * check if generate string start with a number
			 */
			String qname = buffer.toString();
			if (qname.matches("[0-9].*")) {
				/*
				 * at "Q" prefix
				 */
				qname = "Q" + qname;
			}
			return qname;
		} catch (NoSuchAlgorithmException e) {
			throw new ToolRuntimeException(e);
		} catch (UnsupportedEncodingException e) {
			throw new ToolRuntimeException(e);
		}
	}

}
