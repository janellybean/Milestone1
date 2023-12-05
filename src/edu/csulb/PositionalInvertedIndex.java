/**
 * Janelle Chan
 * CECS429 Milestone 1 
 * This is a Positional Inverted Index that is used at the very beginning of
 * our search engine. It is used to store the terms and their positions in the
 * documents. 
 */

package edu.csulb;

import cecs429.documents.DirectoryCorpus;
import cecs429.documents.Document;
import cecs429.documents.DocumentCorpus;
import cecs429.indexing.Index;
import cecs429.indexing.Posting;
import cecs429.indexing.PosInvertedIndex;
import cecs429.text.BasicTokenProcessor;
import cecs429.text.EnglishTokenStream;

import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Scanner;
import java.util.List;

public class PositionalInvertedIndex {
    public static void main(String[] args) {
		// Create a DocumentCorpus to load .txt documents from the project directory.
		DocumentCorpus corpus = DirectoryCorpus.loadTextDirectory(Paths.get("").toAbsolutePath(), ".txt");

		// //ask the user first for the filepath
		// System.out.println("Enter the document path: ");
		// Scanner fileScanner = new Scanner(System.in);
		// String filePath = fileScanner.nextLine();
		// System.out.println(filePath);

		// fileScanner.close();

		// // Create a DocumentCorpus to load documents from the project directory.
		// DocumentCorpus corpus = DirectoryCorpus.loadDirectory(Paths.get(filePath).toAbsolutePath(), ".json");
        
		// Index the documents of the corpus.
		Index index = indexCorpus(corpus);
		// Make a token processor just to process the query 
		BasicTokenProcessor processor = new BasicTokenProcessor();


		Scanner scanner = new Scanner(System.in);
		while(true) {
			//ask the user
			System.out.println("Enter a term to search or type 'quit' to exit: ");
			
			String query = scanner.nextLine();
			if(query.equals("quit")) {
				break;
			}

			//use the process token on the query string
			List<String> processedQuery = processor.processToken(query);
			//then put it in the cleaner
			String normalizedQuery = processor.normalizeType(processedQuery);
			//then we can search the index
			for (Posting p : index.getPostings(normalizedQuery)) {
				System.out.println("Document " + corpus.getDocument(p.getDocumentId()).getTitle());
			}
		}
		scanner.close();
	}
	
	private static Index indexCorpus(DocumentCorpus corpus) {
        //HashSet<String> vocabulary = new HashSet<>();
        //inverted index implementation
        PosInvertedIndex invertedIndex = new PosInvertedIndex();
		BasicTokenProcessor processor = new BasicTokenProcessor();

		// First, build the vocabulary hash set.
		for (Document d : corpus.getDocuments()) {
			System.out.println("Found document " + d.getTitle());
			// TODO:
			// Tokenize the document's content by constructing an EnglishTokenStream around the document's content.
			// Iterate through the tokens in the document, processing them using a BasicTokenProcessor,
			//		and adding them to the HashSet vocabulary.

			//iterate through tokens
			EnglishTokenStream ets = new EnglishTokenStream(d.getContent());
			for (String token : ets.getTokens()) {
				//process tokens
				List<String> processedTokens = processor.processToken(token);

				//take processed tokens and normalize
				String normalizedToken = processor.normalizeType(processedTokens);
                
				//print out each term that gets normalized
				//System.out.println(normalizedToken);

				//add the normalized terms to the inverted index
				invertedIndex.addTerm(normalizedToken, d.getId());
			}
		}
        return invertedIndex;
	}
}
