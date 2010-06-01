/* 
 * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/    
 * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 *
 */
package de.topicmapslab.ctm.writer.templates.autodetection;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.tmapi.core.Association;
import org.tmapi.core.Construct;
import org.tmapi.core.Name;
import org.tmapi.core.Occurrence;
import org.tmapi.core.Role;
import org.tmapi.core.Topic;
import org.tmapi.core.TopicMap;
import org.tmapi.index.TypeInstanceIndex;

import de.topicmapslab.ctm.writer.core.CTMTopicMapWriter;
import de.topicmapslab.ctm.writer.exception.SerializerException;
import de.topicmapslab.ctm.writer.templates.Template;
import de.topicmapslab.ctm.writer.templates.TemplateFactory;
import de.topicmapslab.ctm.writer.templates.entry.NameEntry;
import de.topicmapslab.ctm.writer.templates.entry.OccurrenceEntry;
import de.topicmapslab.ctm.writer.templates.entry.RoleEntry;
import de.topicmapslab.ctm.writer.templates.entry.param.ParamFactory;

/**
 * Implementation of a auto-detection algorithm for templates as part of a topic
 * map.
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 * 
 */
public class TemplateDetection {

	/**
	 * the relevance threshold for auto-detection of template. The threshold
	 * define the minimum frequency of possible use of a detected template
	 */
	private final float threshold;

	/**
	 * the type-instance-index of the topic map
	 */
	private final TypeInstanceIndex index;

	/**
	 * the reference of the parent topic map writer
	 */
	private final CTMTopicMapWriter writer;

	/**
	 * constructor
	 * 
	 * @param properties
	 *            the properties of the encapsulating property instance
	 * @param topicMap
	 *            the topic map
	 */
	public TemplateDetection(final CTMTopicMapWriter writer,
			final TopicMap topicMap) {
		this.index = topicMap.getIndex(TypeInstanceIndex.class);
		this.writer = writer;
		this.threshold = writer.getProperties()
				.getTemplateDetectionRelevanceThreshold();
	}

	/**
	 * Method is calling to detect templates as a part of the topic map. Method
	 * is looking for frequently patterns in context of topic items as instances
	 * of a specific type.
	 * 
	 * @return a set of detected templates
	 * @throws SerializerException
	 *             thrown if detection algorithm failed
	 */
	public Set<Template> tryToDetectTemplates() throws SerializerException {
		Set<Template> templates = new HashSet<Template>();

		/*
		 * auto-detect topic-templates if enabled
		 */
		if (writer.getProperties().isTopicTemplateDetectionSupported()) {
			for (Topic type : index.getTopicTypes()) {
				templates.addAll(tryToDetectTopicTemplates(type));
			}
		}

		/*
		 * auto-detect association-templates if enabled
		 */
		if (writer.getProperties().isAssociationTemplateDetectionSupported()) {
			for (Topic type : index.getAssociationTypes()) {
				templates.addAll(tryToDetectAssociationTemplates(type));
			}
		}

		return templates;
	}

	/**
	 * Method is called to detect templates as part of a topic-block. Method is
	 * looking for frequently patterns of ontology. It try to detect this
	 * patterns as part of occurrence or name entries.
	 * 
	 * @param type
	 *            the topic type which specified the instances to scan
	 * @return a set of detected templates
	 * @throws SerializerException
	 *             thrown if transformation failed
	 */
	public Set<Template> tryToDetectTopicTemplates(Topic type)
			throws SerializerException {
		Set<Template> templates = new HashSet<Template>();

		/*
		 * store for all template-candidates
		 */
		Map<Class<? extends Construct>, Set<Candidate>> candidateMap = new HashMap<Class<? extends Construct>, Set<Candidate>>();
		/*
		 * iterate over all instances of the given type
		 */
		Collection<Topic> instances = index.getTopics(type);
		for (Topic topic : instances) {
			/*
			 * scan occurrence entries
			 */
			Set<Candidate> candidates = candidateMap.get(Occurrence.class);
			if (candidates == null) {
				candidates = new HashSet<Candidate>();
			}
			for (Occurrence occurrence : topic.getOccurrences()) {
				Candidate candidate = null;
				/*
				 * extract candidate form storage if exists
				 */
				for (Candidate c : candidates) {
					Occurrence occ = ((Occurrence) c.construct);
					if (occ.getType().equals(occurrence.getType())
							&& occ.getDatatype().equals(
									occurrence.getDatatype())
							&& occ.getScope()
									.containsAll(occurrence.getScope())
							&& occurrence.getScope()
									.containsAll(occ.getScope())) {
						candidate = c;
						break;
					}
				}
				if (candidate == null) {
					candidate = new Candidate();
					candidate.construct = occurrence;
				}
				/*
				 * increment count of the usage of this entry
				 */
				candidate.count++;
				candidates.add(candidate);
			}
			/*
			 * store results of the current instance
			 */
			candidateMap.put(Occurrence.class, candidates);

			/*
			 * scan name entries
			 */
			candidates = candidateMap.get(Name.class);
			if (candidates == null) {
				candidates = new HashSet<Candidate>();
			}
			for (Name name : topic.getNames()) {
				Candidate candidate = null;
				/*
				 * extract candidate from storage if exists
				 */
				for (Candidate c : candidates) {
					Name n = ((Name) c.construct);
					if (n.getType().equals(name.getType())
							&& n.getScope().containsAll(name.getScope())
							&& name.getScope().containsAll(n.getScope())) {
						candidate = c;
						break;
					}
				}
				if (candidate == null) {
					candidate = new Candidate();
					candidate.construct = name;
				}
				/*
				 * increment count of the usage of this entry
				 */
				candidate.count++;
				candidates.add(candidate);
			}
			/*
			 * store results of the current instance
			 */
			candidateMap.put(Name.class, candidates);
		}

		/*
		 * generate a template from given candidates by using the threshold
		 */
		Template template = candidatesToTemplate(type, candidateMap, instances
				.size());
		/*
		 * allow only relevant templates with more than two entries
		 */
		if (template.getEntries().size() > 2) {
			templates.add(template);
		}

		return templates;
	}

