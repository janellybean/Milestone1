/**
 * Janelle Chan
 * CECS429 Assignment 2
 * Similar to TermDocumentIndexer.java, but using the InvertedIndex class
 */

package main;

import methods.documents.DirectoryCorpus;
import methods.documents.Document;
import methods.documents.DocumentCorpus;
import methods.indexing.Index;
import methods.indexing.Posting;
import methods.indexing.InvertedIndex;
import methods.text.BasicTokenProcessor;
import methods.text.EnglishTokenStream;

import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Scanner;

public class InvertedIndexer {
    public static void main(String[] args) {
        // Create a DocumentCorpus to load .txt documents from the project directory.
		DocumentCorpus corpus = DirectoryCorpus.loadTextDirectory(Paths.get("").toAbsolutePath(), ".txt");
		// Index the documents of the corpus.
		Index index = indexCorpus(corpus);

		Scanner scanner = new Scanner(System.in);
		while(true) {
			//ask the user
			System.out.println("Enter a term to search or type 'quit' to exit: ");
			
			String query = scanner.nextLine();
			if(query.equals("quit")) {
				break;
			}
			for (Posting p : index.getPostings(query)) {
				System.out.println("Document " + corpus.getDocument(p.getDocumentId()).getTitle());
			}
		}
		scanner.close();
	}
	
	private static Index indexCorpus(DocumentCorpus corpus) {
        //HashSet<String> vocabulary = new HashSet<>();
        //inverted index implementation
        InvertedIndex invertedIndex = new InvertedIndex();
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
				String processedToken = processor.processToken(token);
				//add to inverted index
				invertedIndex.addTerm(processedToken, d.getId());
			}
		}
        return invertedIndex;
	}
}
