package janggi.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import janggi.model.piece.Piece;

public class Board {
    private final Map<Position, Piece> pieces = new HashMap<>();

    public void move(Position departure, Position destination, Color currentTurn) {
        validateExistPiecePosition(departure);
        Piece piece = pieces.get(departure);
        validateSameTeam(currentTurn, piece);
        validateMovablePosition(destination, piece.calculateMovablePositions(departure, generateOccupiedPositions()));
        pieces.remove(departure);
        pieces.put(destination, piece);
    }

    public OccupiedPositions generateOccupiedPositions() {
        return new OccupiedPositions(convertOccupiedMap());
    }

    public void  putPiece(Position position, Piece piece) {
        pieces.put(position, piece);
    }

    private static void validateSameTeam(Color currentTurn, Piece piece) {
        if (piece.identity().getColor() != currentTurn) {
            throw new IllegalArgumentException("움직일 수 없는 기물입니다.");
        }
    }

    private void validateExistPiecePosition(Position departure) {
        if (!pieces.containsKey(departure)) {
            throw new IllegalArgumentException("기물이 존재하지 않는 위치입니다.");
        }
    }

    private void validateMovablePosition(Position destination, Set<Position> movablePositions) {
        if (!movablePositions.contains(destination)) {
            throw new IllegalArgumentException("이동할 수 없는 위치입니다.");
        }
    }

    private Map<Position, PieceIdentity> convertOccupiedMap() {
        return pieces.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().identity()));
    }
}