	/**
	 * Method to convert the relevant candidates to a template.
	 * 
	 * @param type
	 *            the topic type which specified the instances to scan
	 * @param candidates
	 *            the set of candidates
	 * @param total
	 *            the number of scanned entries
	 * @return the generated template
	 * @throws SerializerException
	 *             thrown if transformation failed
	 */
	private Template candidatesToTemplate(Topic type,
			Map<Class<? extends Construct>, Set<Candidate>> candidates,
			long total) throws SerializerException {

		TemplateFactory factory = writer.getFactory();

		/*
		 * create template identifier
		 */
		String identifier = writer.getCtmIdentity().getMainIdentifier(
				writer.getProperties(), type).getIdentifier();

		/*
		 * is prefixed IRI
		 */
		if (identifier.contains(":")) {
			identifier = identifier.substring(identifier.lastIndexOf(":") + 1);
		}

		/*
		 * is slash contained
		 */
		if (identifier.contains("/")) {
			identifier = identifier.substring(identifier.lastIndexOf("/") + 1);
		}

		/*
		 * is hash contained
		 */
		if (identifier.contains("#")) {
			identifier = identifier.substring(identifier.lastIndexOf("#") + 1);
		}

		/*
		 * create a template for the given type
		 */
		Template template = factory.newTemplate("template-topic-" + identifier);

		/*
		 * add instance-property-entry to the template
		 */
		template.add(factory.getEntryFactory().newIsInstanceOfEntry(
				factory.getEntryFactory().newTopicTypeParam(type)));

		Set<String> variables = new HashSet<String>();

		/*
		 * iterate over all occurrence-candidates
		 */
		for (Candidate candidate : candidates.get(Occurrence.class)) {
			/*
			 * check frequency against threshold
			 */
			if ((float) candidate.count / (float) total >= threshold) {
				/*
				 * create new occurrence-template-entry by construct
				 */
				OccurrenceEntry entry = OccurrenceEntry.buildFromConstruct(
						writer, (Occurrence) candidate.construct);
				int count = 2;
				/*
				 * check if variable name is unique and try to unify variable
				 * name if necessary
				 */
				String variable = entry.getParameter().getCTMRepresentation();
				while (variables.contains(variable)) {
					variable = entry.getParameter().getCTMRepresentation()
							+ Integer.toString(count);
					count++;
				}
				/*
				 * set new variable name
				 */
				entry.setValueOrVariable(factory.getEntryFactory()
						.newVariableParam(variable));
				/*
				 * store variable name
				 */
				variables.add(variable);
				/*
				 * add entry to template
				 */
				template.add(entry);
			}
		}

		/*
		 * iterate over all name-candidates
		 */
		for (Candidate candidate : candidates.get(Name.class)) {
			/*
			 * check frequency against threshold
			 */
			if ((float) candidate.count / (float) total >= threshold) {
				/*
				 * create new name-template-entry by construct
				 */
				NameEntry entry = NameEntry.buildFromConstruct(writer,
						(Name) candidate.construct);
				int count = 2;
				/*
				 * check if variable name is unique and try to unify variable
				 * name if necessary
				 */
				String variable = entry.getParameter().getCTMRepresentation();
				while (variables.contains(variable)) {
					variable = entry.getParameter().getCTMRepresentation()
							+ Integer.toString(count);
					count++;
				}
				/*
				 * set new variable name
				 */
				entry.setValueOrVariable(factory.getEntryFactory()
						.newVariableParam(variable));
				/*
				 * store variable name
				 */
				variables.add(variable);
				/*
				 * add entry to template
				 */
				template.add(entry);
			}
		}

		return template;
	}

