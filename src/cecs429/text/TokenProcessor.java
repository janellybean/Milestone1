package cecs429.text;

import java.util.List;


/**
 * A TokenProcessor applies some rules of normalization to a token from a document, and returns a term for that token.
 * 
 * Updated for Milestone 1, change the processToken method to convett a token into a type.
 * Add a new method, normalizeType, that converts a type into a term by using a "Porter2 Stemmer"

	Terms are inserted into the inverted index. Types are only used for wildcard queries and spelling correction.
 */
public interface TokenProcessor {
	/**
	 * Processes a token into a type.
	 */
	List<String> processToken(String token);

	/**
	 * Normalizes a type into a term.
	 */
	String normalizeType(List<String> type);
}
