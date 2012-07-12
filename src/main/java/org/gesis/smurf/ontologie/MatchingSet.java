package org.gesis.smurf.ontologie;

import java.util.LinkedHashSet;

import org.gesis.smurf.util.FrequencyCounter;

import com.hp.hpl.jena.rdf.model.Property;

/**
 * A set of Match-ojects.
 * 
 * @author matthaeus
 * 
 */
public class MatchingSet extends LinkedHashSet<Match> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3480010161016358281L;

	private FrequencyCounter counter = new FrequencyCounter();

	@Override
	public boolean add( Match m ) {

		Match[] matches = toArray( new Match[] {} );

		// check if it exists
		for ( Match match : matches ) {
			if ( !m.equals( match ) )
				// does not exist
				continue;

			m.setWeight( m.getWeight() + match.getWeight() );
			break;
		}

		// remove the element first, since a set does not overwrite the element
		// when inserted twice
		super.remove( m );

		// weight of m is overwritten now. add the new object
		super.add( m );

		return true;
	}

	public FrequencyCounter getPropertyCounter() {
		return counter;
	}

	/**
	 * Increases the counter for the given <i>property</i>.
	 * 
	 * @param predicate
	 */
	public void increasePropertyCount( Property predicate ) {
		if ( predicate == null )
			return;

		counter.add( predicate.toString() );
	}

	/**
	 * Returns the frequency counter of the given <i>property</i>.
	 * 
	 * @param predicate
	 * @return
	 */
	public double getFrequencyOf( Property predicate ) {
		if ( predicate == null )
			return 0d;

		return counter.getFrequencyOf( predicate.toString() );
	}

}
