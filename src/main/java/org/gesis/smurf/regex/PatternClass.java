package org.gesis.smurf.regex;

import java.util.List;
import java.util.TreeSet;


/**
 * Basic-Class for representing a class of patterns for a special data type. 
 * For the data type <i>date</i> for instance, this might be used to represent
 * all possible patterns for dates, e.g. <i>20.05.2012</i>, <i>2012.Dec.01</i>, etc. 
 * 
 * @author matthaeus
 *
 */
public class PatternClass extends TreeSet<Pattern> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5566312729160405130L;

	private PatternType patternType;

	public static int sortBy = Pattern.SORT_DESCENDING;

	/**
	 * Basic constructor.
	 */
	public PatternClass(PatternType type) {
		this.patternType = type;
	}

	/**
	 * Provide a list of patterns during instantiation.
	 * @param patterns
	 */
	public PatternClass(List<Pattern> patterns) {
		if ( patterns != null )
			this.addAll( patterns );
	}

	/**
	 * Adds a pattern.
	 * 
	 * @param pattern
	 * @return
	 */
	public PatternClass addPattern( Pattern pattern ) {

		super.add( pattern );

		return this;
	}

	public PatternType getPatternType() {
		return patternType;
	}

	public void setPatternType( PatternType patternType ) {
		this.patternType = patternType;
	}

	public int getSortBy() {
		return sortBy;
	}

	public void setSortBy( int sortBy ) {
		PatternClass.sortBy = sortBy;
	}

}
