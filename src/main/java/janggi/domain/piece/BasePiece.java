package janggi.domain.piece;

import janggi.domain.Side;

public abstract class BasePiece implements Piece {
    protected final Side side;
    private final PieceType pieceType;

    public BasePiece(Side side, PieceType pieceType) {
        this.side = side;
        this.pieceType = pieceType;
    }

    @Override
    public boolean isEqualPieceType(PieceType pieceType) {
        return this.pieceType == pieceType;
    }

    @Override
    public boolean isEqualSide(Side side) {
        return this.side == side;
    }

    @Override
    public PieceAttribute getPieceInfo() {
        return new PieceAttribute(side, pieceType);
    }

    @Override
    public int getPieceScore() {
        return this.pieceType.getScore();
    }
}
