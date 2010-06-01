/* 
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/    
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 *
 */
package de.topicmapslab.ctm.writer.templates;

import static de.topicmapslab.ctm.writer.utility.CTMTokens.TOPICVARIABLE;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import de.topicmapslab.ctm.writer.templates.entry.TemplateEntry;
import de.topicmapslab.ctm.writer.templates.entry.base.IEntry;

/**
 * Utility class to represent a candidate for template merging. Each candidate
 * contains a set of entries which are used in different templates.
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 * 
 */
public class MergeCandidate implements Comparable<MergeCandidate> {
	/**
	 * the set of entries representing by this candidate
	 */
	protected final Set<IEntry> entries = new HashSet<IEntry>();
	/**
	 * the set of templates using the contained entries
	 */
	protected Set<Template> templates = new HashSet<Template>();

	/**
	 * an unique UUID as part of new template-name
	 */
	private UUID uuid = UUID.randomUUID();

	/**
	 * Method checks if the given template is contained by the internal list.
	 * 
	 * @param template
	 *            the template
	 * @return <code>true</code> if the internal list contains the given
	 *         template, <code>false</code> otherwise.
	 */
	public boolean contains(Template template) {
		return templates.contains(template);
	}

	/**
	 * Method checks if the given entry is contained by the internal list.
	 * 
	 * @param entry
	 *            the entry
	 * @return <code>true</code> if the internal list contains the given entry,
	 *         <code>false</code> otherwise.
	 */
	public boolean contains(IEntry entry) {
		for (IEntry e : entries) {
			if (e.equals(entry)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Check if candidate is adaptive for given template.
	 * 
	 * @param template
	 *            the template
	 * @return <code>true</code> if the candidate can replaced a part of the
	 *         given template.
	 */
	public boolean isAdaptiveFor(Template template) {
		if (entries.size() <= template.getEntries().size()) {
			for (IEntry entry : template.getEntries()) {
				if (template.getEntries().contains(entry)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Method is called to transform the internal entries to a template entry,
	 * to provide a template-invocation as a part of a template.
	 * 
	 * @param template
	 *            the parent template
	 * @return the generated template-entry
	 */
	public TemplateEntry toTemplateEntry(Template template) {
		List<String> variables = new LinkedList<String>();
		variables.add(TOPICVARIABLE);
		for (IEntry entry : template.getEntries()) {
			if (contains(entry)) {
				variables.add(entry.getParameter().getCTMRepresentation());
			}
		}
		Template t = toTemplate();
		return new TemplateEntry(t, variables.toArray(new String[0]));
	}

	/**
	 * Method is called to transform the internal entries to a template to
	 * provide a template, which can be called by template-invocation.
	 * 
	 * @return the generated template
	 */
	public Template toTemplate() {
		Template t = new Template(templates.iterator().next().getTemplateName()
				+ "-invoc-" + uuid.getMostSignificantBits());
		for (IEntry entry : entries) {
			t.add(entry);
		}
		return t;
	}

	/**
	 * Comparison of the {@link MergeCandidate}
	 * 
	 * @param o
	 *            the other {@link MergeCandidate}
	 * 
	 * @return a negative integer if the other {@link MergeCandidate} contains
	 *         less entries than this or it matches to less templates than this
	 *         ( only if the number of entries are equal )<br />
	 * <br />
	 *         zero if the number of entries and the number of templates are
	 *         equals<br />
	 * <br />
	 *         a positive integer if the other {@link MergeCandidate} contains
	 *         more entries than this or it matches to more templates than this
	 *         ( only if the number of entries are equal )<br />
	 * <br />
	 */
	public int compareTo(MergeCandidate o) {
		int compare = o.entries.size() - entries.size();
		if (compare == 0) {
			compare = o.templates.size() - templates.size();
		}
		return compare;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof MergeCandidate) {
			if (entries.size() != ((MergeCandidate) obj).entries.size()) {
				return false;
			}
			for (IEntry entry : entries) {
				boolean equal_ = false;
				for (IEntry entry_ : ((MergeCandidate) obj).entries) {
					if (entry_.equals(entry)) {
						equal_ = true;
						break;
					}
				}
				if (!equal_) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return super.hashCode();
	}

	/**
	 * Redirect to {@link Template#toString()} <br />
	 * <br /> {@inheritDoc}
	 */
	@Override
	public String toString() {
		return toTemplate().toString();
	}
}
