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
    public void addTerm(String term, int documentId, int position) {
        //get the list of postings for the term
        List<Posting> postings = iIndex.get(term);

        //if the list is not null, then add the posting to the list
        if(postings != null) {
            //get the last posting in the list
            Posting lastPost = postings.get(postings.size() - 1);

            //if the last posting's documentId is not the same as the current documentId
            //then add the posting to the list
            if(lastPost.getDocumentId() != documentId) {
                postings.add(new Posting(documentId, position));
            }
            //else, add the position to the last posting
            else {
                lastPost.addPosition(position);
            }
        }
        //else, create a new list of postings for the term and add the posting to the list
        else {
            List<Posting> newPostings = new ArrayList<>();
            newPostings.add(new Posting(documentId, position));
            iIndex.put(term, newPostings);
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
