package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Board {
    private final Map<Position, Piece> pieces = new HashMap<>();

    public void move(Position selectedPosition, Position destinationPosition) {
        if (!pieces.containsKey(selectedPosition)) {
            throw new IllegalArgumentException("기물이 존재하지 않는 위치입니다.");
        }
        Piece piece = pieces.get(selectedPosition);
        Set<Position> movablePositions = piece.calculateMovablePositions(selectedPosition, generateOccupiedPositions());
        if (!movablePositions.contains(destinationPosition)) {
            throw new IllegalArgumentException("이동할 수 없는 위치입니다.");
        }
        pieces.remove(selectedPosition);
        pieces.put(destinationPosition, piece);
    }

    private OccupiedPositions generateOccupiedPositions() {
        return new OccupiedPositions(pieces.entrySet().stream().collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> entry.getValue().identity()
        )));
    }

    public boolean isExist(Position position) {
        return pieces.containsKey(position);
    }

    public void putPiece(Position position, Piece piece) {
        pieces.put(position, piece);
    }
}
