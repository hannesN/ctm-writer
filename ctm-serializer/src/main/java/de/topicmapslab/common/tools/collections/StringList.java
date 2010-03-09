/** 
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/    
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 *
 */
package de.topicmapslab.common.tools.collections;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Special Implementation of a {@link LinkedList} containing {@link String}s to
 * extends the base features of the {@link List} implementation with special
 * string operations.
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 * 
 */

public class StringList extends LinkedList<String> {

	private static final long serialVersionUID = 1L;

	/**
	 * empty constructor, bequeathed from super-class
	 */
	public StringList() {
	}

	/**
	 * constructor, bequeathed from super-class
	 * 
	 * @param c
	 *            base content which should be added to the new {@link List}
	 *            instance
	 */
	public StringList(Collection<String> c) {
		super(c);
	}

	/**
	 * Method checks if a {@link String} is contained by the current
	 * {@link StringList} instance, which contains the given {@link String} as a
	 * substring.
	 * 
	 * @param substring
	 *            the substring to look for
	 * @return <code>true</code> if at least one {@link String} is contained by
	 *         list, which contains the given substring, <code>false</code>
	 *         otherwise
	 */
	public boolean containsAsSubstring(final String substring) {
		for (String string : this) {
			if (string.contains(substring)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Method checks if a {@link String} is contained by the current
	 * {@link StringList} instance, which is a substring of the given
	 * {@link String}.
	 * 
	 * @param string
	 *            the string
	 * @return <code>true</code> if at least one {@link String} is contained
	 *         which is a substring of the given parameter, <code>false</code>
	 *         otherwise.
	 */
	public boolean containsSubstring(final String string) {
		for (String substring : this) {
			if (string.contains(substring)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Special erasing operation, which removes all elements from the current
	 * {@link StringList}, which contains the given substring.
	 * 
	 * @param substring
	 *            the substring which should be contained by internal elements
	 * @return the number of removed elements
	 */
	public long removeAllContainingSubstring(final String substring) {
		StringList copy = new StringList();
		copy.addAll(this);

		long count = 0;
		for (String string : copy) {
			if (string.contains(substring)) {
				this.remove(string);
				count++;
			}
		}
		return count;
	}

}
