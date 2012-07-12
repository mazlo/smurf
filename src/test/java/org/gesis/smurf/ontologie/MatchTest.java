package org.gesis.smurf.ontologie;

import org.junit.Assert;
import org.junit.Test;

public class MatchTest {

	@Test
	public void testEquality() {
		Match m1 = new Match( null, 0.9 );
		Match m2 = new Match( null, 0.9 );

		// returns true because of null property value
		Assert.assertEquals( m1, m2 );
	}

	@Test
	public void testAddingWeights() {
		Match match = new Match( null, 0.9 );

		match.addWeight( 0.9 );
		match.addWeight( 0.2 );

		Assert.assertTrue( 2.0d == match.getWeight() );
	}
}
