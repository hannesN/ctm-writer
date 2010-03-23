package de.topicmapslab.ctm.writer.templates.entry.param;

import java.util.regex.Pattern;

import de.topicmapslab.ctm.writer.exception.InvalidNamePatternException;

public class VariableParam implements IEntryParam {

	private static Pattern pattern = Pattern.compile("\\$.+");

	private final String name;

	public VariableParam(final String name) throws InvalidNamePatternException {
		if (!pattern.matcher(name).matches()) {
			throw new InvalidNamePatternException(name, pattern.toString());
		}
		this.name = name;
	}

	@Override
	public String getCTMRepresentation() {
		return name;
	}

}
