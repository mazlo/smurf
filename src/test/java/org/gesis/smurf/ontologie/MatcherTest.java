package org.gesis.smurf.ontologie;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.gesis.smurf.regex.Pattern;
import org.gesis.smurf.regex.PatternClass;
import org.gesis.smurf.regex.PatternType;
import org.junit.Assert;
import org.junit.Test;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;

public class MatcherTest {

	@Test
	public void testReadModel() {
		Model model1 = MatchingAlgorithm.readModelFromFile( "data/data-1915-excerpt.rdf" );

		Assert.assertNotNull( model1 );
		Assert.assertEquals( 2405, model1.listStatements().toList().size() );
	}

	@Test
	public void testFindMatchings() {
		// the patterns
		PatternClass patternClass = new PatternClass( PatternType.NUMERIC );
		patternClass.add( Pattern.compile( "\\d+(\\.\\d+)?", 0.9 ) );

		Model model1 = MatchingAlgorithm.readModelFromFile( "data/data-1915-excerpt.rdf" );
		Assert.assertNotNull( model1 );

		Set<Match> matchings = MatchingAlgorithm.findMatchings( patternClass, model1.listStatements() );
		Assert.assertNotNull( matchings );
		Assert.assertTrue( !matchings.isEmpty() );
		Assert.assertEquals( 2, matchings.size() );
		Assert.assertEquals( "year", matchings.iterator().next().getProperty().getLocalName() );
	}

	@Test
	public void testGetMatchings() {
		// the patterns
		PatternClass patternClass = new PatternClass( PatternType.CODE );
		patternClass.add( Pattern.compile( "[a-zA-Z][a-zA-Z]", 0.9 ) );
		patternClass.add( Pattern.compile( "[a-zA-Z]{5}", 0.4 ) );

		RegularExpressionMatcher matcher = new RegularExpressionMatcher( new File( "data/data-1915-excerpt.rdf" ), new File( "data/teicp000.owl" ), patternClass );

		Assert.assertNotNull( matcher.getPatternClass() );
		Assert.assertEquals( 2, matcher.getPatternClass().size() );

		Set<SchemaMatch> matchings = matcher.getMatchings();

		Assert.assertNotNull( matchings );
		Assert.assertEquals( 4, matchings.size() );

		SchemaMatch schemaMatch = matchings.iterator().next();
		Property leftMatch = schemaMatch.getLeftMatch();
		Property rightMatch = schemaMatch.getRightMatch();

		Assert.assertNotNull( leftMatch );
		Assert.assertNotNull( rightMatch );
		// Assert.assertEquals( "statecode", leftMatch.getLocalName() );

		for ( SchemaMatch match : matchings ) {
			System.out.println( match.getLeftMatch() + " and " + match.getRightMatch() + ": " + match.getProbability() );
		}
	}

	@Test
	public void testPatternLearner() {
		// input
		List<PatternClass> patternClasses = new ArrayList<PatternClass>();
	}
}
