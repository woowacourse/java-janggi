package janggi.domain.piece;

import janggi.domain.piece.movement.MovementStrategy;
import janggi.domain.piece.pieces.PiecesView;
import java.util.Objects;

public class Piece implements PieceView {

    private final PieceType pieceType;
    private final Side side;
    private final MovementStrategy movementStrategy;
    private Position position;

    public Piece(PieceType pieceType, MovementStrategy movementStrategy, Side side, int x, int y) {
        this.pieceType = pieceType;
        this.side = side;
        this.movementStrategy = movementStrategy;
        this.position = new Position(x, y);
    }

    public void move(PiecesView map, int x, int y) {
        Position destination = new Position(x, y);
        validateMovable(map, destination);
        position = destination;
    }

    private void validateMovable(PiecesView map, Position destination) {
        if (!movementStrategy.isMoveable(map, position, side, destination)) {
            throw new IllegalArgumentException("해당 위치로 이동할 수 없습니다.");
        }
    }

    @Override
    public final Side getSide() {
        return side;
    }

    @Override
    public final Position getPosition() {
        return position;
    }

    @Override
    public final boolean isSamePosition(Position position) {
        return this.position.equals(position);
    }

    @Override
    public PieceType getPieceType() {
        return pieceType;
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
