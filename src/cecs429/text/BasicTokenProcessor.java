package cecs429.text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//import org.tartarus.snowball.ext.PorterStemmer;
//import org.apache.lucene.analysis.en.PorterStemmer;
//import the portStemmer.java file

import cecs429.text.Stemmer;


/**
 * A BasicTokenProcessor will process tokens into types and normalize the types into terms.
 * 
 * (Note:) Terms are inserted into the inverted index. Types are only used for wildcard queries and spelling correction.
 */
public class BasicTokenProcessor implements TokenProcessor {
	/** processToken method converts a token into a type.
 	* 	1. If there is at least one hyphen (-) in the token:
			a. Split the token to form 2 or more tokens.
			b. Form a set containing the split tokens, as well as the original token with the hypen(s) removed.
			Example: "Hewlett-Packard-Computing" turns into the set {Hewlett, Packard, Computing, HewlettPackardComputing}.
			c. The remaining steps are performed on each token in the set, and the set of resulting types is returned. Note that this means the "process token" method now returns a list of string results, not just a single string.
		2. Remove all non-alphanumeric characters from the beginning and end of the token, but not the middle.
		Example: Hello. becomes Hello ; 192.168.1.1 remains unchanged.
		3. Remove all apostrophes or quotation marks (single or double quotes) from anywhere in the token.
		4. Convert the token to lowercase.
	*/
	@Override
	public List<String> processToken(String token) {
		List<String> types = new ArrayList<>();

		String cleanToken = token;

		//check the first or last character of the string
		//if it is not alphanumeric, remove it
		while(!Character.isLetterOrDigit(cleanToken.charAt(0)) || !Character.isLetterOrDigit(cleanToken.charAt(cleanToken.length() - 1))) {
			if(!Character.isLetterOrDigit(cleanToken.charAt(0))) {
				cleanToken = cleanToken.substring(1);
			}
			if(!Character.isLetterOrDigit(cleanToken.charAt(cleanToken.length() - 1))) {
				cleanToken = cleanToken.substring(0, cleanToken.length() - 1);
			}
		}
		
		//remove all apostrophes or quotation marks (single or double quotes) from anywhere in the token
		cleanToken = token.replaceAll("[\"']", "");

		//makes all tokens lower case
		cleanToken = token.toLowerCase();

		//check for hyphen
		if(cleanToken.contains("-")) {
			//split the token and add each split token to the set
			String[] splitTokens = cleanToken.split("-");
			for(String splitToken : splitTokens) {
				types.add(splitToken);

			//remove the hyphen and add the token to the set
			types.add(cleanToken.replaceAll("-", ""));
			}
		}
		else {
			//add the token to the set
			types.add(cleanToken);
		}

		//return the set of types
		return types;
	}

	/** normalizeType converts a type into a term by 
		1. stem using a "Porter2 Stemmer"

		Will take the set of types and return a term.
	*/
	@Override
	public String normalizeType(List<String> types) {
		//take the list from processToken and stem it
		Stemmer stemmer = new Stemmer();
		for(String type : types) {
			stemmer.add(type.toCharArray(), type.length());
			stemmer.stem();
		}

		//return the stemmed word
		return stemmer.toString();
	}
}
