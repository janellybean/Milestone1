package cecs429.indexing;

import java.util.*;

/**
 * This is the new inverted index that will track the positions of the term in each document
 * You will maintain one index for your corpus: 
 * the PositionalInvertedIndex, a positional index as discussed in class,
 *  where postings lists consist of (documentID, [position1, position2, ...]) pairs. 
 * Using InvertedIndex from Homework 2 as a reference point, 
 * create PositionalInvertedIndex as a new implementation of the Index interface. 
 * We will no longer have a need for positionless postings,
 *  so the Posting class will need to be updated to represent the list of positions 
 * of a posting. You will also need to modify addTerm to account for the position of 
 * the term within the document. 
    
 The index should consist of a hash map from string keys (the terms in the vocabulary) 
 to (array) lists of postings. You must not use any other hash maps or any “set” data 
 structures in your code, only lists.
 */

public class PosInvertedIndex2 implements Index{
    //hash map that has string keys and array lists of postings
    private final Map<String, List<Posting>> iIndex;
    //this is for the list of vocabulary terms
    private final List<String> iVocabulary;
    //this is so we can set the corpus size later
    private final int iCorpusSize; 

    /**
     * Constructs an empty index w given vocabulary set.
     */
    public PosInvertedIndex2() {
        //initialize everything
        iIndex = new HashMap<>();
        iVocabulary = new ArrayList<>();
        iCorpusSize = 0;
    }

    /*
     * Constructor w parameters
     */
    public PosInvertedIndex2(Collection<String> vocabulary, int corpusSize) {
        //initialize everything
        iIndex = new HashMap<>();
        iVocabulary = new ArrayList<>(vocabulary);
        iCorpusSize = corpusSize;

        Collections.sort(iVocabulary);

        for(String term : iVocabulary) {
            iIndex.put(term, new ArrayList<>());
        }
    }

    /*
     * addTerm method will add a term into the index, but 
     * accounts for the position of the term within the document. 
     */
    public void addTerm(String term, int documentId, List<int> positions) {
        
    }

    /**
	 * Retrieves a list of Postings of documents that contain the given term.
	 */
    @Override
	public List<Posting> getPostings(String term){
        return null;
    }
	
	/**
	 * A sorted list of all terms in the index vocabulary.
	 */
	   @Override
    public List<String> getVocabulary() {
        return null;
    }
}
