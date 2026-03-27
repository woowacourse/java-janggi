package domain.piece.move;

import domain.piece.PieceType;

public abstract class MoveRule {

    /**
     * 어떤 피스의 이동 전략인지.
     * */

    protected final PieceType pieceType;
    protected final Directions directions;

    public MoveRule(PieceType pieceType, Directions directions) {
        this.pieceType = pieceType;
        this.directions = directions;
    }

}
