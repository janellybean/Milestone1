package methods.indexing;

import java.util.*;

/**
 * Implements an Index using an inverted index.
 */
public class InvertedIndex implements Index{
    private final Map<String, List<Posting>> iIndex;
    private final List<String> iVocabulary;
    private final int iCorpusSize;

    /**
     * Constructs an empty index with given vocabulary set and corpus size.
     */
    public InvertedIndex() {
        //initialize everything
        iIndex = new HashMap<>();
        iVocabulary = new ArrayList<>();
        iCorpusSize = 0;
    }

    public InvertedIndex(Collection<String> vocabulary, int corpusSize) {
        //initialize everything
        iIndex = new HashMap<>();
        iVocabulary = new ArrayList<>(vocabulary);
        iCorpusSize = corpusSize;

        Collections.sort(iVocabulary);

        for(String term : iVocabulary) {
            iIndex.put(term, new ArrayList<>());
        }
    }

    //addTerm method must run in O(1) time
    //careful not to add duplicate postings using documentId
    public void addTerm(String term, int documentId) {
        //get the list of postings for the term
        List<Posting> postings = iIndex.get(term); 

        // If the term is not found in the index, create a new list.
        if (postings == null) {
            postings = new ArrayList<>();
            iIndex.put(term, postings);
        }

        // Use a list to check if the documentId is already in the postings list.
        for (Posting p : postings) {
            if (p.getDocumentId() == documentId) {
                return;
            }
        }
        
        // If the documentId is not in the postings list, add it.
        postings.add(new Posting(documentId));
        
    }

    /**
	 * Retrieves a list of Postings of documents that contain the given term.
	 */
    @Override
    public List<Posting> getPostings(String term) {
        //get the list of postings for the term from the inverted index
        //if the term is not found, return an empty list
        List<Posting> results = iIndex.get(term);
        if(results == null) {
            return Collections.emptyList();
        }

        return results;
    }

    /**
	 * A sorted list of all terms in the index vocabulary.
	 */
    @Override
    public List<String> getVocabulary() {
        return Collections.unmodifiableList(iVocabulary);
    }
}
