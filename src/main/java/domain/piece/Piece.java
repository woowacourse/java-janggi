package domain.piece;

import domain.board.Intersection;
import domain.game.Side;
import domain.move.Path;
import java.util.List;
import java.util.Objects;

public final class Piece {

    private final PieceType type;
    private final Side side;

    public Piece(PieceType type, Side side) {
        this.type = type;
        this.side = side;
    }

    public List<Intersection> movablePaths(Intersection from, AlivePieces alivePieces) {
        List<Path> movablePaths = type.movablePaths(from, side);

        return type.movableDestinations(side, movablePaths, alivePieces);
    }

    public boolean isSameSide(Side side) {
        return this.side == side;
    }

    public boolean hasDifferentSide(Side side) {
        return this.side != side;
    }

    public boolean isSameType(PieceType pieceType) {
        return this.type == pieceType;
    }

    public boolean isNotSameType(PieceType pieceType) {
        return !isSameType(pieceType);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Piece that)) {
            return false;
        }
        return type == that.type && side == that.side;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, side);
    }

    @Override
    public String toString() {
        return "Piece{" +
                "type=" + type +
                ", side=" + side +
                '}';
    }
}
