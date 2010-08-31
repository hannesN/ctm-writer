/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/    
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 *
 */
package de.topicmapslab.common.tools.collections;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Utility class to provide additional features for {@link Collection}s. Class
 * contains mathematical combination operation and transformation opterations.
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 * 
 */
public class CollectionUtils {

	/**
	 * Mathematical combination of values given by a set of type E. Method
	 * creates and return all possible combinations of the given values, the
	 * order will not be observed.
	 * 
	 * @param <E>
	 *            the type of the given and returned values
	 * @param set
	 *            the given set
	 * @return a sequence of all combinations where the order will not be
	 *         observed
	 */
	public static <E extends Object> Set<Set<E>> combination(Set<E> set) {
		Set<E> combination = new HashSet<E>();
		Set<Set<E>> combinations = new HashSet<Set<E>>();
		combination(set, combination, combinations);
		return combinations;
	}

	/**
	 * Internal function to realize the mathematical combination of given
	 * values. This method creates the next sub-tree by iterating over values
	 * given by parameter set.
	 * 
	 * @param <E>
	 *            the type of the given and returned values
	 * @param set
	 *            the possible values for next iteration
	 * @param combination
	 *            the current path of the abstract combination tree
	 * @param combinations
	 *            overall combinations
	 */
	private static <E extends Object> void combination(Set<E> set,
			Set<E> combination, Set<Set<E>> combinations) {
		for (E value : set) {
			Set<E> combination_ = new HashSet<E>();
			combination_.addAll(combination);
			combination_.add(value);
			combinations.add(combination_);

			Set<E> set_ = new HashSet<E>();
			set_.addAll(set);
			long size = set_.size();
			set_.remove(value);
			if (set_.size() == size) {
				for (E v : set_) {
					if (v.equals(value)) {
						set_.remove(v);
						break;
					}
				}
			}
			if (!set_.isEmpty()) {
				combination(set_, combination_, combinations);
			}
		}
	}

	/**
	 * Mathematical combination of values given by a list of type E. Method
	 * creates and return all possible combinations of the given values, the
	 * order will be observed.
	 * 
	 * @param <E>
	 *            the type of the given and returned values
	 * @param list
	 *            the given set
	 * @return a sequence of all combinations where the order will not be
	 *         observed
	 */
	public static <E extends Object> List<List<E>> combination(List<E> list) {
		List<E> combination = new LinkedList<E>();
		List<List<E>> combinations = new LinkedList<List<E>>();
		combination(list, combination, combinations);
		return combinations;
	}

	/**
	 * Internal function to realize the mathematical combination of given
	 * values. This method creates the next sub-tree by iterating over values
	 * given by parameter set.
	 * 
	 * @param <E>
	 *            the type of the given and returned values
	 * @param list
	 *            the possible values for next iteration
	 * @param combination
	 *            the current path of the abstract combination tree
	 * @param combinations
	 *            overall combinations
	 */
	private static <E extends Object> void combination(List<E> list,
			List<E> combination, List<List<E>> combinations) {
		for (E value : list) {
			List<E> combination_ = new LinkedList<E>();
			combination_.addAll(combination);
			combination_.add(value);
			combinations.add(combination_);

			List<E> list_ = new LinkedList<E>();
			list_.addAll(list);
			list_.remove(list);
			if (!list_.isEmpty()) {
				combination(list_, combination_, combinations);
			}
		}
	}

	/**
	 * Transformation operation for a {@link Collection} to a {@link Set}.
	 * 
	 * @param <E>
	 *            the type of the given and returned values
	 * @param collection
	 *            the collection to transform
	 * @return the new {@link Set}, containing all entries of the given
	 *         collection
	 */
	public static <E extends Object> Set<E> asSet(Collection<E> collection) {
		Set<E> hashSet = new HashSet<E>();
		hashSet.addAll(collection);
		return hashSet;
	}

	/**
	 * Transformation operation for a {@link Collection} to a {@link List}.
	 * 
	 * @param <E>
	 *            the type of the given and returned values
	 * @param collection
	 *            the collection to transform
	 * @return the new {@link List}, containing all entries of the given
	 *         collection
	 */
	public static <E extends Object> List<E> asList(Collection<E> collection) {
		List<E> list = new LinkedList<E>();
		list.addAll(collection);
		return list;
	}

}
