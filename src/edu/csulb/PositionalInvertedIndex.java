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

import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class PositionalInvertedIndex {
    public static void main(String[] args) {
		// Create a DocumentCorpus to load .txt documents from the project directory.
		// DocumentCorpus corpus = DirectoryCorpus.loadTextDirectory(Paths.get("").toAbsolutePath(), ".txt");

		List<String> fileNames = new ArrayList<>();
		fileNames.add(".txt");
		fileNames.add(".json");

		//ask the user for a directory path
		System.out.println("Enter a directory path: ");
		Scanner scanner = new Scanner(System.in);
		String path = scanner.nextLine().toLowerCase();

		//load the directory 
		DocumentCorpus corpus = DirectoryCorpus.loadDirectory(Paths.get(path).toAbsolutePath(), fileNames);

		//index the corpus
		System.out.println("Indexing corpus...");
		Index index = indexCorpus(corpus);
		System.out.println("Indexing is done.");

		//print the number of documents in the corpus
		System.out.println("Number of documents in the corpus: " + corpus.getCorpusSize());

		Scanner input = new Scanner(System.in);
		while(true)
		{
			System.out.print("Enter a query or type 'exit': ");
			// Make a token processor just to process the query
			//BasicTokenProcessor processor = new BasicTokenProcessor();
			String query = input.nextLine().toLowerCase();

			if(query.equals("exit")) {
				break;
			}

			QueryComponent q = BooleanQueryParser.parseQuery(query);
			List<Posting> queryPostings = q.getPostings(index);
			int postSize = queryPostings.size();
			
			for(Posting p : queryPostings){
				System.out.println(corpus.getDocument(p.getDocumentId()).getTitle() + " Doc ID: " + p.getDocumentId());
			}
			System.out.println("There are " + postSize + " postings returned.\n");
		
		}
		input.close();
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
