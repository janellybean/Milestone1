package cecs429.querying;

import java.util.List;

import cecs429.indexing.Index;
import cecs429.indexing.Posting;
import cecs429.text.BasicTokenProcessor;
import cecs429.text.EnglishTokenStream;

/**
 * A TermLiteral represents a single term in a subquery.
 */
public class TermLiteral implements QueryComponent {
	private String mTerm;
	
	public TermLiteral(String term) {
		mTerm = term;
	}
	
	public String getTerm() {
		return mTerm;
	}
	
	@Override
	public List<Posting> getPostings(Index index) {
		// process and stem the mTerm string.
		// take the input, put it in the processor
		// then stem it and return it

		BasicTokenProcessor processor = new BasicTokenProcessor();
		List<String> token = processor.processToken(mTerm);
		String finalTerm = processor.normalizeType(token);
		
		System.out.println("Query term: " + finalTerm);
		return index.getPostings(finalTerm);
	}
	
	@Override
	public String toString() {
		return mTerm;
	}
}
