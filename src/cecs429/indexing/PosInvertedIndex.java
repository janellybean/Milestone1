package cecs429.indexing;

import java.util.*;

/**
 * Implements an Index using an inverted index.
 */
public class PosInvertedIndex implements Index{
    //Map that should take in a String term and List with documentId and position
    // private final Map<String, List<Posting>> iIndex;
    private final Map<String, List<List<Object>>> iIndex;
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
		List<List<Object>> postingList;

		//if a term already has a List of Postings
		if(iIndex.containsKey(term))
		{
			//Example: represented as (posting1,[1]),(posting2,[1])
			postingList = iIndex.get(term);

			//get the last posting in the list to check for duplicates
			Posting lastPosting = (Posting) postingList.get(postingList.size() - 1).get(0);

           //CHange this to use Posting<DocumentId, List<Position>> as it was before
           //no using the .get(postingList.size() AND using .get(0))

			//if the last posting of the list is not equal to the document ID, then add the posting to the inverted index
			//with the position the token is located
			if (lastPosting.getDocumentId() != docId)
			{
				Posting p = new Posting(docId);
				List<Object> postingPositionPair = new ArrayList<>();
				List<Integer> positionList =new ArrayList<>();
				positionList.add(position);
				postingPositionPair.add(p);
				postingPositionPair.add(positionList);
				postingList.add(postingPositionPair);
			}

			//if the posting is already in the list, then add the position token to the arraylist
			else{
				List<Object> pair = postingList.get(postingList.size()-1);
				List<Integer> positionList = (List<Integer>) pair.get(1);
				positionList.add(position);
			}

		}

		//initialize the term and the posting list if they do not exist in the hashmap
		else
		{
			//the main postinglist that is going to be stored as the value in the hashmap
			postingList = new ArrayList<>();

			//the position of each token in the document
			List<Integer> positionList = new ArrayList<>();

			//the pair of postinglist and the tokens position
			List<Object> postingPositionPair = new ArrayList<>();
			positionList.add(position); //remember to make the positionlist and list

			Posting p = new Posting(docId);

			//ex to create (posting1,[1])
			postingPositionPair.add(p);
			postingPositionPair.add(positionList);

			//add to the main postingList that is going to be stored in the hashmap
			postingList.add(postingPositionPair);
			iIndex.put(term, postingList);
		}
		/* to test out the position of the token in the doc
		if(documentId == 1){
			List<Object> pair = postingList.get(postingList.size()-1);
			System.out.println(pair.get(1));
		}
		 */
        // //list of all postings for the term
        // List<List<Object>> postings;

        // //if the term does not have a posting list, create one
        // if (!iIndex.containsKey(term)) {
        //     postings = new ArrayList<>();

        //     //the position is added to this posting
        //     //position has to be added to the list of positions
        //     List<Integer> positionList = new ArrayList<>();
        //     positionList.add(position);

        //     List<Object> postPair = new ArrayList<>();

        //     Posting post = new Posting(docId);

        //     postPair.add(post);
        //     postPair.add(positionList);

        //     //add the term and posting list to the index
        //     iIndex.put(term, postings);
        //     iVocabulary.add(term);
        // } else {
        //     //if the term has a posting list, get it and add the position
        //     //the postings looks like (docId, [pos1, pos2, ...]), (docId, [pos1, pos2, ...])
        //     postings = iIndex.get(term);

        //     //check if the docId is already in the posting list using the last posting
        //     //if it is, add the position to the list of positions
        //     //if not, create a new posting and add it to the list of postings
        //     if (postings.get(postings.size() - 1).get(0).equals(docId)) {
        //         //get the last posting and add the position to the list of positions
        //         List<Object> positionList = (List<Object>) postings.get(postings.size() - 1).get(1);
        //         positionList.add(position);
        //         //update the postings list
        //         iIndex.put(term, postings);
        //     } else {
        //         //create a new posting if it is in a new doc
        //         //and add it to the list of postings
        //         List<Integer> positionList = new ArrayList<>();
        //         List<Object> postPair = new ArrayList<>();
        //         Posting post = new Posting(docId);

        //         //add the position to the list of positions
        //         positionList.add(position);
        //         postPair.add(post);
        //         postPair.add(positionList);
        //         postings.add(postPair);

        //         //update the postings list
        //         iIndex.put(term, postings);
        //     }
        // }
    }

    /**
	 * Retrieves a list of Postings of documents that contain the given term.
	 */
    @Override
    public List<Posting> getPostings(String term) {
        //get the list of postings for the term from the inverted index
        //if the term is not found, return an empty list

        List<Posting> postings = new ArrayList<>();
        if (iIndex.get(term) != null) {
            for(List<Object> pair : iIndex.get(term)) {
            postings.add((Posting) pair.get(0));
            }
        }

        return postings;
    }

    /**
	 * A sorted list of all terms in the index vocabulary.
	 */
    @Override
    public List<String> getVocabulary() {
        return iVocabulary;
    }
}
