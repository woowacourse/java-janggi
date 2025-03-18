package janggi.domain;

import janggi.common.ErrorMessage;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Side;
import java.util.List;
import java.util.Objects;

public class PiecePosition {

    private final Position position;
    private final Piece piece;

    public PiecePosition(Position position, Piece piece) {
        this.position = position;
        this.piece = piece;
    }

    public String toName() {
        return piece.toName();
    }

    public boolean isSameSide(Side side) {
        return piece.isSameSide(side);
    }

    public void move(Position movePosition) {
        List<Position> availableMovePositions = piece.availableMovePositions(position);

        if (!availableMovePositions.contains(movePosition)) {
            throw new IllegalArgumentException(ErrorMessage.CANNOT_MOVE_PIECE.getMessage());
        }

        position.update(movePosition);
        System.out.println(position);
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
        PiecePosition that = (PiecePosition) o;
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
