package cecs429.indexing;

import java.util.List;

/**
 * A Posting encapulates a document ID associated with a search query component.
 * 
 * TODO: add the list of positions of a posting.
 */
public class Posting {
	private int mDocumentId;
	private int mPosition;
	private int[] positions;
	
	public Posting(int documentId) {
		mDocumentId = documentId;
	}

	public Posting(int documentId, int position) {
		mDocumentId = documentId;
		mPosition = position;
	}
	
	public int getDocumentId() {
		return mDocumentId;
	}

	public int getPosition() {
		return mPosition;
	}

	public void addPosition(int position) {
		if (positions == null) {
			positions = new int[1];
			positions[0] = position;
		} else {
			int[] newPositions = new int[positions.length + 1];
			System.arraycopy(positions, 0, newPositions, 0, positions.length);
			newPositions[positions.length] = position;
			positions = newPositions;
		}
	}

	// print out the whole array of positions in a string function
	public void printPositions() {
		System.out.print("Positions: ");
		for (int i = 0; i < positions.length; i++) {
			System.out.print(positions[i] + " ");
		}
		System.out.println();
	}

	public String getPositions() {
		String pos = "";
		for (int i = 0; i < positions.length; i++) {
			pos += positions[i] + " ";
		}
		return pos;
	}
}
