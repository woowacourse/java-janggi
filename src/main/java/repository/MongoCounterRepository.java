package repository;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.Updates;
import org.bson.Document;

public class MongoCounterRepository implements CounterRepository, AutoCloseable {

    private static final String URI = "mongodb://localhost:27017";
    private static final String DB_NAME = "janggi_db";
    private static final String COLLECTION_NAME = "counters";

    private final MongoClient client;
    private final MongoCollection<Document> collection;

    public MongoCounterRepository() {
        this.client = MongoClients.create(URI);
        this.collection = client.getDatabase(DB_NAME).getCollection(COLLECTION_NAME);
    }

    @Override
    public Long getNextId() {
        Document result = collection.findOneAndUpdate(
                Filters.eq("_id", "gameId"),
                Updates.inc("seq", 1L),
                new FindOneAndUpdateOptions().upsert(true).returnDocument(ReturnDocument.AFTER)
        );
        return result.getLong("seq");
    }

    @Override
    public void close() {
        client.close();
    }
}
