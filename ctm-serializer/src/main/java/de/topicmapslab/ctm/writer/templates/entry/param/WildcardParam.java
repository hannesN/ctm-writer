package de.topicmapslab.ctm.writer.templates.entry.param;

public class WildcardParam implements IEntryParam {

	private final String name;

	protected WildcardParam(final String name) {
		this.name = "?" + name;
	}

	public String getCTMRepresentation() {
		return name;
	}

}
