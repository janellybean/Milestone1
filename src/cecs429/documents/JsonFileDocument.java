package cecs429.documents;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Represents a document that is saved as a json file in the local file system.
 */
public class JsonFileDocument implements FileDocument{
    private int mDocumentId;
    private Path mFilePath;

    //declare the variables for the json file
    private String title;
    private String body;
    private String url;
    
    /**
     * Constructs a JsonFileDocument with the given document ID representing the 
     * file at the given absolute file path.
     */
    public JsonFileDocument(int id, Path absoluteFilePath) {
        mDocumentId = id;
        mFilePath = absoluteFilePath;

        //read the json file
        try{
            //create an object mapper
            ObjectMapper mapper = new ObjectMapper();
            //read the json file
            JsonNode jsonNode = mapper.readTree(absoluteFilePath.toFile());

            //get the title, body, and url from the json file
            this.title = jsonNode.get("title").asText();
            this.body = jsonNode.get("body").asText();
            this.url = jsonNode.get("url").asText();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Path getFilePath() {
        return mFilePath;
    }

    @Override
    public int getId() {
        return mDocumentId;
    }

    @Override
    public Reader getContent() {
        try {
            return Files.newBufferedReader(mFilePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getTitle() {
        return mFilePath.getFileName().toString();
    }

    public static FileDocument loadJsonFileDocument(Path absolutePath, int documentId) {
        return new JsonFileDocument(documentId, absolutePath);
    }
}
