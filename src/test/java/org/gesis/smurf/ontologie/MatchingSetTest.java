package org.gesis.smurf.ontologie;

import org.junit.Assert;
import org.junit.Test;

public class MatchingSetTest {

	@Test
	public void testMatchingSet() {
		MatchingSet set = new MatchingSet();
		Match match;

		set.add( new Match( null, 0.9 ) );

		// size is 1
		Assert.assertEquals( 1, set.size() );

		// get the first match
		match = set.iterator().next();

		Assert.assertNotNull( match );
		Assert.assertTrue( 0.9d == match.getWeight() );

		// add to more matches
		set.add( new Match( null, 0.9 ) );
		set.add( new Match( null, 0.2 ) );

		// size is still 1 because there is only one match (all matches are equal because of the null property)
		Assert.assertEquals( 1, set.size() );

		// get the match
		match = set.iterator().next();

		Assert.assertNotNull( match );
		Assert.assertTrue( 2.0d == match.getWeight() );
	}

	@Test
	public void testPropertyCounter() {
		// TODO zl test
	}
}
