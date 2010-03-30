package de.topicmapslab.ctm.writer.templates.entry.param;

import org.tmapi.core.Topic;;

public class TopicTypeParam implements IEntryParam {

	private final Topic topic;

	protected TopicTypeParam(final Topic topic) {
		this.topic = topic;
	}

	public String getCTMRepresentation() {
		return topic.toString();
	}

	public Topic getTopic() {
		return topic;
	}

}
