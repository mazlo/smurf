package org.gesis.smurf.regex;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.gesis.smurf.regex.PatternLearner;
import org.junit.Test;

public class PatternLearnerTest {

	@Test
	public void testJavaPatternMatcher() {
		Pattern pattern = Pattern.compile( "\\d{4}\\s[A-Za-z]{3}-\\d{2}" );
		Matcher matcher = pattern.matcher( "2004 Jan-13" );

		assertTrue( matcher.matches() );
	}

	@Test
	public void testLearnerDezimal() {
		String value = "2004";
		String learnedPattern = PatternLearner.learnFromToken( value );

		assertEquals( "\\d{4}", learnedPattern );
	}

	@Test
	public void testLearnerLiteral() {
		String value = "SMURF";
		String learnedPattern = PatternLearner.learnFromToken( value );

		assertEquals( "[A-Za-z]{5}", learnedPattern );
	}

	@Test
	public void testLearnerSpecialCharacter() {
		String value;
		String learnedPattern;

		value = "-";
		learnedPattern = PatternLearner.learnFromToken( value );
		assertEquals( "-", learnedPattern );

		value = "/";
		learnedPattern = PatternLearner.learnFromToken( value );
		assertEquals( "/", learnedPattern );

		value = "_";
		learnedPattern = PatternLearner.learnFromToken( value );
		assertEquals( "_", learnedPattern );
	}

	@Test
	public void testTokenizerDate() {
		String[] tokens = PatternLearner.tokenize( "12.20.2004" );
		assertEquals( 5, tokens.length );
		assertEquals( "20", tokens[2] );
		assertEquals( "2004", tokens[4] );
	}

	@Test
	public void testTokenizerDateLiteral() {
		String[] tokens = PatternLearner.tokenize( "27. July, 2012" );
		assertEquals( 7, tokens.length );
		assertEquals( " ", tokens[2] );
		assertEquals( "July", tokens[3] );
		assertEquals( "2012", tokens[6] );
	}

	@Test
	public void testPatternLearner() {
		String string;
		String learnedPattern;

		string = "27. July, 2012";
		learnedPattern = PatternLearner.learnPatternFor( string );
		assertEquals( "\\d{2}.\\s[A-Za-z]{4},\\s\\d{4}", learnedPattern );

		string = "July 5th, 2012";
		learnedPattern = PatternLearner.learnPatternFor( string );
		assertEquals( "[A-Za-z]{4}\\s\\d[A-Za-z]{2},\\s\\d{4}", learnedPattern );
	}
}
