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
		List<Integer> query = new ArrayList<>();

		// program the merge for an AndQuery, by gathering the postings of the composed QueryComponents and
		// intersecting the resulting postings.

		//this loop is to get the postings for each component
		for(int i = 0; i < mComponents.size(); i++) {
			//two lists for the two components
			List<Integer> q1 = new ArrayList<>();
			List<Integer> q2 = new ArrayList<>();

			//get the postings for the current component
			for(Posting p: mComponents.get(i).getPostings(index)) {
				q2.add(p.getDocumentId());
			}

			//counters for the two lists
			int x = 0;
			int y = 0;

			//while the counters are less than the size of the lists
			//aka while lists are not empty yet
			//compare the two lists and add the postings to the first list if they are the same posting
			while(x < q1.size() && y < q2.size()) {
				if(q1.get(x).equals(q2.get(y))) {
					query.add(q1.get(x));
					x++;
					y++;
				}
				//if the postings are not the same, increment the counter of the list with the smaller posting
				//aka the list for the word that has less postings is incremented
				else if(q1.get(x) < q2.get(y)) {   
					x++;
				}
				else {
					y++;
				}
			}
		}
		return result;
	}
	
	@Override
	public String toString() {
		return
		 String.join(" AND ", mComponents.stream().map(c -> c.toString()).collect(Collectors.toList()));
	}
}
