package org.gesis.smurf.regex;

import junit.framework.Assert;

import org.junit.Test;

public class PatternClassTest {

	@Test
	public void testSortingDescendent() {
		PatternClass pc = new PatternClass( PatternType.CODE );
		pc.add( Pattern.compile( "[a-zA-Z][a-zA-Z]-[0-9]", 0.7 ) );
		pc.add( Pattern.compile( "[a-zA-Z]", 0.4 ) );
		pc.add( Pattern.compile( "[a-zA-Z][a-zA-Z]", 0.9 ) );

		Assert.assertEquals( 3, pc.size() );

		// sorting
		Assert.assertEquals( 0.9, pc.first().getWeight() );
		Assert.assertEquals( 0.4, pc.last().getWeight() );
	}

	@Test
	public void testSortingAscendent() {
		PatternClass pc = new PatternClass( PatternType.CODE );
		pc.setSortBy( Pattern.SORT_ASCENDING );

		pc.add( Pattern.compile( "[a-zA-Z][a-zA-Z]-[0-9]", 0.7 ) );
		pc.add( Pattern.compile( "[a-zA-Z]", 0.4 ) );
		pc.add( Pattern.compile( "[a-zA-Z][a-zA-Z]", 0.9 ) );

		Assert.assertEquals( 3, pc.size() );

		// sorting
		Assert.assertEquals( 0.4, pc.first().getWeight() );
		Assert.assertEquals( 0.9, pc.last().getWeight() );
	}

	@Test
	public void testEqualPatterns() {
		PatternClass pc = new PatternClass( PatternType.CODE );
		pc.addPattern( Pattern.compile( "[a-zA-Z]", 0.4 ) );
		pc.addPattern( Pattern.compile( "[a-zA-Z]", 0.4 ) );

		Assert.assertEquals( 1, pc.size() );
	}

}
