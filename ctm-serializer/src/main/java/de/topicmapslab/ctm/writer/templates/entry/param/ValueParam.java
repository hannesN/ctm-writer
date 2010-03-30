package de.topicmapslab.ctm.writer.templates.entry.param;

public class ValueParam implements IEntryParam {

	private final String value;
	
	protected ValueParam(final String value){
		this.value = value;
	}
	
	public String getCTMRepresentation() {
		return value;
	}

}
