package org.gesis.smurf.regex.util;

import org.gesis.zl.util.LevenshteinDistance;

/**
 * Compares two instances of regular expression patterns and computes the edit
 * distance for them.
 * 
 * @author matthaeus
 * 
 */
public class PatternInstanceLevenshteinComparator implements PatternComparator<String> {

	public int compare( String pattern1, String pattern2 ) {

		int distance = LevenshteinDistance.compute( pattern1, pattern2 );

		if ( distance == 0 )
			// equal
			return 0;

		// TODO Levenshtein: implement threshold. when are the two patterns considered as equal??

		return -1;
	}

	public boolean isEqual( String source, String toTarget ) {
		int distance = compare( source, toTarget );

		if ( distance == 0 )
			// equal
			return true;

		return false;
	}

}
