package cecs429.indexing;

import java.sql.Array;
import java.util.List;
import java.util.ArrayList;

/**
 * A Posting encapulates a document ID associated with a search query component.
 * 
 * TODO: add the list of positions of a posting.
 */
public class Posting {
	private int mDocumentId;
	private List<Integer> positions;
	
	public Posting(int documentId) {
		mDocumentId = documentId;
		this.positions = new ArrayList<>();
	}

	public Posting(int documentId, int position) {
		mDocumentId = documentId;	
		this.positions = new ArrayList<>();
		positions.add(position);
	}
	
	public int getDocumentId() {
		return mDocumentId;
	}

	public List<Integer> getPositions() {
		return positions;
	}

	//addPosition method to add the position to a list of positions
	//this is the second parameter of the Posting
	public void addPosition(int position) {
		this.positions.add(position);
	}

}
