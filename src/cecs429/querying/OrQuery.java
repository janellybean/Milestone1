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

		//program the merge for an OrQuery, by gathering the postings of the composed QueryComponents and
		// unioning the resulting postings.
		for(QueryComponent component: mComponents) {
			// String andComponent = component.toString();

			//checks the AND component of the query
			// if (andComponent.contains("AND")) {
			// 	List<Posting> andPostings = component.getPostings(index);
			// 	for (Posting posting : andPostings) {
			// 		if (!result.contains(posting))
			// 			result.add(posting);
			// 	}
			// }
			// else {
				// List<Posting> orPostings = component.getPostings(index);
				// for (Posting posting : orPostings) {
				// 	if (!result.contains(posting))
				// 		result.add(posting);
				// }
			// }
			result.addAll(component.getPostings(index));
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
