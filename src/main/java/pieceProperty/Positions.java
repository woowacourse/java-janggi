package pieceProperty;

import java.util.ArrayList;
import java.util.List;

public class Positions {
    private final List<Position> positions;

    public Positions(final List<Position> positions) {
        this.positions = new ArrayList<>(positions);
    }

    public void addPosition(final Position position) {
        positions.add(position);
    }

    public boolean contains(final Position position) {
        return positions.contains(position);
    }

    public List<Position> getPositions() {
        return positions;
    }

}
