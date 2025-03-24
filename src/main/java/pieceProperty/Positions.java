package pieceProperty;

import java.util.ArrayList;
import java.util.List;
import piece.Piece;

public class Positions {
    private final List<Position> positions;

    public Positions(List<Position> positions) {
        this.positions = new ArrayList<>(positions);
    }

    public void addPosition(Position position) {
        positions.add(position);
    }

    public boolean containsPosition(Piece piece) {
        return positions.stream().anyMatch(piece::isSamePosition);
    }

    public List<Position> getPositions() {
        return positions;
    }

}
