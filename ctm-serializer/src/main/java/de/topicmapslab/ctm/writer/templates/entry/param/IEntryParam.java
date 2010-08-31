package de.topicmapslab.ctm.writer.templates.entry.param;

/**
 * Interface definition representing a template entry parameter.
 * 
 * @author Sven Krosse
 * @email krosse@informatik.uni-leipzig.de
 */
public interface IEntryParam {

	/**
	 * Returns the CTM compatible Representation for the current parameter.
	 * 
	 * @return the CTM pattern
	 */
	public String getCTMRepresentation();

}
