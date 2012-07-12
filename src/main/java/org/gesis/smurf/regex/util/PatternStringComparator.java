package org.gesis.smurf.regex.util;

import org.gesis.smurf.regex.Pattern;


/**
 * The easiest and probably the weakest comparator for regular expressions. 
 * It simply compares the two strings on equality.
 * 
 * @author matthaeus
 * 
 */
public class PatternStringComparator implements PatternComparator<Pattern> {

	public int compare( Pattern pattern1, Pattern pattern2 ) {
		String string1 = pattern1.getPattern().pattern();
		String string2 = pattern2.getPattern().pattern();

		return string1.compareTo( string2 );
	}

	public boolean isEqual( Pattern source, Pattern toTarget ) {
		int i = compare(source, toTarget);
		
		if ( i == 0 )
			return true;
		else
			return false;
	}

}
