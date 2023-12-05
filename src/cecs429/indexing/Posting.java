package cecs429.indexing;

/**
 * A Posting encapulates a document ID associated with a search query component.
 * 
 * TODO: add the list of positions of a posting.
 */
public class Posting {
	private int mDocumentId;
	private int mPosition;
	
	public Posting(int documentId) {
		mDocumentId = documentId;
	}
	
	public int getDocumentId() {
		return mDocumentId;
	}

	public int getPosition() {
		return mPosition;
	}
}
