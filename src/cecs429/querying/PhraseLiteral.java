package cecs429.querying;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.management.Query;

import cecs429.indexing.Index;
import cecs429.indexing.Posting;

/**
 * Represents a phrase literal consisting of one or more terms that must occur in sequence.
 */
public class PhraseLiteral implements QueryComponent {
	// The list of individual terms in the phrase.
	private List<QueryComponent> mComponents = new ArrayList<>();
	
	/**
	 * Constructs a PhraseLiteral with the given individual phrase terms.
	 */
	public PhraseLiteral(Collection<QueryComponent> terms) {
		mComponents.addAll(terms);
	}
		
	@Override
	public List<Posting> getPostings(Index index) {
		List<Posting> result = new ArrayList<>();

		List<Integer> query1  = new ArrayList<>();
		List<Integer> query2  = new ArrayList<>();

		// TODO: program this method. Retrieve the postings for the individual terms in the phrase,
		// and positional merge them together.
		for(QueryComponent component : mComponents){
			if(query1.isEmpty()) {
				for (Posting posting : index.getPostings(component.toString())) {
					query1.add(posting.getDocumentId());
				}
				result.addAll(index.getPostings(component.toString()));
				//skip to the next component
				continue;
			}
			for (Posting posting : index.getPostings(component.toString())) {
				query2.add(posting.getDocumentId());
			}

		}
		return result;
		// return null;
		// TODO: program this method. Retrieve the postings for the individual terms in the phrase,
		// and positional merge them together.
	}
	
	@Override
	public String toString() {
		String terms = 
			mComponents.stream()
			.map(c -> c.toString())
			.collect(Collectors.joining(" "));
		return "\"" + terms + "\"";
	}
}
