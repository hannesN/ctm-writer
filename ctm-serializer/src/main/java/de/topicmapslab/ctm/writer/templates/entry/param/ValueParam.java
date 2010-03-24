package de.topicmapslab.ctm.writer.templates.entry.param;

public class ValueParam implements IEntryParam {

	private final String value;
	
	public ValueParam(final String value){
		this.value = value;
	}
	
	@Override
	public String getCTMRepresentation() {
		return value;
	}

}
