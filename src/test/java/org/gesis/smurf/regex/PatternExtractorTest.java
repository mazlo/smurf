package org.gesis.smurf.regex;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

public class PatternExtractorTest {

	@Test
	public void testLiteralsOnly() {

		// the literals of the statements
		List<String> literals = new ArrayList<String>();
		literals.add( "NZ" );
		literals.add( "OS" );
		literals.add( "TZ" );
		literals.add( "OP" );
		literals.add( "JS" );

		PatternExtractor extractor = new PatternExtractor();
		extractor.learnFrom( literals );

		Collection<Pattern> extractedPatterns = extractor.getExtractedPatterns();

		// should be only one
		Assert.assertNotNull( extractedPatterns );
		Assert.assertEquals( 1, extractedPatterns.size() );

		// get the first
		Pattern pattern = extractedPatterns.iterator().next();

		Assert.assertNotNull( pattern );
		Assert.assertEquals( "[A-Za-z][A-Za-z]", pattern.getPattern().pattern() );
		Assert.assertEquals( 1.0d, pattern.getWeight() );
	}

	@Test
	public void testDigitsOnly() {

		// the literals of the statements
		List<String> literals = new ArrayList<String>();
		literals.add( "02" );
		literals.add( "18" );
		literals.add( "39" );
		literals.add( "42" );
		literals.add( "88" );

		PatternExtractor extractor = new PatternExtractor();
		extractor.learnFrom( literals );

		Collection<Pattern> extractedPatterns = extractor.getExtractedPatterns();

		// should be only one
		Assert.assertNotNull( extractedPatterns );
		Assert.assertEquals( 1, extractedPatterns.size() );

		// get the first
		Pattern pattern = extractedPatterns.iterator().next();

		Assert.assertNotNull( pattern );
		Assert.assertEquals( "\\d{2}", pattern.getPattern().pattern() );
		Assert.assertEquals( 1.0d, pattern.getWeight() );
	}

	@Test
	public void testLiteralWeights() {
		// the literals of the statements
		List<String> literals = new ArrayList<String>();
		literals.add( "20" ); // a digit
		literals.add( "ZL" ); // an alphanumeric string
		literals.add( "31" ); // a digit
		literals.add( "JS" ); // an alphanumeric string

		PatternExtractor extractor = new PatternExtractor();
		extractor.learnFrom( literals );

		Collection<Pattern> extractedPatterns = extractor.getExtractedPatterns();

		// two different patterns
		Assert.assertNotNull( extractedPatterns );
		Assert.assertEquals( 2, extractedPatterns.size() );

		// get the first
		Iterator<Pattern> patternIterator = extractedPatterns.iterator();
		Pattern pattern = patternIterator.next();

		Assert.assertNotNull( pattern );
		Assert.assertEquals( "[A-Za-z][A-Za-z]", pattern.getPattern().pattern() );
		Assert.assertEquals( 0.5d, pattern.getWeight() );

		// get the second
		pattern = patternIterator.next();
		Assert.assertEquals( "\\d{2}", pattern.getPattern().pattern() );
		Assert.assertEquals( 0.5d, pattern.getWeight() );

	}

	@Test
	public void testExtractedPatternSimilarity() {
		List<PatternClass> patternClasses = new ArrayList<PatternClass>();
		patternClasses.add( new PatternClass( PatternType.CODE ).addPattern( new Pattern( java.util.regex.Pattern.compile( "[A-Za-z][A-Za-z]" ), 0.4d ) ) );
		patternClasses.add( new PatternClass( PatternType.DATE ).addPattern( new Pattern( java.util.regex.Pattern.compile( "\\d{2}" ), 0.4d ) ) );

		// the literals of the statements
		List<String> literals = new ArrayList<String>();
		literals.add( "20" ); // a digit
		literals.add( "ZL" ); // an alphanumeric string
		literals.add( "31" ); // a digit
		literals.add( "JS" ); // an alphanumeric string

		PatternExtractor extractor = new PatternExtractor();
		extractor.learnFrom( literals );

		Collection<Pattern> extractedPatterns = extractor.getExtractedPatterns();
		Assert.assertEquals( 2, extractedPatterns.size() );

		List<PatternClass> newPatternClasses = extractor.compareAndAddPattern( patternClasses, extractedPatterns );

		Assert.assertNotNull( newPatternClasses );
		Assert.assertEquals( 2, newPatternClasses.size() );
	}

	@Test
	public void testExtractedPatternUnsimilarity() {
		List<PatternClass> patternClasses = new ArrayList<PatternClass>();
		patternClasses.add( new PatternClass( PatternType.CODE ).addPattern( new Pattern( java.util.regex.Pattern.compile( "[A-Za-z][A-Za-z][A-Za-z]" ), 0.4d ) ) );
		patternClasses.add( new PatternClass( PatternType.DATE ).addPattern( new Pattern( java.util.regex.Pattern.compile( "\\d{2}" ), 0.4d ) ) );

		// the literals of the statements
		List<String> literals = new ArrayList<String>();
		literals.add( "201" ); // a digit
		literals.add( "ZL" ); // an alphanumeric string
		literals.add( "312" ); // a digit
		literals.add( "JS" ); // an alphanumeric string

		PatternExtractor extractor = new PatternExtractor();
		extractor.learnFrom( literals );

		Collection<Pattern> extractedPatterns = extractor.getExtractedPatterns();
		Assert.assertEquals( 2, extractedPatterns.size() );

		List<PatternClass> newPatternClasses = extractor.compareAndAddPattern( patternClasses, extractedPatterns );

		Assert.assertNotNull( newPatternClasses );
		Assert.assertEquals( 3, newPatternClasses.size() );

		Assert.assertEquals( 2, newPatternClasses.get( 2 ).size() );
	}
}
