package cecs429.documents;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;

import com.fasterxml.jackson.databind.*;
import java.io.StringReader;
import java.io.BufferedReader;


/**
 * Represents a document that is saved as a json file in the local file system.
 */
public class JsonFileDocument implements FileDocument{
    private int mDocumentId;
    private Path mFilePath;
    
    /**
     * Constructs a JsonFileDocument with the given document ID representing the 
     * file at the given absolute file path.
     */
    public JsonFileDocument(int id, Path absoluteFilePath) {
        mDocumentId = id;
        mFilePath = absoluteFilePath;
    }

    @Override
    public Path getFilePath() {
        return mFilePath;
    }

    @Override
    public int getId() {
        return mDocumentId;
    }

    //read the json file (different from the TextFileDocument implementation)
    @Override
    public Reader getContent() {
        String content = "";
        try {
            //read the json file
            JsonNode json = new ObjectMapper().readTree(Files.newBufferedReader(mFilePath));
            //get the fields of the json file
            //it has "title" "body" and "url"
            content = json.get("title").asText() + " " + json.get("body").asText() + " " + json.get("url").asText();

            StringReader reader = new StringReader(content.toLowerCase());

            //return the content of the json file
            return reader;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //get the title of the json file
    @Override
    public String getTitle() {
        try {
            //read the json file
            JsonNode json = new ObjectMapper().readTree(Files.newBufferedReader(mFilePath));
            //return the title of the json file
            return json.get("title").asText();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //load the json file
    public static FileDocument loadJsonFileDocument(Path absolutePath, int documentId) {
        return new JsonFileDocument(documentId, absolutePath);
    }
}
