package domain.piece;

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Piece piece)) return false;
        return Objects.equals(side, ((Piece) obj).side) && Objects.equals(pieceType, ((Piece) obj).pieceType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(side) + Objects.hash(pieceType);
    }
}
