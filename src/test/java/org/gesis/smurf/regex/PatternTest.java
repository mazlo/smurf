package org.gesis.smurf.regex;

import org.junit.Assert;
import org.junit.Test;

public class PatternTest {

	@Test
	public void testEqualPatterns() {
		Pattern p = new Pattern( "[0-9]", 0.4 );

		boolean isEqual = p.equals( new Pattern( "[0-9]", 0.5 ) );

		Assert.assertTrue( isEqual );
	}

	@Test
	public void testUnequalPatterns() {
		Pattern p = new Pattern( "[0-1]", 0.4 );

		boolean isEqual = p.equals( new Pattern( "[0-9]", 0.5 ) );

		Assert.assertFalse( isEqual );
	}

	@Test
	public void testEqualNullPatterns() {
		Pattern p = new Pattern( "[0-9]", 0.4 );

		boolean isEqual = p.equals( null );

		Assert.assertFalse( isEqual );
	}
}
