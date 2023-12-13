package cecs429.indexing;

import java.util.*;

/**
 * A Posting encapulates a document ID associated with a search query component.
 * Updated to represent a list of positions for each posting
 * The posting will look like this: (docId, [pos1, pos2, ...])
 */
public class Posting {
	private int mDocumentId;
	private List<Integer> mPositions;

	public Posting(int documentId, int position) {
		mDocumentId = documentId;
		mPositions = new ArrayList<>();
		mPositions.add(position);
	}

	public Posting(int documentId) {
		mDocumentId = documentId;
	}

	
	public int getDocumentId() {
		return mDocumentId;
	}

	public List<Integer> getPositions() {
		return mPositions;
	}
	
}
