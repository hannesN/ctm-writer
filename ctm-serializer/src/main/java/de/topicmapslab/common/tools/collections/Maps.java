/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/    
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 *
 */
package de.topicmapslab.common.tools.collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Utility class to provide additional features for {@link Map}s. Class contains
 * operations to sort a {@link Map}.
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 * 
 */
public class Maps {

	/**
	 * Method provides sort operations for a {@link Map}. The given {@link Map}
	 * will be sort by value in first iteration and by key in second iteration
	 * if Flag is set.
	 * 
	 * @param <K>
	 *            the key type of the given map
	 * @param <V>
	 *            the value type of the given map
	 * @param map
	 *            the map to sort
	 * @param comparator
	 *            the comparator class for comparing the values
	 * @param sortKeysAsAlternative
	 *            the flag representing the comparison of keys, if values are
	 *            equal
	 * @param ascendingKey
	 *            sort keys ascending
	 * @param ascendingValue
	 *            sort values ascending
	 * @return a list of sorted {@link Entry}s
	 */
	public static <K extends Object, V extends Object> List<Entry<K, V>> sort(
			Map<K, V> map, final Comparator<V> comparator,
			final boolean sortKeysAsAlternative, final boolean ascendingKey,
			final boolean ascendingValue) {
		List<Entry<K, V>> list = new ArrayList<Entry<K, V>>();
		list.addAll(map.entrySet());

		Collections.sort(list, new Comparator<Entry<K, V>>() {
			@SuppressWarnings("unchecked")
			public int compare(Entry<K, V> o1, Entry<K, V> o2) {
				int compare = comparator.compare(o1.getValue(), o2.getValue());
				if (sortKeysAsAlternative && compare == 0) {
					if (o1.getKey() instanceof Comparable<?>) {
						compare = ((Comparable<K>) o1.getKey()).compareTo(o2
								.getKey());
						if (ascendingValue) {
							compare *= -1;
						}
					}
				} else if (ascendingKey) {
					compare *= -1;
				}
				return compare;
			}
		});

		return list;
	}

}
