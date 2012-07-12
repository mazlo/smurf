package org.gesis.smurf.regex;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.gesis.smurf.regex.util.PatternComparator;
import org.gesis.smurf.util.FrequencyCounter;

public class PatternExtractor {

	private Map<String, Pattern> literalsToPatterns = new HashMap<String, Pattern>();
	private FrequencyCounter counter = new FrequencyCounter();

	private int totalCount = 0;

	// TODO zl Must be more generic here. this class should take any other
	// pattern comparator, not just those who take Patterns
	private PatternComparator<Pattern> patternComparator = null;

	public PatternExtractor() {
		// default constructor
	}

	public PatternExtractor(PatternComparator<Pattern> patternComparator) {
		this.patternComparator = patternComparator;
	}

	public void learnFrom( List<String> literals ) {
		learnFrom( literals.iterator() );
	}

	public void learnFrom( Iterator<String> literals ) {

		// for each literal
		while ( literals.hasNext() ) {
			String literal = literals.next();
			String pattern = PatternLearner.learnPatternFor( literal );

			if ( literalsToPatterns.containsKey( pattern ) ) {
				continue;
			}

			// add the pattern
			counter.add( pattern );
			literalsToPatterns.put( pattern, Pattern.compile( pattern, 0 ) );

			// increase total count
			totalCount++;
		}

		// update the pattern weights
		for ( String stringPattern : literalsToPatterns.keySet() ) {
			Pattern pattern = literalsToPatterns.get( stringPattern );

			pattern.setWeight( counter.getFrequencyOf( stringPattern ) / totalCount );
		}

	}

	public Collection<Pattern> getExtractedPatterns() {
		return literalsToPatterns.values();
	}

	/**
	 * @param patternClasses
	 * @param extractedPatterns
	 * @return
	 */
	public List<PatternClass> compareAndAddPattern( List<PatternClass> patternClasses, Collection<Pattern> extractedPatterns ) {

		if ( patternComparator == null )
			// no pattern comparator
			return patternClasses;

		boolean isEqual = false;
		boolean addedPatternToOtherClass = false;

		PatternClass otherClass = new PatternClass( PatternType.OTHER );

		// for each extracted pattern
		for ( Pattern extractedPattern : extractedPatterns ) {
			// loop each pattern class
			patternClassLoop: for ( PatternClass patternClass : patternClasses ) {
				for ( Pattern classPattern : patternClass ) {
					// check if pattern is equal to extracted pattern
					isEqual = patternComparator.isEqual( extractedPattern, classPattern );

					if ( isEqual )
						// look at the next extracted pattern
						break patternClassLoop;
				}

			}

			if ( !isEqual ) {
				// extracted pattern is not equal to any other pattern
				// add extracted pattern to new pattern class
				otherClass.add( extractedPattern );

				addedPatternToOtherClass = true;
			}

		}

		if ( addedPatternToOtherClass )
			// add other class only if extracted pattern added to it
			patternClasses.add( otherClass );

		return patternClasses;
	}
}
