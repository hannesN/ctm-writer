///*
// * Copyright: Copyright 2010 Topic Maps Lab, University of Leipzig. http://www.topicmapslab.de/    
// * License:   Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0.html
// * 
// * @author Sven Krosse
// * @email krosse@informatik.uni-leipzig.de
// *
// */
//package de.topicmapslab.ctm.writer.templates;
//
//import java.util.HashSet;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Set;
//
//import org.tmapi.core.Association;
//import org.tmapi.core.Topic;
//
//import de.topicmapslab.ctm.writer.core.serializer.ISerializer;
//import de.topicmapslab.ctm.writer.exception.SerializerException;
//import de.topicmapslab.ctm.writer.templates.entry.base.IEntry;
//import de.topicmapslab.ctm.writer.utility.CTMBuffer;
//
///**
// * Serializer implementation for template-invocations.
// * 
// * @author Sven Krosse
// * @email krosse@informatik.uni-leipzig.de
// * 
// */
//public class TemplateInvocationSerializer implements ISerializer<Topic> {
//
//	/**
//	 * Method to convert the given construct to its specific CTM string. The
//	 * result should be written to the given output buffer.
//	 * 
//	 * @param template
//	 *            the template called by the invocation
//	 * @param topic
//	 *            the topic to serialize
//	 * @param buffer
//	 *            the output buffer
//	 * @return <code>true</code> if new content was written into buffer,
//	 *         <code>false</code> otherwise
//	 * @throws SerializerException
//	 *             Thrown if serialization failed.
//	 */
//	public static Set<Object> serialize(Template template, Topic topic,
//			CTMBuffer buffer) throws SerializerException {
//
////		if (!template.isAdaptiveFor(topic)) {
////			throw new SerializerException(
////					"template is not adaptive for given topic.");
////		}
//
//		Set<Object> affectedConstructs = new HashSet<Object>();
//
//		final List<String> arguments = new LinkedList<String>();
//
//		/*
//		 * extract all arguments for template-invocation call
//		 */
//		for (IEntry entry : template.getEntries()) {
//			/*
//			 * static template entry
//			 */
//			if (!entry.isDependentFromVariable()) {
//				continue;
//			}
//			arguments.addAll(entry.extractArguments(topic, topic,
//					affectedConstructs));
//		}
//		/*
//		 * redirect to TemplateSerializer
//		 */
//		TemplateSerializer.serialize(template, buffer, arguments
//				.toArray(new String[0]));
//		buffer.appendTailLine();
//		return affectedConstructs;
//	}
//
//	/**
//	 * Method to convert the given association to its specific CTM string
//	 * representing the template-invocation call. The result should be written
//	 * to the given output buffer.
//	 * 
//	 * @param template
//	 *            the template called by the invocation
//	 * @param association
//	 *            the association to serialize
//	 * @param buffer
//	 *            the output buffer
//	 * @return <code>true</code> if new content was written into buffer,
//	 *         <code>false</code> otherwise
//	 * @throws SerializerException
//	 *             Thrown if serialization failed.
//	 */
//	public static Set<Object> serialize(Template template, Association association,
//			CTMBuffer buffer) throws SerializerException {
//
////		if (!template.isAdaptiveFor(association)) {
////			throw new SerializerException(
////					"template is not adaptive for given association.");
////		}
//
//		Set<Object> affectedConstructs = new HashSet<Object>();
//
//		final List<String> arguments = new LinkedList<String>();
//
//		/*
//		 * extract all arguments for template-invocation call
//		 */
//		for (IEntry entry : template.getEntries()) {
//			/*
//			 * static template entry
//			 */
//			if (!entry.isDependentFromVariable()) {
//				continue;
//			}
//			arguments.addAll(entry.extractArguments(association.getType(),
//					association, affectedConstructs));
//			if (template.getVariables().size() >= arguments.size()) {
//				break;
//			}
//		}
//
//		/*
//		 * redirect to TemplateSerializer
//		 */
//		TemplateSerializer.serialize(template, buffer, arguments
//				.toArray(new String[0]));
//		return affectedConstructs;
//	}
//
// }
