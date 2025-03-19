package janggi.piece;

import janggi.Position;
import janggi.Side;
import java.util.List;

public abstract class Piece {
    Side side;

    public Piece(Side side) {
        this.side = side;
    }

    public Side getSide() {
        return side;
    }

    public abstract List<Position> calculatePath(Position start, Position end);
}
