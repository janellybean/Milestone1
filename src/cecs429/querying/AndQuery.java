package cecs429.querying;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Collections;

import cecs429.indexing.Index;
import cecs429.indexing.Posting;

/**
 * An AndQuery composes other QueryComponents and merges their postings in an intersection-like operation.
 */
public class AndQuery implements QueryComponent {
	private List<QueryComponent> mComponents;
	

	public AndQuery(List<QueryComponent> components) {
		mComponents = components;
	}
	
	public List<QueryComponent> getComponents() {
		return mComponents;
	}
	
	@Override
	public List<Posting> getPostings(Index index) {
		List<Posting> result = new ArrayList<>();

		// program the merge for an AndQuery, by gathering the postings of the composed QueryComponents and
		// intersecting the resulting postings.

		// get the first component's postings
		result = mComponents.get(0).getPostings(index);
		// then intersect it with the next component
		// and then use this as a query to intersect with the next component
		// then return the result

        if (mComponents.size() > 1) {
            for (int i = 1; i < mComponents.size(); i++) {
				//add the two postings together if there is more than one component
				//set p1 as the result (it is for after merging the first two in case)
				List<Posting> p1 = result;
				//the second posting will be the next component
				List<Posting> p2 = mComponents.get(i).getPostings(index);
				//new result which contains the intersection of these two postings
				result = new ArrayList<>();

				int x = 0;
				int y = 0;
				
				//while the counters are less than the size of the lists
				//compare the result using the docId for these postings
				while (x < p1.size() && y < p2.size()) {
					if (p1.get(x).getDocumentId() == p2.get(y).getDocumentId()) {
						result.add(p1.get(x));
						x++;
						y++;
					} else if (p1.get(x).getDocumentId() < p2.get(y).getDocumentId()) {
						x++;
					} else {
						y++;
					}
				}
            }
        }

        return result;

		// //this loop is to get the postings for each component
		// for(int i = 0; i < mComponents.size(); i++) {
		// 	//two lists for the two components
		// 	List<Integer> q1 = new ArrayList<>();
		// 	List<Integer> q2 = new ArrayList<>();

		// 	//get the postings for the current component
		// 	for(Posting p: mComponents.get(i).getPostings(index)) {
		// 		q1.add(p.getDocumentId());
		// 	}

		// 	//counters for the two lists
		// 	int x = 0;
		// 	int y = 0;

		// 	//while the counters are less than the size of the lists
		// 	//aka while lists are not empty yet
		// 	//compare the two lists and add the postings to the first list if they are the same posting
		// 	
		// }
		

		// return result;
	}
	
	@Override
	public String toString() {
		return
		 String.join(" AND ", mComponents.stream().map(c -> c.toString()).collect(Collectors.toList()));
	}
}
