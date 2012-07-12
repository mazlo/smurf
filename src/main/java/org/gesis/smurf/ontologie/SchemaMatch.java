package org.gesis.smurf.ontologie;

import com.hp.hpl.jena.rdf.model.Property;

public class SchemaMatch {

	// TODO zl Probability to be implemented
	private double probability = 1.0d;

	private Property leftMatch;
	private Property rightMatch;

	public SchemaMatch( Property leftMatch, Property rightMatch, double probability ) {
		this.leftMatch = leftMatch;
		this.rightMatch = rightMatch;
		this.probability = probability;
	}

	public double getProbability() {
		return probability;
	}

	public void setProbability( double probability ) {
		this.probability = probability;
	}

	public Property getLeftMatch() {
		return leftMatch;
	}

	public Property getRightMatch() {
		return rightMatch;
	}

}
