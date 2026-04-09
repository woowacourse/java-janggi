package dao;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import domain.Board;
import domain.Piece;
import domain.Team;
import domain.Type;
import domain.vo.Position;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bson.Document;
import org.bson.types.ObjectId;

public class BoardDao {

    private final MongoCollection<Document> collection;

    public BoardDao() {
        MongoClient client = MongoClients.create("mongodb://admin:password123@localhost:27017");
        MongoDatabase database = client.getDatabase("janggi");
        this.collection = database.getCollection("board");
    }

    public String save(Board board, int turnCount) {
        ArrayList<Document> pieces = new ArrayList<>();

        for (Map.Entry<Position, Piece> entry : board.getBoard().entrySet()) {
            Position position = entry.getKey();
            Piece piece = entry.getValue();

            Document document = new Document()
                    .append("row", position.getRow())
                    .append("col", position.getCol())
                    .append("team", piece.getTeamName())
                    .append("type", piece.getTypeName());
            pieces.add(document);
        }

        Document gameDocument = new Document()
                .append("pieces", pieces)
                .append("turnCount", turnCount);

        collection.insertOne(gameDocument);
        return gameDocument.getObjectId("_id").toString();
    }

    public Board findBoardByGameId(String gameId) {
        Document gameDocument = collection.find(Filters.eq("_id", new ObjectId(gameId))).first();
        if (gameDocument == null) {
            throw new IllegalArgumentException("[ERROR] 해당 게임을 찾을 수 없습니다: " + gameId);
        }

        List<Document> pieces = gameDocument.getList("pieces", Document.class);
        HashMap<Position, Piece> boardMap = new HashMap<>();

        for (Document doc : pieces) {
            Position position = Position.of(doc.getInteger("row"), doc.getInteger("col"));
            Team team = findTeamByName(doc.getString("team"));
            Type type = findTypeByName(doc.getString("type"));
            boardMap.put(position, Piece.of(team, type));
        }

        return Board.of(boardMap);
    }

    public void update(String gameId, Board board, int turnCount) {
        ArrayList<Document> pieces = new ArrayList<>();
        for (Map.Entry<Position, Piece> entry : board.getBoard().entrySet()) {
            Position position = entry.getKey();
            Piece piece = entry.getValue();

            Document document = new Document()
                    .append("row", position.getRow())
                    .append("col", position.getCol())
                    .append("team", piece.getTeamName())
                    .append("type", piece.getTypeName());

            pieces.add(document);
        }

        Document updateDoc = new Document()
                .append("pieces", pieces)
                .append("turnCount", turnCount);

        collection.replaceOne(
                Filters.eq("_id", new ObjectId(gameId)),
                updateDoc
        );
    }

    public int findTurnCountByGameId(String gameId) {
        Document gameDocument = collection.find(Filters.eq("_id", new ObjectId(gameId))).first();
        if (gameDocument == null) {
            throw new IllegalArgumentException("[ERROR] 해당 게임을 찾을 수 없습니다: " + gameId);
        }

        return gameDocument.getInteger("turnCount");
    }

    private Team findTeamByName(String name) {
        for (Team team : Team.values()) {
            if (team.getName().equals(name)) {
                return team;
            }
        }

        throw new IllegalArgumentException("[ERROR] 알 수 없는 팀입니다: " + name);
    }

    private Type findTypeByName(String name) {
        for (Type type : Type.values()) {
            if (type.getName().equals(name)) {
                return type;
            }
        }

        throw new IllegalArgumentException("[ERROR] 알 수 없는 기물입니다: " + name);
    }
}
