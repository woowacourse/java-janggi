package janggi.domain;

import janggi.common.ErrorMessage;
import janggi.domain.piece.Side;
import java.util.Objects;
import java.util.Set;

public class PieceState {

    private final Piece piece;
    private Position position;

    public PieceState(Position position, Piece piece) {
        this.position = position;
        this.piece = piece;
    }

    public String toName() {
        return piece.toName();
    }

    public boolean isSameSide(Side side) {
        return piece.isSameSide(side);
    }

    public boolean isSameSide(PieceState other) {
        return piece.isSameSide(other.piece);
    }

    public void move(Position movePosition) {
        Set<Position> availableMovePositions = getAvailableMovePositions();

        if (!availableMovePositions.contains(movePosition)) {
            throw new IllegalArgumentException(ErrorMessage.CANNOT_MOVE_PIECE.getMessage());
        }

        updatePosition(movePosition);
    }

    public Set<Position> getAvailableMovePositions() {
        return piece.availableMovePositions(position);
    }

    public void updatePosition(Position movePosition) {
        this.position = position.update(movePosition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, piece);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PieceState that = (PieceState) o;
        return Objects.equals(position, that.position) && Objects.equals(piece, that.piece);
    }

    @Override
    public String toString() {
        return "PiecePosition{" +
                "position=" + position +
                ", piece=" + piece +
                '}';
    }

    public Position getPosition() {
        return position;
    }
}
