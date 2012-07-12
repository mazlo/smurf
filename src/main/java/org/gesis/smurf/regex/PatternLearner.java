package org.gesis.smurf.regex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * Provides methods for learning patterns, i.e. regular expressions, from strings. Strings may be of the type '2004 Jan-24'. 
 * This algorithm is based on the "Clustering-based algorithm for rules generation" method, 
 * introduced in the paper of Anne Ngu, David Buttler, and Terence Critchlow  
 * "Automatic Generation of Data Types for Classification of Deep Web Sources", 2005.
 * 
 * @author matthaeus
 * 
 */
public class PatternLearner {

	public static Map<Character, String> regex;

	static {
		regex = new HashMap<Character, String>();

		regex.put( 'A', "[A-Za-z]" );
		regex.put( 'B', "[A-Za-z]" );
		regex.put( 'C', "[A-Za-z]" );
		regex.put( 'D', "[A-Za-z]" );
		regex.put( 'E', "[A-Za-z]" );
		regex.put( 'F', "[A-Za-z]" );
		regex.put( 'G', "[A-Za-z]" );
		regex.put( 'H', "[A-Za-z]" );
		regex.put( 'I', "[A-Za-z]" );
		regex.put( 'J', "[A-Za-z]" );
		regex.put( 'K', "[A-Za-z]" );
		regex.put( 'L', "[A-Za-z]" );
		regex.put( 'M', "[A-Za-z]" );
		regex.put( 'N', "[A-Za-z]" );
		regex.put( 'O', "[A-Za-z]" );
		regex.put( 'P', "[A-Za-z]" );
		regex.put( 'Q', "[A-Za-z]" );
		regex.put( 'R', "[A-Za-z]" );
		regex.put( 'S', "[A-Za-z]" );
		regex.put( 'T', "[A-Za-z]" );
		regex.put( 'U', "[A-Za-z]" );
		regex.put( 'V', "[A-Za-z]" );
		regex.put( 'W', "[A-Za-z]" );
		regex.put( 'X', "[A-Za-z]" );
		regex.put( 'Y', "[A-Za-z]" );
		regex.put( 'Z', "[A-Za-z]" );

		regex.put( 'a', "[A-Za-z]" );
		regex.put( 'b', "[A-Za-z]" );
		regex.put( 'c', "[A-Za-z]" );
		regex.put( 'd', "[A-Za-z]" );
		regex.put( 'e', "[A-Za-z]" );
		regex.put( 'f', "[A-Za-z]" );
		regex.put( 'g', "[A-Za-z]" );
		regex.put( 'h', "[A-Za-z]" );
		regex.put( 'i', "[A-Za-z]" );
		regex.put( 'j', "[A-Za-z]" );
		regex.put( 'k', "[A-Za-z]" );
		regex.put( 'l', "[A-Za-z]" );
		regex.put( 'm', "[A-Za-z]" );
		regex.put( 'n', "[A-Za-z]" );
		regex.put( 'o', "[A-Za-z]" );
		regex.put( 'p', "[A-Za-z]" );
		regex.put( 'q', "[A-Za-z]" );
		regex.put( 'r', "[A-Za-z]" );
		regex.put( 's', "[A-Za-z]" );
		regex.put( 't', "[A-Za-z]" );
		regex.put( 'u', "[A-Za-z]" );
		regex.put( 'v', "[A-Za-z]" );
		regex.put( 'w', "[A-Za-z]" );
		regex.put( 'x', "[A-Za-z]" );
		regex.put( 'y', "[A-Za-z]" );
		regex.put( 'z', "[A-Za-z]" );

		regex.put( '0', "\\d" );
		regex.put( '1', "\\d" );
		regex.put( '2', "\\d" );
		regex.put( '3', "\\d" );
		regex.put( '4', "\\d" );
		regex.put( '5', "\\d" );
		regex.put( '6', "\\d" );
		regex.put( '7', "\\d" );
		regex.put( '8', "\\d" );
		regex.put( '9', "\\d" );

		regex.put( ' ', "\\s" );

		// TODO to enhance
	}

	/**
	 * Learns a pattern from the passed token and returns a regular expression for it. 
	 * For instance, for the token 'Jan' the pattern '[A-Za-z]{3}' will be created. 
	 * 
	 * @param string
	 * @return A pattern for the passed token.
	 */
	public static String learnFromToken( String string ) {

		if ( StringUtils.isEmpty( string ) )
			return "";

		char[] tokens = string.toCharArray();

		StringBuilder ret = new StringBuilder();

		String lastRegex = "";
		int counter = 0;
		for ( char ch : tokens ) {
			String reg = regex.get( ch );

			if ( StringUtils.isEmpty( reg ) )
				// if there is no special pattern in the map, the pattern is simply the char itself
				reg = String.valueOf( ch );

			if ( !StringUtils.equals( lastRegex, reg ) )
				lastRegex = reg;

			counter++;
		}

		if ( counter <= 1 )
			ret.append( lastRegex );
		else
			ret.append( lastRegex + "{" + counter + "}" );

		return ret.toString();
	}

	/**
	 * Tokenizes the passed string. We see a token as a sequence of equal characters, 
	 * e.g. '2004' is a token. Uppercase and lowercase letters are treated equally. <br/><br/>
	 * 
	 * This method returns a string-array of tokens. For instance, for the string <i>'2004 Jan.24'</i>
	 * the method returns <i>['2004',' ','Jan','.','24']</i>.   
	 * 
	 * @param string
	 * @return A string-array containing the tokens.
	 */
	public static String[] tokenize( String string ) {
		if ( StringUtils.isEmpty( string ) )
			return new String[] {};

		// contains the tokens
		List<String> ret = new ArrayList<String>();
		// represents one token
		StringBuilder token = new StringBuilder();

		int lastType = -1;
		for ( char ch : string.toCharArray() ) {
			if ( lastType == -1 )
				// initialize in the first iteration
				lastType = Character.getType( ch );

			if ( lastType == Character.UPPERCASE_LETTER )
				// this is to handle uppercase letters and lowercase letters equaly
				lastType = Character.LOWERCASE_LETTER;

			if ( lastType != Character.getType( ch ) ) {
				// reset if we encounter a new symbol
				ret.add( token.toString() );

				lastType = Character.getType( ch );
				token = new StringBuilder();
			}

			token.append( ch );
		}
		
		// add the last token also
		ret.add( token.toString() );

		return ret.toArray( new String[] {} );
	}

	/**
	 * Generates a complete pattern, i.e. regular expression, for the given string. 
	 * The string is first tokenized. For each of the tokens a pattern is created. 
	 * Finally all patterns for the tokens are assembled.
	 * 
	 * @param string
	 * @return
	 */
	public static String learnPatternFor( String string ) {

		StringBuilder builder = new StringBuilder();

		for ( String token : tokenize( string ) ) {
			String pattern = learnFromToken( token );

			builder.append( pattern );
		}

		return builder.toString();
	}

}
