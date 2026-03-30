package pieces;

import movepolicy.MoveContext;
import position.Position;

public record EmptyPiece() implements Piece {

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public boolean isHan() {
        return false;
    }

    @Override
    public boolean isCho() {
        return false;
    }

    @Override
    public boolean isSameSide(Piece other) {
        return false;
    }

    @Override
    public boolean isPo() {
        return false;
    }

    @Override
    public MoveContext askMoveContext(Position departure, Position destination) {
        throw new IllegalArgumentException("해당 위치에 기물이 없습니다.");
    }

    @Override
    public PieceType getType() {
        return PieceType.EMPTY;
    }
}
