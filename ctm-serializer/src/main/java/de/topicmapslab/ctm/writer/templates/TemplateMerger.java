/*
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/    
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 *
 */
package de.topicmapslab.ctm.writer.templates;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import de.topicmapslab.common.tools.collections.CollectionUtils;
import de.topicmapslab.ctm.writer.properties.CTMTopicMapWriterProperties;
import de.topicmapslab.ctm.writer.templates.entry.TemplateEntry;
import de.topicmapslab.ctm.writer.templates.entry.base.IEntry;

/**
 * Implementation of a auto-template merger algorithm. Class provides methods to
 * identity potential template-entries to merge.
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 * 
 */
public class TemplateMerger {

	/**
	 * the threshold as a minimum value for the number of entries contained in
	 * the merged template
	 */
	private final long threshold;

	/**
	 * hidden constructor
	 */
	public TemplateMerger(CTMTopicMapWriterProperties properties) {
		this.threshold = properties.getTemplateMergerThreshold();
	}

	/**
	 * Method is calling to merge templates by replacing frequently used
	 * template entries with a template-invocation call and creating a new
	 * template.
	 * 
	 * @param templates
	 *            the templates to merge
	 * @return a list containing all templates including the merged templates,
	 *         the created templates and the templates which can not be merged
	 */
	public Collection<Template> mergeTemplates(final Set<Template> templates) {
		List<Template> mergedTemplates = new LinkedList<Template>();
		/*
		 * add all old templates
		 */
		mergedTemplates.addAll(templates);

		Set<MergeCandidate> candidates = new HashSet<MergeCandidate>();

		/*
		 * iterate over all templates
		 */
		for (Template template : templates) {
			Set<IEntry> entries = new HashSet<IEntry>();
			entries.addAll(template.getEntries());
			/*
			 * create all possible combinations of the internal entries of the
			 * current template.
			 */
			Set<Set<IEntry>> combinations = CollectionUtils
					.combination(entries);
			/*
			 * iterate over entry sets
			 */
			for (Set<IEntry> combination : combinations) {
				/*
				 * check if number of threshold are greater or equal than the
				 * threshold
				 */
				if (combination.size() >= threshold) {
					/*
					 * check if merge-candidate already stored at temporary list
					 */
					MergeCandidate candidate = new MergeCandidate();
					candidate.entries.addAll(combination);
					for (MergeCandidate c : candidates) {
						if (c.equals(candidate)) {
							candidate = c;
							break;
						}
					}
					/*
					 * add matching template to candidate
					 */
					candidate.templates.add(template);
					candidates.add(candidate);
				}
			}
		}

		/*
		 * convert set to list
		 */
		List<MergeCandidate> candidatesList = CollectionUtils
				.asList(candidates);

		/*
		 * sort candidates
		 */
		Collections.sort(candidatesList, new Comparator<MergeCandidate>() {

			public int compare(MergeCandidate o1, MergeCandidate o2) {
				return o1.compareTo(o2);
			}

		});

		/*
		 * iterate over merge candidates
		 */
		for (MergeCandidate candidate : candidatesList) {
			/*
			 * check if number of matching templates are more than one
			 */
			if (candidate.templates.size() > 1) {
				/*
				 * flag if candidate really used
				 */
				boolean used = false;
				/*
				 * iterate over matching templates
				 */
				for (Template template_ : candidate.templates) {
					Template template = template_;
					/*
					 * extract template from temporary list if exists
					 */
					for (int index = 0; index < mergedTemplates.size(); index++) {
						Template t = mergedTemplates.get(index);
						if (template.getTemplateName().equalsIgnoreCase(
								t.getTemplateName())) {
							template = t;
							/*
							 * remove old template
							 */
							mergedTemplates.remove(index);
							break;
						}
					}
					/*
					 * create a new template entry
					 */
					TemplateEntry templateEntry = candidate
							.toTemplateEntry(template);
					/*
					 * remove all entries from template which are affected by
					 * the new template entry
					 */
					List<IEntry> entries = new LinkedList<IEntry>();
					entries.addAll(template.getEntries());
					long count = 0;
					for (IEntry entry : template.getEntries()) {
						if (candidate.contains(entry)) {
							entries.remove(entry);
							count++;
						}
					}
					/*
					 * check if number of affected entries and number of removed
					 * entries are equal, otherwise the entry does not match to
					 * the template, because of other modifications
					 */
					if (count == candidate.entries.size()) {
						/*
						 * modify template and store
						 */
						template = new Template(template.getTemplateName(),
								entries);
						template.add(templateEntry);
						mergedTemplates.add(template);
						used = true;
					} else {
						/*
						 * store unmodified template
						 */
						mergedTemplates.add(template);
					}
				}

				/*
				 * if candidate really used add a new template representing the
				 * merged template
				 */
				if (used) {
					mergedTemplates.add(candidate.toTemplate());
				}
			}
		}

		return mergedTemplates;
	}
}
