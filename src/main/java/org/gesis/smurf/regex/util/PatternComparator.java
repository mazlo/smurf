package org.gesis.smurf.regex.util;

import java.util.Comparator;

/**
 * This class aims to be a parent class for all regular expressions comparators, which compare
 * two regular expressions on their semantic similarity.
 * 
 * @author matthaeus
 *
 * @param <T>
 */
public interface PatternComparator<T> extends Comparator<T> {
	
	public boolean isEqual( T source, T toTarget );

}
