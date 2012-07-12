package org.gesis.smurf.ontologie;

import org.apache.commons.lang.StringUtils;

import com.hp.hpl.jena.rdf.model.Property;

/**
 * A Match represents a match of a pattern to a literal of a property. Since we
 * do instance-based schema matching, we save the Property in this class. In
 * connection with a weighting.
 * 
 * @author matthaeus
 * 
 */
public class Match implements Comparable<Match> {

	private Property property;

	private double weight;

	public Match( Property property, double weight ) {
		super();
		this.property = property;
		this.weight = weight;
	}

	public Property getProperty() {
		return property;
	}

	public void setProperty( Property property ) {
		this.property = property;
	}

	public void addWeight( double weight ) {
		this.weight += weight;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight( double weight ) {
		this.weight = weight;
	}

	public int compareTo( Match otherMatch ) {
		if ( weight == 0 )
			// other object is bigger
			return -1;

		if ( otherMatch == null || otherMatch.getWeight() == 0 )
			// this object is bigger
			return 1;

		if ( weight < otherMatch.getWeight() )
			return -1;

		if ( weight > otherMatch.getWeight() )
			return 1;

		return 0;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( property == null ) ? 0 : property.hashCode() );
		return result;
	}

	@Override
	public boolean equals( Object obj ) {
		if ( this == obj )
			return true;

		if ( obj == null )
			return false;

		if ( getClass() != obj.getClass() )
			return false;

		Match other = (Match) obj;
		if ( property == null ) {
			if ( other.property != null )
				return false;
		} else if ( !StringUtils.equals( property.toString(), other.property.toString() ) )
			return false;

		return true;
	}

}
