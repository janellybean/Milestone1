package cecs429.querying;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import cecs429.indexing.Index;
import cecs429.indexing.Posting;

/**
 * An OrQuery composes other QueryComponents and merges their postings with a union-type operation.
 */
public class OrQuery implements QueryComponent {
	// The components of the Or query.
	private List<QueryComponent> mComponents;
	
	public OrQuery(List<QueryComponent> components) {
		mComponents = components;
	}
	
	@Override
	public List<Posting> getPostings(Index index) {
		List<Posting> result = new ArrayList<>();

		result = mComponents.get(0).getPostings(index);

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
				
				//if the postings are the same, add it
				//if the posting is smaller, add the smaller
				//if there is a posting that still has more, add the rest
				while (x < p1.size() && y < p2.size()) {
					if (p1.get(x).getDocumentId() == p2.get(y).getDocumentId()) {
						result.add(p1.get(x));
						x++;
						y++;
					} else if (p1.get(x).getDocumentId() < p2.get(y).getDocumentId()) {
						result.add(p1.get(x));
						x++;
					} else {
						result.add(p2.get(y));
						y++;
					}
				}
				//if there are still postings left, add them
				while(x < p1.size()) {
					result.add(p1.get(x));
					x++;
				}
				while(y < p2.size()) {
					result.add(p2.get(y));
					y++;
				}
				
            }
        }
		return result;
	}
	
	@Override
	public String toString() {
		// Returns a string of the form "[SUBQUERY] + [SUBQUERY] + [SUBQUERY]"
		return "(" +
		 String.join(" OR ", mComponents.stream().map(c -> c.toString()).collect(Collectors.toList()))
		 + " )";
	}
}
