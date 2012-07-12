package org.gesis.smurf.ontologie;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.gesis.smurf.regex.PatternClass;

import com.hp.hpl.jena.rdf.model.Model;

/**
 * Class to represent a simple matcher, based on regular expressions.
 * 
 * @author matthaeus
 * 
 */
public class RegularExpressionMatcher {

	private Model ontology1;
	private Model ontology2;
	private PatternClass patternClass;

	public RegularExpressionMatcher(Model ontology1, Model ontology2, PatternClass patternClass) {
		this.ontology1 = ontology1;
		this.ontology2 = ontology2;
		this.patternClass = patternClass;
	}

	public RegularExpressionMatcher(File ontology1, File ontology2, PatternClass patternClass) {
		if ( ontology1 == null )
			throw new IllegalArgumentException( "ontology1 was null" );
		if ( ontology2 == null )
			throw new IllegalArgumentException( "ontology2 was null" );

		this.ontology1 = MatchingAlgorithm.readModelFromFile( ontology1.getAbsolutePath() );
		this.ontology2 = MatchingAlgorithm.readModelFromFile( ontology2.getAbsolutePath() );

		this.patternClass = patternClass;
	}

	public Model getOntology1() {
		return ontology1;
	}

	public void setOntology1( Model ontology1 ) {
		this.ontology1 = ontology1;
	}

	public Model getOntology2() {
		return ontology2;
	}

	public void setOntology2( Model ontology2 ) {
		this.ontology2 = ontology2;
	}

	public PatternClass getPatternClass() {
		return patternClass;
	}

	public void setPatternClass( PatternClass patternClass ) {
		this.patternClass = patternClass;
	}

	/**
	 * Constructs a set of SchemaMatch-objects, where each represents a match
	 * between two different schema elements.
	 * 
	 * @return
	 */
	public Set<SchemaMatch> getMatchings() {
		// find the set of matchings in both models
		MatchingSet matchingsInModel1 = MatchingAlgorithm.findMatchings( this.patternClass, this.ontology1.listStatements() );
		MatchingSet matchingsInModel2 = MatchingAlgorithm.findMatchings( this.patternClass, this.ontology2.listStatements() );

		Set<SchemaMatch> matchings = new HashSet<SchemaMatch>();

		// croxx product
		for ( Match leftMatch : matchingsInModel1 ) {
			for ( Match rightMatch : matchingsInModel2 ) {
				double weighting = ( leftMatch.getWeight() / matchingsInModel1.getFrequencyOf( leftMatch.getProperty() ) ) * ( rightMatch.getWeight() / matchingsInModel2.getFrequencyOf( rightMatch.getProperty() ) );
				matchings.add( new SchemaMatch( leftMatch.getProperty(), rightMatch.getProperty(), weighting ) );
			}
		}

		return matchings;
	}

}
