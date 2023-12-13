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

		// TODO: program the merge for an AndQuery, by gathering the postings of the composed QueryComponents and
		// intersecting the resulting postings.
		for(int i = 0; i < mComponents.size(); i++) {
			if(index.getVocabulary().contains(mComponents.get(i).toString()) && index.getVocabulary().contains(mComponents.get(i+1).toString())) {
				List<Integer> q1 = new ArrayList<>();
				List<Integer> q2 = new ArrayList<>();

				for(Posting p: mComponents.get(i).getPostings(index)) {
					q2.add(p.getDocumentId());
				}

				int x = 0;
				int y = 0;

				while(x < q1.size() && y < q2.size()) {
					if(q1.get(x).equals(q2.get(y))) {
						query.add(q1.get(x));
						x++;
						y++;
					}
					else if(q1.get(x) < q2.get(y)) {
						x++;
					}
					else {
						y++;
					}
				}
			}
		}

		// QueryComponent queryComponent = mComponents.get(0);
		// List<Posting> postingsList = queryComponent.getPostings(index);
		// for(Posting posting: postingsList){
		// 	if (query.contains(posting.getDocumentId()))
		// 			result.add(posting);
		// }

		return result;
	}
	
	@Override
	public String toString() {
		return
		 String.join(" AND ", mComponents.stream().map(c -> c.toString()).collect(Collectors.toList()));
	}
}