	/**
	 * Method is called to detect templates as replacement of a
	 * association-block. Method is looking for frequently use of association
	 * items of the same type.
	 * 
	 * @param type
	 *            the association type which specified the instances to scan
	 * @return a set of detected templates
	 * @throws SerializerException
	 *             thrown if transformation failed
	 */
	public Set<Template> tryToDetectAssociationTemplates(Topic type)
			throws SerializerException {
		Set<Template> templates = new HashSet<Template>();

		/*
		 * storage of candidates
		 */
		Set<Candidate> candidates = new HashSet<Candidate>();
		/*
		 * iterate over all association items
		 */
		Collection<Association> associations = index.getAssociations(type);
		for (Association association : associations) {
			/*
			 * iterate over all roles
			 */
			for (Role role : association.getRoles()) {
				Candidate candidate = null;
				/*
				 * extract candidate from storage if exists
				 */
				for (Candidate c : candidates) {
					Role r = ((Role) c.construct);
					if (r.getType().equals(role.getType())) {
						candidate = c;
						break;
					}
				}
				if (candidate == null) {
					candidate = new Candidate();
					candidate.construct = role;
				}
				/*
				 * increment count of the usage of this entry
				 */
				candidate.count++;
				candidates.add(candidate);
			}
		}
		/*
		 * generate a template from given candidates by using the threshold
		 */
		Template template = candidatesToTemplate(type, candidates, templates
				.size());
		templates.add(template);
		return templates;
	}

	/**
	 * Method to convert the relevant candidates to a template.
	 * 
	 * @param type
	 *            the association type which specified the instances to scan
	 * @param candidates
	 *            the set of candidates
	 * @param total
	 *            the number of scanned entries
	 * @return the generated template
	 * @throws SerializerException
	 *             thrown if transformation failed
	 */
	private Template candidatesToTemplate(Topic type,
			Set<Candidate> candidates, long total) throws SerializerException {

		String identifier = writer.getCtmIdentity().getMainIdentifier(
				writer.getProperties(), type).getIdentifier();

		/*
		 * create template identifier
		 */
		/*
		 * is prefixed IRI
		 */
		if (identifier.contains(":")) {
			identifier = identifier.substring(identifier.lastIndexOf(":") + 1);
		}
		/*
		 * is slash contained
		 */
		if (identifier.contains("/")) {
			identifier = identifier.substring(identifier.lastIndexOf("/") + 1);
		}

		/*
		 * is hash contained
		 */
		if (identifier.contains("#")) {
			identifier = identifier.substring(identifier.lastIndexOf("#") + 1);
		}
		/*
		 * create a template for the given type
		 */
		Template template = writer.getFactory().newTemplate(
				"template-association-" + identifier);

		Set<RoleEntry> roleEntries = new HashSet<RoleEntry>();

		Set<String> variables = new HashSet<String>();

		/*
		 * iterate over all occurrence-candidates
		 */
		for (Candidate candidate : candidates) {
			/*
			 * check frequency against threshold
			 */
			if ((float) candidate.count / (float) total >= threshold) {
				/*
				 * create new role-template-entry by construct
				 */
				RoleEntry entry = RoleEntry.buildFromConstruct(writer,
						(Role) candidate.construct);
				/*
				 * check if variable name is unique and try to unify variable
				 * name if necessary
				 */
				int count = 2;
				String variable = entry.getParameterAsString();
				while (variables.contains(variable)) {
					variable = entry.getParameterAsString()
							+ Integer.toString(count);
					count++;
				}
				/*
				 * set new variable name
				 */
				entry.setTopicOrVariable(new ParamFactory()
						.newVariableParam(variable));
				/*
				 * store variable name
				 */
				variables.add(variable);
				/*
				 * add entry to entry list
				 */
				roleEntries.add(entry);
			}
		}

		/*
		 * add new association-template-entry
		 */
		template.add(writer.getFactory().getEntryFactory().newAssociationEntry(
				type, roleEntries.toArray(new RoleEntry[0])));

		return template;
	}

	/**
	 * Internal POJO to represent a candidate
	 * 
	 * @author Sven Krosse
	 * @email krosse@informatik.uni-leipzig.de
	 * 
	 */
	class Candidate {
		/**
		 * the construct which represent the entry of the instance
		 */
		Construct construct;
		/**
		 * the frequency of usage of this entry
		 */
		int count;
	}

}
