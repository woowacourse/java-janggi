package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import model.piece.Piece;

public class Board {
    private final Map<Position, Piece> pieces = new HashMap<>();

    public void move(Position departure, Position destination) {
        validateExistPiecePosition(departure);
        Piece piece = pieces.get(departure);
        validateMovablePosition(destination, piece.calculateMovablePositions(departure, generateOccupiedPositions()));
        pieces.remove(departure);
        pieces.put(destination, piece);
    }

    public void validateExistPiecePosition(Position departure) {
        if (!pieces.containsKey(departure)) {
            throw new IllegalArgumentException("기물이 존재하지 않는 위치입니다.");
        }
    }
    
    public void validateMovablePosition(Position destination, Set<Position> movablePositions) {
        if (!movablePositions.contains(destination)) {
            throw new IllegalArgumentException("이동할 수 없는 위치입니다.");
        }
    }

    public OccupiedPositions generateOccupiedPositions() {
        return new OccupiedPositions(convertOccupiedMap());
    }

    public void putPiece(Position position, Piece piece) {
        pieces.put(position, piece);
    }

    private Map<Position, PieceIdentity> convertOccupiedMap() {
        return pieces.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().identity()));
    }
}
