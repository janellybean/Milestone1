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
import cecs429.querying.BooleanQueryParser;
import cecs429.querying.QueryComponent;
import cecs429.indexing.PosInvertedIndex;
import cecs429.text.BasicTokenProcessor;
import cecs429.text.EnglishTokenStream;

import cecs429.documents.Json.*;
import java.nio.file.Files;

import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class PositionalInvertedIndex {
    public static void main(String[] args) {
		// Create a DocumentCorpus to load .txt documents from the project directory.
		// DocumentCorpus corpus = DirectoryCorpus.loadTextDirectory(Paths.get("").toAbsolutePath(), ".txt");

		//ask the user first for the filepath
		System.out.println("Enter the document path: ");
		Scanner fileScanner = new Scanner(System.in);
		String filePath = fileScanner.nextLine();
		System.out.println(filePath);

		fileScanner.close();

		//create list of file extensions
		List<String> fileExtension = new ArrayList<String>();
		fileExtension.add(".txt");
		fileExtension.add(".json");

		// Create a DocumentCorpus to load either json or txt documents from the project directory.
		DocumentCorpus corpus = DirectoryCorpus.loadDirectory(Paths.get(filePath).toAbsolutePath(), fileExtension);
        
		// Index the documents of the corpus.
		Index index = indexCorpus(corpus);

		//print out the index
		

		// Make a token processor just to process the query 
		BasicTokenProcessor processor = new BasicTokenProcessor();

		//ask the user
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter a term to search or type 'quit' to exit: ");
		String query = scanner.nextLine();
		while(query != "quit") {
			//use the process token on the query string
			List<String> processedQuery = processor.processToken(query);
			//then put it in the cleaner
			String normalizedQuery = processor.normalizeType(processedQuery);

			//implement the BooleanQueryParser
			QueryComponent q = BooleanQueryParser.parseQuery(normalizedQuery);

			//then we can search the index
			//we use QueryComponent to get the postings, which uses index as the source
			//q.getPostings(index);
			int count = 0;
			for (Posting p : q.getPostings(index)) {
				//count the number of documents printed
				count++;
				System.out.println("Document " + corpus.getDocument(p.getDocumentId()).getTitle());
				//print the positions of the term in the document
				System.out.println("Positions: " + p.getPositions());
			}
			System.out.println(count + " documents found.");
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
			//make count for the position of the term
			int position = 1;
			//System.out.println("Found document " + d.getTitle());
			//iterate through tokens
			EnglishTokenStream ets = new EnglishTokenStream(d.getContent());
			for (String token : ets.getTokens()) {
				//process the token (aka clean it)
				List<String> processedTokens = processor.processToken(token);
				for (String finalToken : processedTokens) {
					//normalize the token (aka stem it)
					finalToken = processor.normalizeType(processedTokens);
					//add the term to the inverted index
					invertedIndex.addTerm(finalToken, d.getId(), position);
					position++;
				}
			}
		}
        return invertedIndex;
	}
}
