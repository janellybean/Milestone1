package cecs429.indexing;

import java.util.*;

/**
 * Implements an Index using an inverted index.
 */
public class PosInvertedIndex implements Index{
    //Map that should take in a String term and List with documentId and position
    private final Map<String, List<Posting>> iIndex;
    // private final Map<String, List<List<Object>>> iIndex;
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
        //the main postinglist that is going to be stored as the value in the hashmap
		List<Posting> postingList;

		//if a term already has a List of Postings
		if(iIndex.containsKey(term))
		{
			//Example: represented as (posting1,[1]),(posting2,[1])
            //posting list is all of the postings for the term
			postingList = iIndex.get(term);

			//get the last posting in the list to check for duplicates
			// Posting lastPosting = (Posting) postingList.get(postingList.size() - 1).get(0);

           //CHange this to use Posting<DocumentId, List<Position>> as it was before
            Posting lastPosting = postingList.get(postingList.size()-1);

            //aka if it is an old term in a new document
			if (lastPosting.getDocumentId() != docId)
			{
				Posting p = new Posting(docId, position);
                postingList.add(p);

                //test if the term was acutally added
                // System.out.println("Term added to existing posting: " + term + " to doc " + docId + " at position " + position);
			}
		}
		//initialize the term and the posting list if they do not exist in the hashmap
        //this is if it is a new term
		else
		{
			//the main postinglist that is going to be stored as the value in the hashmap
            //create a new list of postings for this term
			postingList = new ArrayList<>();

            //new posting for the new word
			Posting p = new Posting(docId, position);
            postingList.add(p);

            //test if the term was added to existing posting
            // System.out.println("New term added: " + term + " to doc " + docId + " at position " + position);
		}
        //add new posting list to the index of postings
		iIndex.put(term, postingList);
		
        
    }

    /**
	 * Retrieves a list of Postings of documents that contain the given term.
	 */
    @Override
    public List<Posting> getPostings(String term) {
        //get the list of postings for the term from the inverted index
        //if the term is not found, return an empty list

        List<Posting> postingList = new ArrayList<>();
        if (iIndex.get(term) != null) {
            for(Posting p : iIndex.get(term)){
                postingList.add(p);
            }
        }
        else {
            System.out.println("No postings found for the term.\n");
        }

        return postingList;
    }

    /**
	 * A sorted list of all terms in the index vocabulary.
	 */
    @Override
    public List<String> getVocabulary() {
        return iVocabulary;
    }
}
