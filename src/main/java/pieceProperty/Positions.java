package pieceProperty;

import java.util.List;

public class Positions {
    private final List<Position> positions;

    public Positions(List<Position> positions) {
        this.positions = positions;
    }

    public void addPosition(Position position) {
        positions.add(position);
    }
    public List<Position> getPositions() {
        return positions;
    }
}
