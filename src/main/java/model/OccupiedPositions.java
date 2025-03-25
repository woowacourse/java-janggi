package model;

import java.util.Map;

public class OccupiedPositions {
    private final Map<Position, PieceIdentity> positions;

    public OccupiedPositions(Map<Position, PieceIdentity> positions) {
        this.positions = positions;
    }

    public boolean existPosition(Position position) {
        return positions.containsKey(position);
    }

    public boolean existSameColor(Position position, Color color) {
        if (!existPosition(position)) {
            return false;
        }
        return positions.get(position).getColor() == color;
    }
}
