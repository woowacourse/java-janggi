package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.Side;
import java.util.List;

public abstract class Piece {

    private final Side side;
    private Position position;

    protected Piece(Side side, int x, int y) {
        this.side = side;
        this.position = new Position(x, y);
    }

    public final Side getSide() {
        return side;
    }

    public final Position getPosition() {
        return position;
    }

    public final boolean isSamePosition(Position position) {
        return this.position.equals(position);
    }

    public void move(List<Piece> existingPieces, int x, int y) {
        Position destination = new Position(x, y);
        validateMovable(existingPieces, destination);
        position = destination;
    }

    private void validateMovable(List<Piece> existingPieces, Position destination) {
        validateSamePosition(destination);
        if (!isMoveablePosition(destination)) {
            throw new IllegalArgumentException("해당 위치로 이동할 수 없습니다.");
        }
        if (!isMoveablePath(existingPieces, destination)) {
            throw new IllegalArgumentException("불가능한 경로입니다.");
        }
    }

    private void validateSamePosition(Position destination) {
        if (position.equals(destination)) {
            throw new IllegalArgumentException("현재 위치로 이동할 수 없습니다.");
        }
    }

    public int getXPosition() {
        return position.getX();
    }

    public int getYPosition() {
        return position.getY();
    }

    protected abstract boolean isMoveablePosition(Position destination);

    protected abstract boolean isMoveablePath(List<Piece> existingPieces, Position destination);
}
