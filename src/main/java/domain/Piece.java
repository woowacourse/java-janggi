package domain;

import java.util.List;
import java.util.Map;

public abstract class Piece {
    protected final Side side;

    public Piece(Side side) {
        this.side = side;
    }

    public boolean isSameSideAs(Side other) {
        return side.isSameAs(other);
    }

    public Side getSide() {
        return side;
    }

    public abstract List<Position> getAllPosition(Position position);

    public abstract List<Position> getPossibleDestinations(Position position, Map<Position, Piece> map);
}
