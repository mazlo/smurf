package org.gesis.smurf.regex;

import org.apache.commons.lang.StringUtils;


/**
 * Represents a regular expression pattern that also comes with a weight. A
 * Pattern always stands as a representator for a certain data type, e.g. a date
 * or country code. The weight of a pattern can be interpreted as, how likely it
 * is for a pattern to match a specific data type with high confidence.
 * 
 * @author matthaeus
 * 
 */
public class Pattern implements Comparable<Pattern> {

	public static final int SORT_ASCENDING = 0;
	public static final int SORT_DESCENDING = -1;

	private java.util.regex.Pattern pattern;
	private double weight = 0;

	public Pattern( java.util.regex.Pattern compiledPattern, double weight ) {
		this.pattern = compiledPattern;
		this.weight = weight;
	}

	public Pattern(String pattern, double weight) {
		this.pattern = java.util.regex.Pattern.compile( pattern );
		this.weight = weight;
	}

	public static Pattern compile( String regex, double weight ) {
		return new Pattern( java.util.regex.Pattern.compile( regex ), weight );
	}

	public java.util.regex.Pattern getPattern() {
		return pattern;
	}

	public void setPattern( java.util.regex.Pattern pattern ) {
		this.pattern = pattern;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight( double weight ) {
		this.weight = weight;
	}

	public int compareTo( Pattern o ) {
		if ( this.weight == 0 ) {
			if ( o == null )
				return 0;
			else
				return ( PatternClass.sortBy == Pattern.SORT_ASCENDING ? -1 : 1 );
		}

		if ( this.weight < o.weight )
			return ( PatternClass.sortBy == Pattern.SORT_ASCENDING ? -1 : 1 );
		else if ( this.weight > o.weight )
			return ( PatternClass.sortBy == Pattern.SORT_ASCENDING ? 1 : -1 );

		if ( !StringUtils.equals( this.pattern.pattern(), o.getPattern().pattern() ) )
			return -1;
		
		return 0;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( pattern == null ) ? 0 : pattern.hashCode() );
		return result;
	}

	@Override
	public boolean equals( Object obj ) {
		// TODO zl why is this method not used?? when inserting new pattern into
		// a hashset/treeset?
		if ( this == obj )
			return true;

		if ( obj == null )
			return false;

		if ( getClass() != obj.getClass() )
			return false;

		String other = ( (Pattern) obj ).getPattern().pattern();
		if ( pattern == null ) {
			if ( other != null )
				return false;
		} else if ( !pattern.pattern().equals( other ) )
			return false;

		return true;
	}

	@Override
	public String toString() {
		return "{" + pattern + ", " + weight + "}";
	}

}
