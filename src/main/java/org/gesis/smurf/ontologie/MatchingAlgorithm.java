package org.gesis.smurf.ontologie;

import java.io.InputStream;
import java.util.regex.Matcher;

import org.apache.commons.lang.StringUtils;
import org.gesis.smurf.regex.Pattern;
import org.gesis.smurf.regex.PatternClass;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.rdf.model.impl.LiteralImpl;
import com.hp.hpl.jena.rdf.model.impl.ResourceImpl;
import com.hp.hpl.jena.util.FileManager;

/**
 * Provides static methods for matching.
 * 
 * @author matthaeus
 * 
 */
public class MatchingAlgorithm {

	/**
	 * Reads the given <i>filename</i> into a Model-object.
	 * 
	 * @param filename
	 * @return
	 */
	public static Model readModelFromFile( String filename ) {
		Model model = ModelFactory.createDefaultModel();

		InputStream in = FileManager.get().open( filename );
		if ( in == null ) {
			throw new IllegalArgumentException( "File: " + filename + " not found" );
		}

		// read the RDF/XML file
		model.read( in, null );

		return model;
	}

	/**
	 * This is the most important class which builds up the matching algorithm.
	 * Returns a MatchingSet of Match-objects, where each of them represent a
	 * match of an instance for a pattern in <i>patternClass</i>.
	 * 
	 * @param patternClass
	 * @param statements
	 * @return
	 */
	public static MatchingSet findMatchings( PatternClass patternClass, StmtIterator statements ) {

		MatchingSet matchings = new MatchingSet();

		// print out the predicate, subject and object of each statement
		while ( statements.hasNext() ) {
			Statement stmt = statements.nextStatement(); // get next statement
			// Resource subject = stmt.getSubject(); // get the subject
			Property predicate = stmt.getPredicate(); // get the predicate
			RDFNode object = stmt.getObject(); // get the object

			String value = null;
			if ( object instanceof ResourceImpl ) {
				value = object.asResource().getLocalName();

				if ( StringUtils.isEmpty( value ) )
					// hack due to data model: take the value after the last
					// string
					value = object.asResource().toString().substring( object.asResource().toString().lastIndexOf( "/" ) + 1 );

				int index;
				if ( ( index = StringUtils.indexOf( value, "#" ) ) > 0 )
					// hack due to object value. it is, e.g. 'age2#5-9',
					// but we rather want '5-9'
					value = value.substring( index + 1 );

			} else if ( object instanceof LiteralImpl ) {
				value = object.toString();

				if ( StringUtils.contains( value, "^^" ) )
					// hack due to data model: we do not want have
					// '.../XMLSchema#unsigned' in the value
					value = value.substring( 0, value.indexOf( "^^" ) );
			} else {
				value = "";
			}

			matchings.increasePropertyCount( predicate );

			// iterate the patterns to see if some matches the literal
			for ( Pattern pattern : patternClass ) {
				Matcher matcher = pattern.getPattern().matcher( value );

				if ( !matcher.matches() ) {
					continue;
				}

				Match newMatch = new Match( predicate, pattern.getWeight() );
				matchings.add( newMatch );

				// TODO zl Add some threshold here

				break;
			}

		}

		return matchings;
	}

	public static void writeToSystemOut( Model model ) {
		model.write( System.out );
	}
}
