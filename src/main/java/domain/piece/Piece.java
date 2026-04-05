package domain.piece;

import domain.piece.strategy.MovingCondition;

import java.util.Objects;

public final class Piece {

    private final PieceType pieceType;
    private final Side side;

    private Piece(PieceType pieceType, Side side) {
        this.pieceType = pieceType;
        this.side = side;
    }

    public static Piece of(Side side, PieceType pieceType) {
        return new Piece(pieceType, side);
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public Side getSide() {
        return side;
    }

    public String getTypeName() {
        return pieceType.getName();
    }

    public MovingCondition getMovingCondition() {
        return pieceType.getMovingCondition();
    }

    public boolean isSameSide(Side side) {
        return side == this.side;
    }

    public boolean isSamePieceType(PieceType pieceType) {
        return this.pieceType == pieceType;
    }

    public double getPieceValue() {
        return pieceType.getPieceValue();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Piece piece)) return false;
        return Objects.equals(side, piece.side) &&
                Objects.equals(pieceType, piece.pieceType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(side) + Objects.hash(pieceType);
    }
}
