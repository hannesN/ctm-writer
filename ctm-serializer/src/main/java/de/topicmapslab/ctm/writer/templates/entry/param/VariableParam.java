package de.topicmapslab.ctm.writer.templates.entry.param;

public class VariableParam implements IEntryParam {

	private final String name;

	protected VariableParam(final String name) {
		this.name = "$" + name;
	}

	public String getCTMRepresentation() {
		return name;
	}

}
