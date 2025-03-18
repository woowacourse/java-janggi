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
        validateSamePosition(x, y);
        validateMovable(x, y);
        position = position.moveTo(x, y);
    }

    private void validateSamePosition(int x, int y) {
        if (position.isSameCoordinate(x, y)) {
            throw new IllegalArgumentException("현재 위치로 이동할 수 없습니다.");
        }
    }

    protected abstract void validateMovable(int x, int y);
}
