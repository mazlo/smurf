package org.gesis.smurf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;

import org.gesis.smurf.ontologie.RegularExpressionMatcher;
import org.gesis.smurf.ontologie.SchemaMatch;
import org.gesis.smurf.regex.Pattern;
import org.gesis.smurf.regex.PatternClass;
import org.gesis.smurf.regex.PatternType;

public class RegexMatcher {

	public static void main( String[] args ) {
		if ( args.length < 3 ) {
			System.out.println( "Too less arguments. Please provide:\n- source ontology file\n- target ontology file\n- file with patterns that follows the following convention\n\t<regex_pattern><tab><weighting_as_double>\n\te.g.[0-9]{3}     0.4\n  you can specify more than one pattern in each line.\n\nAuthors: matthaeus.zloch@gesis.org, benjamin.zapilko@gesis.org, johann.schaible@gesis.org" );
			System.exit( -1 );
		}

		String filename1 = args[0];
		String filename2 = args[1];

		String patternFilename = args[2];

		PatternClass patternClass = new PatternClass( PatternType.CODE );

		try {
			File patternsFile = new File( patternFilename );

			if ( !patternsFile.exists() )
				throw new FileNotFoundException( "Could not find file that holds the regular expression patterns." );

			BufferedReader reader = new BufferedReader( new FileReader( patternsFile ) );

			// read pattern in each line
			String line = "";
			while ( ( line = reader.readLine() ) != null ) {
				String[] patternString = line.split( "\\t" );

				if ( patternString.length != 2 ) {
					System.err.println( "Could not parse pattern in file: " + patternFilename + ". Please remind the convention." );
					System.exit( -1 );
				}

				// add each pattern to the class
				patternClass.add( Pattern.compile( patternString[0], Double.valueOf( patternString[1] ) ) );
			}

		} catch ( FileNotFoundException e ) {
			e.printStackTrace();
			System.err.println( "Could not find file. I have to stop here." );
			System.exit( -1 );
		} catch ( IOException e ) {
			e.printStackTrace();
			System.err.println( "Could not find file. I have to stop here." );
			System.exit( -1 );
		}

		// the matcher
		RegularExpressionMatcher matcher = new RegularExpressionMatcher( new File( filename1 ), new File( filename2 ), patternClass );

		// get the matchings
		Set<SchemaMatch> matchings = matcher.getMatchings();

		if ( matchings.size() == 0 ) {
			System.out.println( "No matchings found. sorry." );
			System.exit( 0 );
		}

		// print out on console
		for ( SchemaMatch match : matchings ) {
			System.out.println( match.getLeftMatch() + " and " + match.getRightMatch() + ": " + match.getProbability() );
		}
	}
}
