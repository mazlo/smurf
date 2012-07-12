package org.gesis.smurf.regex;

import net.vidageek.regex.RegexGenerator;

import org.junit.Test;

public class PatternGeneratorTest {

	@Test
	public void testPatternGenerator() {
		String word = RegexGenerator.generateMatchingWord( "[az]" );

		System.out.println( word );
	}
}
