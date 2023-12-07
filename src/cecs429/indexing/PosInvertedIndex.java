package cecs429.indexing;

import java.util.*;

/**
 * Implements an Index using an inverted index.
 */
public class PosInvertedIndex implements Index{
    //Map that should take in a String term and List with documentId and position
    private final Map<String, List<Posting>> iIndex;
    private final List<String> iVocabulary;
    private final int iCorpusSize;

    /**
     * Constructs an empty index with given vocabulary set and corpus size.
     */
    public PosInvertedIndex() {
        //initialize everything
        iIndex = new HashMap<>();
        iVocabulary = new ArrayList<>();
        iCorpusSize = 0;
    }

    public PosInvertedIndex(Collection<String> vocabulary, int corpusSize) {
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
    //edited so it tracks positions of terms in the document
    //each posting has the string term and a list of positions
    public void addTerm(String term, int docId, int position) {
        //if the term is not in the vocabulary, add it
        if(!iIndex.containsKey(term)) {
            iVocabulary.add(term);
            iIndex.put(term, new ArrayList<>());
        }

        //get the list of postings for the term
        List<Posting> postings = iIndex.get(term);
        
        //if the list is empty, add the posting
        if(postings.isEmpty()) {
            postings.add(new Posting(docId, position));
            return;
        }

        //if the list is not empty, check if the posting already exists
        //if it does, add the position to the posting
        for(Posting p : postings) {
            if(p.getDocumentId() == docId) {
                p.addPosition(position);
                return;
            }
        }
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
