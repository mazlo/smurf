package org.gesis.smurf.regex;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

import com.sharomank.regex.parser.AbstractRegexPart;
import com.sharomank.regex.parser.RegexParser;

public class PatternParserTest {

	@Test
	public void testPatternParser1() {
		List<AbstractRegexPart> parts = RegexParser.parse( "\\d{2}.[A-Za-z]{2,4}" );

		for ( AbstractRegexPart part : parts ) {
			assertNotNull( part.getContent() );
		}

		AbstractRegexPart[] partsAsArray = parts.toArray( new AbstractRegexPart[] {} );

		assertEquals( "2", partsAsArray[1].getContent() );
		assertEquals( "A-Za-z", partsAsArray[3].getContent() );
		assertEquals( "2,4", partsAsArray[4].getContent() );
	}
	
	@Test
	public void testPatternParser2() {
		List<AbstractRegexPart> parts = RegexParser.parse( "(a|b)" );

		for ( AbstractRegexPart part : parts ) {
			assertNotNull( part.getContent() );
			System.out.println( part );
		}

	}
}