package dao.mongodb;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoConnection implements AutoCloseable {

    private static final String CONNECTION_STRING = "mongodb://admin:password123@localhost:27017";
    private static final String DATABASE_NAME = "janggi";

    private final MongoClient client;
    private final MongoDatabase database;

    public MongoConnection() {
        this.client = MongoClients.create(CONNECTION_STRING);
        this.database = client.getDatabase(DATABASE_NAME);
    }

    public MongoCollection<Document> getCollection(String name) {
        return database.getCollection(name);
    }

    @Override
    public void close() {
        client.close();
    }
}