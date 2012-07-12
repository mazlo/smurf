package org.gesis.smurf.regex.util;

import org.gesis.smurf.regex.Pattern;



public class PatternComparatorFactory {

	private static PatternStringComparator patternStringComparator = null;
	private static RegexPartComparator regexPartComparator = null;
	private static PatternInstanceLevenshteinComparator patternInstanceComparator = null;

	// TODO zl implement comparator which returns true if all strings matched by the first
	// regexp are also matched by the second.

	public static PatternComparator<Pattern> getPatternStringComparator() {
		if ( patternStringComparator == null )
			patternStringComparator = new PatternStringComparator();

		return patternStringComparator;
	}

	public static RegexPartComparator getRegexPartComparator() {
		if ( regexPartComparator == null )
			regexPartComparator = new RegexPartComparator();

		return regexPartComparator;
	}

	public static PatternComparator<String> getPatternInstanceComparator() {
		if ( patternInstanceComparator == null )
			patternInstanceComparator = new PatternInstanceLevenshteinComparator();

		return patternInstanceComparator;
	}
}
