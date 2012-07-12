package org.gesis.smurf.regex.util;

import org.apache.commons.lang.StringUtils;

import com.sharomank.regex.parser.AbstractRegexPart;
import com.sharomank.regex.parser.types.QuantifierGroup;

public class RegexPartComparator implements PatternComparator<AbstractRegexPart> {

	public int compare( AbstractRegexPart part1, AbstractRegexPart part2 ) {
		if ( StringUtils.equals( part1.getPart(), part2.getPart() ) )
			// string equality is given
			return 0;

		if ( part1 instanceof QuantifierGroup && part2 instanceof QuantifierGroup ) {
			return isSubsetRelation( (QuantifierGroup) part1, (QuantifierGroup) part2 );
		}

		return 0;
	}

	private int isSubsetRelation( QuantifierGroup part1, QuantifierGroup part2 ) {
		if ( part1.hasRange() && part2.hasRange() ) {
			// TODO
			return 0;
		} else if ( part1.hasRange() && !part2.hasRange() ) {
			// e.g. {2-4} =? {3}
			// check if 3 subset of 2-4
			return isIn( part2.getContent(), part1.getMinRangeValue(), part1.getMaxRangeValue() );
		} else if ( !part1.hasRange() && part2.hasRange() ) {
			// e.g. {3} =? {3}
			// check if 3 subset of 2-4
			return isIn( part1.getContent(), part2.getMinRangeValue(), part2.getMaxRangeValue() );
		}

		return -1;

	}

	private int isIn( String no, String leftRange, String rightRange ) {
		int i = Integer.parseInt( no );
		if ( i >= Integer.parseInt( leftRange ) && i <= Integer.parseInt( rightRange ) )
			return 0;

		return -1;
	}

	public boolean isEqual(AbstractRegexPart source, AbstractRegexPart toTarget) {
		int ret = compare( source, toTarget );

		if ( ret == 0 )
			return true;

		return false;
	}

}
