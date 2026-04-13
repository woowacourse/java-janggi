package dao;

import static org.assertj.core.api.Assertions.assertThat;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MongoConnectionTest {

    private static final String CONNECTION_STRING = "mongodb://admin:password123@localhost:27017";
    private static final String DATABASE_NAME = "janggi";

    @Test
    @DisplayName("MongoDB 서버에 연결할 수 있다")
    void MongoDB_서버에_연결할_수_있다() {
        try (MongoClient client = MongoClients.create(CONNECTION_STRING)) {
            MongoDatabase database = client.getDatabase(DATABASE_NAME);

            Document result = database.runCommand(new Document("ping", 1));

            assertThat(result.getDouble("ok")).isEqualTo(1.0);
        }
    }
}
