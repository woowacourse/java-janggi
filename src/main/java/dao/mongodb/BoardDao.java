package dao.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import dto.PieceDto;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.bson.types.ObjectId;

public class BoardDao {

    private final MongoCollection<Document> collection;

    public BoardDao(MongoConnection connection) {
        this.collection = connection.getCollection("board");
    }

    public String save(List<PieceDto> pieces, int turnCount) {
        ArrayList<Document> pieceDocuments = new ArrayList<>();

        for (PieceDto pieceDto : pieces) {
            pieceDocuments.add(toDocument(pieceDto));
        }

        Document gameDocument = getDocument(turnCount, pieceDocuments);

        collection.insertOne(gameDocument);
        return gameDocument.getObjectId("_id").toString();
    }

    public List<PieceDto> findPiecesByGameId(String gameId) {
        Document gameDocument = findGameDocument(gameId);

        if (gameDocument.getBoolean("isFinished")) {
            throw new IllegalArgumentException("[ERROR] 이미 종료된 게임입니다: " + gameId);
        }

        List<Document> pieces = gameDocument.getList("pieces", Document.class);
        List<PieceDto> pieceDtos = new ArrayList<>();

        for (Document doc : pieces) {
            pieceDtos.add(toPieceDto(doc));
        }

        return pieceDtos;
    }

    public int findTurnCountByGameId(String gameId) {
        Document gameDocument = findGameDocument(gameId);
        return gameDocument.getInteger("turnCount");
    }

    public void update(String gameId, List<PieceDto> pieces, int turnCount) {
        ArrayList<Document> pieceDocuments = new ArrayList<>();

        for (PieceDto pieceDto : pieces) {
            pieceDocuments.add(toDocument(pieceDto));
        }

        Document updateFields = new Document("$set", new Document()
                .append("pieces", pieceDocuments)
                .append("turnCount", turnCount));

        collection.updateOne(
                Filters.eq("_id", new ObjectId(gameId)),
                updateFields
        );
    }

    public void finish(String gameId) {
        Document updateFields = new Document("$set", new Document("isFinished", true));

        collection.updateOne(
                Filters.eq("_id", new ObjectId(gameId)),
                updateFields
        );
    }

    private Document findGameDocument(String gameId) {
        Document gameDocument = collection.find(Filters.eq("_id", new ObjectId(gameId))).first();
        if (gameDocument == null) {
            throw new IllegalArgumentException("[ERROR] 해당 게임을 찾을 수 없습니다: " + gameId);
        }
        return gameDocument;
    }

    private Document getDocument(int turnCount, ArrayList<Document> pieceDocuments) {
        return new Document()
                .append("pieces", pieceDocuments)
                .append("turnCount", turnCount)
                .append("isFinished", false);
    }

    private Document toDocument(PieceDto pieceDto) {
        return new Document()
                .append("row", pieceDto.row())
                .append("col", pieceDto.col())
                .append("team", pieceDto.team())
                .append("type", pieceDto.type());
    }

    private PieceDto toPieceDto(Document doc) {
        return new PieceDto(
                doc.getInteger("row"),
                doc.getInteger("col"),
                doc.getString("team"),
                doc.getString("type")
        );
    }
}