package pieceProperty;

import java.util.ArrayList;
import java.util.List;
import piece.Piece;

public class Positions {
    private final List<Position> positions;

    public Positions(final List<Position> positions) {
        this.positions = new ArrayList<>(positions);
    }

    public void addPosition(final Position position) {
        positions.add(position);
    }

    public boolean containsPosition(final Piece piece) {
        return positions.stream().anyMatch(piece::isSamePosition);
    }

    public List<Position> getPositions() {
        return positions;
    }

}
