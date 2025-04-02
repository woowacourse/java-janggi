package janggi.model;

import java.util.Map;

public class OccupiedPositions {
    private final Map<Position, PieceIdentity> positions;

    public OccupiedPositions(Map<Position, PieceIdentity> positions) {
        this.positions = positions;
    }

    public boolean existPosition(Position position) {
        return positions.containsKey(position);
    }

    public PieceIdentity getPieceIdentity(Position position) {
        if (!existPosition(position)) {
            throw new IllegalArgumentException("기물이 존재하지 않는 위치입니다.");
        }
        return positions.get(position);
    }

    public boolean isSameType(Position position, PieceType pieceType) {
        if (!existPosition(position)) {
            throw new IllegalArgumentException("기물이 존재하지 않는 위치입니다.");
        }
        return positions.get(position).getPieceType() == pieceType;

    }

    public boolean existSameColor(Position position, Color color) {
        if (!existPosition(position)) {
            return false;
        }
        return positions.get(position).color() == color;
    }

    public Map<Position, PieceIdentity> getPositions() {
        return positions;
    }
}
