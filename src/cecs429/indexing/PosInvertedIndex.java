package cecs429.indexing;

import java.util.*;

/**
 * Implements an Index using an inverted index.
 */
public class PosInvertedIndex implements Index{
    //Map that should take in a String term and List with documentId and position
    private final Map<String, List<Posting>> iIndex;
    private final List<String> iVocabulary;

    /**
     * Constructs an empty index with given vocabulary set and corpus size.
     */
    public PosInvertedIndex() {
        iIndex = new HashMap<>();
        iVocabulary = new ArrayList<>();
    }

    //addTerm method must run in O(1) time
    //careful not to add duplicate postings using documentId
    //edited so it tracks positions of terms in the document
    //each posting has the string term and a list of positions
    public void addTerm(String term, int docId, int position) {
        //list of all postings for the term
        List<Posting> postings;

        //if the term does not have a posting list, create one
        if (!iIndex.containsKey(term)) {
            postings = new ArrayList<>();

            //the position is added to this posting
            //position has to be added to the list of positions
            List<Integer> positionList = new ArrayList<>();
            positionList.add(position);
            postings.add(new Posting(docId, positionList));

            //add the term and posting list to the index
            iIndex.put(term, postings);
            iVocabulary.add(term);
        } else {
            //if the term has a posting list, get it and add the position
            //the postings looks like (docId, [pos1, pos2, ...]), (docId, [pos1, pos2, ...])
            postings = iIndex.get(term);

            //check if the docId is already in the posting list using the last posting
            //if it is, add the position to the list of positions
            //if not, create a new posting and add it to the list of postings
            if (postings.get(postings.size() - 1).getDocumentId() == docId) {
                //get the last posting and add the position to the list of positions
                postings.get(postings.size() - 1).getPositions().add(position);
                //update the postings list
                iIndex.put(term, postings);
            } else {
                //create a new posting if it is in a new doc
                //and add it to the list of postings
                List<Integer> positionList = new ArrayList<>();
                positionList.add(position);
                postings.add(new Posting(docId, positionList));
                //update the postings list
                iIndex.put(term, postings);
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
        List<Posting> postings = iIndex.get(term);
        if (postings == null) {
            return new ArrayList<>();
        } else {
            return postings;
        }
    }

    /**
	 * A sorted list of all terms in the index vocabulary.
	 */
    @Override
    public List<String> getVocabulary() {
        return iVocabulary;
    }
}
