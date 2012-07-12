package org.gesis.smurf.regex;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import net.vidageek.regex.RegexGenerator;

import org.gesis.smurf.regex.util.PatternComparatorFactory;
import org.junit.Assert;
import org.junit.Test;

import com.sharomank.regex.parser.AbstractRegexPart;
import com.sharomank.regex.parser.RegexParser;

public class PatternComparatorTest {

	/**
	 * Tests the string equality
	 */
	@Test
	public void testStringEqualityComparator() {
		boolean isEqual;

		// equality
		isEqual = PatternComparatorFactory.getPatternStringComparator().isEqual( Pattern.compile( "[A-Za-z]\\d", 0.5 ), Pattern.compile( "[A-Za-z]\\d", 0.4 ) );
		assertTrue( isEqual );

		// unequality
		isEqual = PatternComparatorFactory.getPatternStringComparator().isEqual( Pattern.compile( "[A-Za-z]\\s", 0.4 ), Pattern.compile( "[0-9]\\d", 0.4 ) );
		assertFalse( isEqual );
	}

	/**
	 * Tests the quantifiers only.
	 */
	@Test
	public void testRegexPartComparator_quantifierEqual() {
		List<AbstractRegexPart> parts1;
		List<AbstractRegexPart> parts2;

		// exactly equal quantifiers
		parts1 = RegexParser.parse( "{2}" );
		parts2 = RegexParser.parse( "{2}" );

		assertTrue( PatternComparatorFactory.getRegexPartComparator().isEqual( parts1.get( 0 ), parts2.get( 0 ) ) );

		// in-between subset
		parts1 = RegexParser.parse( "{1,5}" );
		parts2 = RegexParser.parse( "{3}" );

		assertTrue( PatternComparatorFactory.getRegexPartComparator().isEqual( parts1.get( 0 ), parts2.get( 0 ) ) );
		assertTrue( PatternComparatorFactory.getRegexPartComparator().isEqual( parts2.get( 0 ), parts1.get( 0 ) ) );

		// subset, with lower range equality
		parts1 = RegexParser.parse( "{1,3}" );
		parts2 = RegexParser.parse( "{1}" );

		assertTrue( PatternComparatorFactory.getRegexPartComparator().isEqual( parts1.get( 0 ), parts2.get( 0 ) ) );
		assertTrue( PatternComparatorFactory.getRegexPartComparator().isEqual( parts2.get( 0 ), parts1.get( 0 ) ) );
	}

	/**
	 * Tests the quantifiers, unequality
	 */
	@Test
	public void testRegexPartComparator_quantifierNotEqual() {
		List<AbstractRegexPart> parts1;
		List<AbstractRegexPart> parts2;

		// unequal quantifiers
		parts1 = RegexParser.parse( "{1}" );
		parts2 = RegexParser.parse( "{2}" );

		assertFalse( PatternComparatorFactory.getRegexPartComparator().isEqual( parts1.get( 0 ), parts2.get( 0 ) ) );
		assertFalse( PatternComparatorFactory.getRegexPartComparator().isEqual( parts2.get( 0 ), parts1.get( 0 ) ) );

		// out of range
		parts1 = RegexParser.parse( "{1,3}" );
		parts2 = RegexParser.parse( "{4}" );

		assertFalse( PatternComparatorFactory.getRegexPartComparator().isEqual( parts1.get( 0 ), parts2.get( 0 ) ) );
		assertFalse( PatternComparatorFactory.getRegexPartComparator().isEqual( parts2.get( 0 ), parts1.get( 0 ) ) );
	}

	@Test
	public void testRegexPartComparator() {
		List<AbstractRegexPart> parts1 = RegexParser.parse( "\\d{2}\\s[A-Za-z]{3}" );
		List<AbstractRegexPart> parts2 = RegexParser.parse( "\\d{1,3}\\s[A-Za-z]{3}" );

		for ( int i = 0; i < parts1.size(); i++ ) {
			AbstractRegexPart part1 = parts1.get( i );
			AbstractRegexPart part2 = parts2.get( i );

			assertTrue( PatternComparatorFactory.getRegexPartComparator().isEqual( part1, part2 ) );
		}
	}

	@Test
	public void testLevenshteinDistance() {
		String instance1 = RegexGenerator.generateMatchingWord( "\\d{2}[a-zA-Z]{2}" );
		String instance2 = RegexGenerator.generateMatchingWord( "\\d{2}[a-zA-Z]{2}" );

		Assert.assertEquals( Boolean.TRUE, PatternComparatorFactory.getPatternInstanceComparator().isEqual( instance1, instance2 ) );

		// TODO zl PatternInstanceComparator add more tests
	}
}
