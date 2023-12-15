package cecs429.querying;

import java.util.List;

import cecs429.indexing.Index;
import cecs429.indexing.Posting;

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
		BasicTokenProcessor processor = new BasicTokenProcessor();
		EnglishTokenStream ets = new EnglishTokenStream(mTerm);

		// process the term using the BasicTokenProcessor
		String cleanedTerm = processor.processTokenStream(mTerm);
		cleanedTerm = processor.stemToken(cleanedTerm);
		cleanedTerm = processor.normalizeToken(cleanedTerm);

		return index.getPostings(cleanedTerm);
	}
	
	@Override
	public String toString() {
		return mTerm;
	}
}
