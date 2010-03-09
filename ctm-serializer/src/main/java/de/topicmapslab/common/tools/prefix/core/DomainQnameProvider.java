/**
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/    
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 *
 */
package de.topicmapslab.common.tools.prefix.core;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.StringTokenizer;

import de.topicmapslab.common.tools.exception.ToolRuntimeException;
import de.topicmapslab.common.tools.prefix.model.IQnameProvider;

/**
 * Implementation of {@link IQnameProvider} interface, which generates a unique
 * QName by extracting domain information of the IRI.
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 * 
 */
public class DomainQnameProvider implements IQnameProvider {

	/**
	 * {@inheritDoc}
	 */
	public String generateQname(String iri) throws ToolRuntimeException {
		try {
			URI uri = new URI(iri);
			if (!"".equalsIgnoreCase(uri.getPath())) {
				final String uriref = generateFromURIPath(uri.getPath());
				if (uriref != null) {
					return uriref;
				}
			}
			return generateFromURIHost(uri.getHost());
		} catch (URISyntaxException e) {
			throw new ToolRuntimeException(e);
		}
	}

	/**
	 * Extract the a QName from the host information. If host is matching the
	 * pattern [.*]\.[.*] then the first part will be returned, otherwise the
	 * second part will be returned.
	 * 
	 * @param host
	 *            the host information to extract from
	 * @return If host is matching the pattern [.*]\.[.*] then the first part
	 *         will be returned, otherwise the second part will be returned.
	 * 
	 */
	private String generateFromURIHost(final String host) {
		StringTokenizer tokenizer = new StringTokenizer(host, ".");
		final String token;
		if (tokenizer.countTokens() > 2) {
			tokenizer.nextToken();
		}

		token = tokenizer.nextToken();
		tokenizer = new StringTokenizer(token, "/");
		return tokenizer.nextToken();
	}

	/**
	 * Extract the a QName from the path information. If path contains a hex (#)
	 * the information before the hex will be returned and if it contains a
	 * number of slashes (/) the last token will be returned. Both rules can be
	 * combined, so the token after the last slash and before the hex will be
	 * returned.
	 * 
	 * @param path
	 *            the given path
	 * @return the extracted information
	 */
	private String generateFromURIPath(final String path) {
		StringTokenizer tokenizer = new StringTokenizer(path, "/");
		if (tokenizer.countTokens() > 0) {
			final String token = tokenizer.nextToken();
			tokenizer = new StringTokenizer(token, "#");
			return tokenizer.nextToken();
		}
		return null;
	}

}
