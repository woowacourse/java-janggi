package domain.piece;

import domain.piece.strategy.MovingCondition;
import domain.position.Position;

import java.util.Map;
import java.util.Objects;

public final class Piece {

    private final PieceType pieceType;
    private final Side side;
    private final MovingCondition movingCondition;

    private Piece(PieceType pieceType, Side side, MovingCondition movingCondition) {
        this.pieceType = pieceType;
        this.side = side;
        this.movingCondition = movingCondition;
    }

    public static Piece of(Side side, PieceType pieceType) {
        return new Piece(pieceType, side, pieceType.getMovingCondition());
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

    public boolean canMove(Map<Position, Piece> pieceMap, Position startPosition, Position endPosition) {
        return movingCondition.canMove(pieceMap, startPosition, endPosition);
    }

    public boolean isSameSide(Side side) {
        return side == this.side;
    }

    public boolean isSamePieceType(PieceType pieceType) {
        return this.pieceType == pieceType;
    }

    public int getPieceScore() {
        return pieceType.getScore();
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

    public boolean isGeneral() {
        return pieceType == PieceType.GENERAL;
    }
}
