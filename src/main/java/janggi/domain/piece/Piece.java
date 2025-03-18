package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.Side;

public abstract class Piece {

    private final Side side;
    private Position position;

    protected Piece(Side side, Position position) {
        this.side = side;
        this.position = position;
    }

    public final Side getSide() {
        return side;
    }

    public final Position getPosition() {
        return position;
    }

    public void move(int x, int y) {
        validateMovable(x, y);
        position = position.moveTo(x, y);
    }

    protected abstract void validateMovable(int x, int y);
}
