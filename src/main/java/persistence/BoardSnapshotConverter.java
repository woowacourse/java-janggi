package persistence;

import domain.common.Position;
import domain.common.Side;
import domain.board.Board;
import domain.piece.Piece;
import domain.piece.PieceFactory;
import domain.piece.PieceType;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.function.Function;

public class BoardSnapshotConverter {
    private static final Map<PieceType, Function<Side, Piece>> PIECE_CREATORS = createPieceCreators();

    public String serialize(Map<Position, Piece> board) {
        StringJoiner joiner = new StringJoiner(";");
        board.entrySet().stream()
                .sorted((first, second) -> comparePosition(first.getKey(), second.getKey()))
                .forEach(entry -> joiner.add(serializeEntry(entry.getKey(), entry.getValue())));
        return joiner.toString();
    }

    public Board deserialize(String snapshot) {
        if (snapshot == null || snapshot.isBlank()) {
            throw new IllegalArgumentException("보드 스냅샷이 비어 있습니다.");
        }

        Map<Position, Piece> board = new HashMap<>();
        String[] entries = snapshot.split(";");
        for (String entry : entries) {
            String[] tokens = entry.split(",");
            if (tokens.length != 4) {
                throw new IllegalArgumentException("보드 스냅샷 형식이 올바르지 않습니다.");
            }
            Position position = Position.of(parseInt(tokens[0]), parseInt(tokens[1]));
            Side side = Side.valueOf(tokens[2]);
            PieceType pieceType = PieceType.valueOf(tokens[3]);
            Piece piece = createPiece(pieceType, side);

            Piece previous = board.put(position, piece);
            if (previous != null) {
                throw new IllegalArgumentException("보드 스냅샷에 중복 좌표가 존재합니다.");
            }
        }

        return new Board(board);
    }

    private int comparePosition(Position first, Position second) {
        List<Integer> firstPosition = first.getPosition();
        List<Integer> secondPosition = second.getPosition();
        int firstY = firstPosition.getLast();
        int secondY = secondPosition.getLast();
        if (firstY != secondY) {
            return Integer.compare(firstY, secondY);
        }
        return Integer.compare(firstPosition.getFirst(), secondPosition.getFirst());
    }

    private String serializeEntry(Position position, Piece piece) {
        List<Integer> coordinates = position.getPosition();
        return String.format(
                "%d,%d,%s,%s",
                coordinates.getFirst(),
                coordinates.getLast(),
                piece.getSide().name(),
                piece.getPieceType().name()
        );
    }

    private int parseInt(String value) {
        return Integer.parseInt(value);
    }

    private Piece createPiece(PieceType pieceType, Side side) {
        Function<Side, Piece> pieceCreator = PIECE_CREATORS.get(pieceType);
        if (pieceCreator == null) {
            throw new IllegalArgumentException("지원하지 않는 기물 타입입니다. pieceType=" + pieceType);
        }
        return pieceCreator.apply(side);
    }

    private static Map<PieceType, Function<Side, Piece>> createPieceCreators() {
        Map<PieceType, Function<Side, Piece>> creators = new EnumMap<>(PieceType.class);
        creators.put(PieceType.GENERAL, PieceFactory::createGeneral);
        creators.put(PieceType.CHARIOT, PieceFactory::createChariot);
        creators.put(PieceType.CANNON, PieceFactory::createCannon);
        creators.put(PieceType.HORSE, PieceFactory::createHorse);
        creators.put(PieceType.ELEPHANT, PieceFactory::createElephant);
        creators.put(PieceType.GUARD, PieceFactory::createGuard);
        creators.put(PieceType.SOLDIER, PieceFactory::createSoldier);
        return creators;
    }
}
