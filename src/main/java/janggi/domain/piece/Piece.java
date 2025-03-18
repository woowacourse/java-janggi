package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.Side;

import java.util.List;

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

    public void move(List<Piece> existingPieces, int x, int y) {
        validateMovable(existingPieces, x, y);
        position = position.moveTo(x, y);
    }

    private void validateMovable(List<Piece> existingPieces, int x, int y) {
        validateSamePosition(x, y);
        if (!isMoveablePosition(x, y)) {
            throw new IllegalArgumentException("해당 위치로 이동할 수 없습니다.");
        }
        if (!isMoveablePath(existingPieces, x, y)) {
            throw new IllegalArgumentException("불가능한 경로입니다.");
        }
    }

    private void validateSamePosition(int x, int y) {
        if (position.isSameCoordinate(x, y)) {
            throw new IllegalArgumentException("현재 위치로 이동할 수 없습니다.");
        }
    }

    protected abstract boolean isMoveablePosition(int x, int y);

    protected abstract boolean isMoveablePath(List<Piece> existingPieces, int x, int y);
}
