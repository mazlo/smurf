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
		
		//Patterns for country code
		// PatternClass patternClass = new PatternClass( PatternType.CODE );
		// patternClass.add( Pattern.compile( "[A-Z][A-Z]", 0.6 ) );
		// patternClass.add( Pattern.compile( "[a-z]{2}", 0.3 ) );
		// patternClass.add( Pattern.compile( "[A-Z]{2}[A-Z0-9]{0,3}", 0.5 ) );
		
		//Patterns for date
		// PatternClass patternClass = new PatternClass( PatternType.CODE );
		// patternClass.add( Pattern.compile( "[0-2][0-9]{3}", 0.7 ) );
		// patternClass.add( Pattern.compile( "[0-9]{4}-[0-9]{2}-[0-9]{2}", 0.9
		// ) );
		// patternClass.add( Pattern.compile( "[0-9]{2}/[0-3][0-9]/[0-9]{2}",
		// 0.85 ) );
				
		
		//Patterns for gender
		PatternClass patternClass = new PatternClass( PatternType.CODE );
		patternClass.add( Pattern.compile( "[F|M|T|N|U]", 0.9 ) );
		patternClass.add( Pattern.compile( "[a-z]{3}-[A-Z]", 0.5 ) );
		patternClass.add( Pattern.compile( "[A-Za-z]{6}", 0.1 ) );
		patternClass.add( Pattern.compile( "Female", 0.99 ) );
		
		// Patterns for Age Groups
		// PatternClass patternClass = new PatternClass( PatternType.CODE );
		// patternClass.add( Pattern.compile( "[0-9]{1,2}-[0-9]{1,2}", 0.6 ) );
		// patternClass.add( Pattern.compile( "[A-Z][0-9]{1,2}-[0-9]{1,2}", 0.5
		// ) );
		// patternClass.add( Pattern.compile( "[A-Z]_[A-Z]{2}[0-9]{2}", 0.5 ) );
		
	
		
		// 1.1 Eurostat und data.gov_325 (=> same as 2.2)
		// RegularExpressionMatcher matcher = new RegularExpressionMatcher( new
		// File( "data/demo_r_d2janoecd_withschema.owl" ), new File(
		// "data/data-325.rdf" ), patternClass );
		
		// 1.2 data.gov_325 und iimb
		//RegularExpressionMatcher matcher = new RegularExpressionMatcher( new File( "data/data-325.rdf" ), new File( "data/onto.owl" ), patternClass );
		
		// 1.3
		//RegularExpressionMatcher matcher = new RegularExpressionMatcher( new File( "data/data-325.rdf" ), new File( "data/data-307-excerpt.rdf" ), patternClass );
		
		// 1.4
		//RegularExpressionMatcher matcher = new RegularExpressionMatcher( new File( "data/population-scotland_withscheme.rdf" ), new File( "data/onto.owl" ), patternClass );
		
		//1.5 und 1.6
		//RegularExpressionMatcher matcher = new RegularExpressionMatcher( new File( "data/population-scotland_withscheme.rdf" ), new File( "data/data-307-excerpt.rdf" ), patternClass );
		
		
		//2.1
		//RegularExpressionMatcher matcher = new RegularExpressionMatcher( new File( "data/1304471908_withschemme.rdf" ), new File( "data/demo_r_d2janoecd_withschema.owl" ), patternClass );
		
		//2.2 SAME AS 1.1 BUT define different pattern!!!
		
		//2.3 
		//RegularExpressionMatcher matcher = new RegularExpressionMatcher( new File( "data/data-1915-excerpt.rdf" ), new File( "data/data-325.rdf" ), patternClass );
		
		//2.4
		RegularExpressionMatcher matcher = new RegularExpressionMatcher( new File( "data/onto.owl" ), new File( "data/1304471908_withschemme.rdf" ), patternClass );
		
		//2.5
		//RegularExpressionMatcher matcher = new RegularExpressionMatcher( new File( "data/onto.owl" ), new File( "data/demo_r_d2janoecd_withschema.owl" ), patternClass );
		
		//2.6
		//RegularExpressionMatcher matcher = new RegularExpressionMatcher( new File( "data/demo_r_d2janoecd_withschema.owl" ), new File( "data/1304471908_withschemme.rdf" ), patternClass );
		
		//2.7
		// RegularExpressionMatcher matcher = new RegularExpressionMatcher( new
		// File( "data/demo_r_d2janoecd_withschema.owl" ), new File(
		// "data/population-scotland_withscheme.rdf" ), patternClass );
		
		//2.8
//		RegularExpressionMatcher matcher = new RegularExpressionMatcher( new File( "data/1304471908_withschemme.rdf" ), new File( "data/population-scotland_withscheme.rdf" ), patternClass );
		
		
		
		
		
		
		//RegularExpressionMatcher matcher = new RegularExpressionMatcher( new File( "data/data-1915-excerpt.rdf" ), new File( "data/teicp000.owl" ), patternClass );

		Assert.assertNotNull( matcher.getPatternClass() );
		//Assert.assertEquals( 2, matcher.getPatternClass().size() );

		Set<SchemaMatch> matchings = matcher.getMatchings();

		Assert.assertNotNull( matchings );
		//Assert.assertEquals( 2, matchings.size() );

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
