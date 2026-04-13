package repository;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import domain.Game;
import domain.Position;
import domain.Side;
import domain.Turn;
import domain.piece.Piece;
import domain.piece.PieceType;
import org.bson.Document;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static constant.BoardConstant.*;

public class MongoGameRepository implements GameRepository, AutoCloseable {

    private static final String URI = "mongodb://localhost:27017";
    private static final String DB_NAME = "janggi_db";
    private static final String COLLECTION_NAME = "boards";

    private static final Map<String, PieceType> NAME_TO_TYPE = Map.ofEntries(
            Map.entry("卒", PieceType.SOLDIER),
            Map.entry("兵", PieceType.SOLDIER),
            Map.entry("象", PieceType.ELEPHANT),
            Map.entry("车", PieceType.CHARIOT),
            Map.entry("車", PieceType.CHARIOT),
            Map.entry("士", PieceType.GUARD),
            Map.entry("楚", PieceType.KING),
            Map.entry("漢", PieceType.KING),
            Map.entry("包", PieceType.CANNON),
            Map.entry("马", PieceType.HORSE),
            Map.entry("馬", PieceType.HORSE),
            Map.entry("．", PieceType.EMPTY)
    );

    private final MongoClient client;
    private final MongoCollection<Document> collection;

    public MongoGameRepository() {
        this.client = MongoClients.create(URI);
        this.collection = client.getDatabase(DB_NAME).getCollection(COLLECTION_NAME);
    }

    @Override
    public void close() {
        client.close();
    }

    @Override
    public void saveGame(Game game) {
        Document doc = createGameDocument(game.getId(), game.getTurn(), game.getBoard());
        collection.replaceOne(Filters.eq("_id", game.getId()), doc, new ReplaceOptions().upsert(true));
    }

    @Override
    public void deleteBoard(Long gameId) {
        collection.deleteOne(Filters.eq("_id", gameId));
    }

    @Override
    public Optional<GameStatus> findBoard(String gameId) {
        Long id = Long.parseLong(gameId);
        Document document = collection.find(Filters.eq("_id", id)).first();
        return Optional.ofNullable(parseGameStatus(id, document));
    }

    private Document createGameDocument(Long gameId, Turn turn, Map<Position, Piece> board) {
        Document boardDoc = new Document();
        for (Map.Entry<Position, Piece> entry : board.entrySet()) {
            String key = entry.getKey().createStoreKey();
            Document pieceDoc = createPieceDocument(entry.getValue());
            boardDoc.append(key, pieceDoc);
        }
        return new Document("_id", gameId)
                .append("turn", turn.current().name())
                .append("layout", boardDoc);
    }

    private Document createPieceDocument(Piece piece) {
        return new Document("type", piece.getName()).append("side", piece.getSide().name());
    }

    private GameStatus parseGameStatus(Long id, Document document) {
        if (document == null) {
            return null;
        }
        Document layout = (Document) document.get("layout");
        Map<Position, Piece> board = parseBoard(layout);
        Turn turn = new Turn(Side.valueOf(document.getString("turn")));
        return GameStatus.of(id, board, turn);
    }

    private Map<Position, Piece> parseBoard(Document layout) {
        Map<Position, Piece> board = new HashMap<>();
        for (int x = MIN_X; x <= MAX_X; x++) {
            parseBoardColumn(layout, board, x);
        }
        return board;
    }

    private void parseBoardColumn(Document layout, Map<Position, Piece> board, int x) {
        for (int y = MIN_Y; y <= MAX_Y; y++) {
            Document pieceDoc = (Document) layout.get(x + " " + y);
            Piece piece = parsePiece(pieceDoc);
            board.put(Position.of(x, y), piece);
        }
    }

    private Piece parsePiece(Document pieceDoc) {
        PieceType type = NAME_TO_TYPE.get(pieceDoc.getString("type"));
        Side side = Side.valueOf(pieceDoc.getString("side"));
        return type.create(side);
    }
}
