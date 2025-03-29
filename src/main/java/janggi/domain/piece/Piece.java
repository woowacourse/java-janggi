package janggi.domain.piece;

import janggi.domain.position.Position;

import java.util.List;
import java.util.Objects;

public abstract class Piece {

    protected final Side side;
    protected Position position;

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

    public void move(List<Piece> existingPieces, Position destination, Side turn) {
        validateTurn(turn);
        validateMovable(existingPieces, destination);
        position = destination;
    }

    private void validateTurn(Side turn) {
        if (this.side != turn) {
            throw new IllegalArgumentException("상대방 기물을 움직일 수 없습니다.");
        }
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
        if (isKing()) return;
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

    public abstract boolean isKing();

    public abstract boolean isCannon();

    public abstract double getScore();

    public boolean isEnemy(Side turn) {
        return turn != this.side;
    }

    @Override
    public String toString() {
        return "Piece{" +
                "side=" + side +
                ", position=" + position +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Piece piece)) {
            return false;
        }
        return side == piece.side && Objects.equals(position, piece.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(side, position);
    }
}
