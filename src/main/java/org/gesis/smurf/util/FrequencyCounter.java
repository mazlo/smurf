package org.gesis.smurf.util;

import gnu.trove.map.TObjectDoubleMap;
import gnu.trove.map.hash.TObjectDoubleHashMap;

import java.util.Set;

import org.apache.commons.lang.StringUtils;

/**
 * Maintains a map of occurrences, where the occurrence of a class is counted.
 * 
 * @author matthaeus
 */
public class FrequencyCounter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2942604147345032355L;

	private TObjectDoubleMap<String> frequencies = new TObjectDoubleHashMap<String>();

	public FrequencyCounter() {
		super();
	}

	/**
	 * Adds a value, i.e. key, to the map of occurrences and increase the counter by 1.
	 * 
	 * @param key
	 */
	public void add( String key ) {
		int initialValue = 1;
		Number value = frequencies.get( key );

		if ( value == null ) {
			value = 0;
		}

		frequencies.put( key, value.intValue() + initialValue );
	}

	/**
	 * Returns the number of occurrences of this clazz.
	 * 
	 * @param key
	 * @return
	 */
	public double getFrequencyOf( String key ) {
		if ( StringUtils.isEmpty( key ) )
			return 0;

		return frequencies.get( key );
	}

	public Set<String> keySet() {
		return frequencies.keySet();
	}
}
