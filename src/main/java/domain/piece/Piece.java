package domain.piece;

import domain.board.Intersection;
import domain.game.Side;
import java.util.List;

public final class Piece {

    public static final Piece EMPTY = new Piece(PieceType.EMPTY, Side.NONE);

    private final PieceType type;
    private final Side side;

    private Piece(PieceType type, Side side) {
        this.type = type;
        this.side = side;
    }

    public static Piece of(PieceType type, Side side) {
        if (type == PieceType.EMPTY) {
            return EMPTY;
        }

        return new Piece(type, side);
    }

    public List<Intersection> movableDestinations(Intersection from, AlivePieces alivePieces) {
        return type.movableDestinations(side, from, alivePieces);
    }

    public boolean isSameSide(Side side) {
        return this.side == side;
    }

    public boolean isDifferentSide(Side side) {
        return this.side != side;
    }

    public boolean isDifferentSide(Piece otherPiece) {
        return this.side != otherPiece.side;
    }

    public boolean isSameType(PieceType pieceType) {
        return this.type == pieceType;
    }

    public boolean isNotSameType(PieceType pieceType) {
        return !isSameType(pieceType);
    }

    public int toPoint() {
        return type.point();
    }

    public PieceType getType() {
        return type;
    }

    public Side getSide() {
        return side;
    }

    @Override
    public String toString() {
        return "Piece{" +
                "type=" + type +
                ", side=" + side +
                '}';
    }
}
