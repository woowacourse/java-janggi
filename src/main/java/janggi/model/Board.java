package janggi.model;

import janggi.model.piece.Piece;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Board {
    private final Map<Position, Piece> pieces = new HashMap<>();

    public void move(Position departure, Position destination) {
        Piece piece = pieces.get(departure);
        pieces.remove(departure);
        pieces.put(destination, piece);
    }

    public Piece findPieceByPositionAndColor(Position position, Color color) {
        validateExistPiecePosition(position);
        Piece piece = pieces.get(position);
        validateSameColorPiece(color, piece);
        return pieces.get(position);
    }

    public void  putPiece(Position position, Piece piece) {
        pieces.put(position, piece);
    }

    public OccupiedPositions generateOccupiedPositions() {
        return new OccupiedPositions(convertOccupiedMap());
    }

    public double calculateScore(Color color) {
        return pieces.values().stream()
                .filter(piece -> piece.isEqualsColor(color))
                .mapToDouble(Piece::getScore).sum();
    }

    private void validateSameColorPiece(Color color, Piece piece) {
        if (piece.identity().color() != color) {
            throw new IllegalArgumentException("같은 팀의 기물이 아닙니다.");
        }
    }

    private void validateExistPiecePosition(Position departure) {
        if (!pieces.containsKey(departure)) {
            throw new IllegalArgumentException("기물이 존재하지 않는 위치입니다.");
        }
    }

    private Map<Position, PieceIdentity> convertOccupiedMap() {
        return pieces.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().identity()));
    }
}
