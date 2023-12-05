//Janelle Chan
//CECS429 Assignment 1

package edu.csulb;

import cecs429.documents.DirectoryCorpus;
import cecs429.documents.Document;
import cecs429.documents.DocumentCorpus;
import cecs429.indexing.Index;
import cecs429.indexing.Posting;
import cecs429.indexing.TermDocumentIndex;
import cecs429.text.BasicTokenProcessor;
import cecs429.text.EnglishTokenStream;

import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Scanner;

public class TermDocumentIndexer {
	public static void main(String[] args) {
		// Create a DocumentCorpus to load .txt documents from the project directory.
		DocumentCorpus corpus = DirectoryCorpus.loadTextDirectory(Paths.get("").toAbsolutePath(), ".txt");
		// Index the documents of the corpus.
		Index index = indexCorpus(corpus) ;

		// We aren't ready to use a full query parser; for now, we'll only support single-term queries.
		//String query = "whale"; // hard-coded search for "whale"

		// TODO: fix this application so the user is asked for a term to search.

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
		HashSet<String> vocabulary = new HashSet<>();
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
				//String processedToken = processor.processToken(token);
				//add to vocabulary
				//vocabulary.add(processedToken);
			}
		}
		
		// TODO:
		// Constuct a TermDocumentMatrix once you know the size of the vocabulary.
		// THEN, do the loop again! But instead of inserting into the HashSet, add terms to the index with addPosting.

		//construct a TermDocumentMatrix
		TermDocumentIndex tdm = new TermDocumentIndex(vocabulary, corpus.getCorpusSize());
		//loop again to add terms to the index with addPosting
		for(Document d : corpus.getDocuments()) {
			EnglishTokenStream ets = new EnglishTokenStream(d.getContent());
			for(String token : ets.getTokens()) {
				//String processedToken = processor.processToken(token);
				//tdm.addTerm(processedToken, d.getId());
			}
		}
		
		return tdm;
	}
}

