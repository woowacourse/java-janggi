package janggi.domain;

import janggi.domain.piece.PieceType;

public record MoveResult(PieceType capturedPieceType) {
    public boolean isCapturedGung() {
        return capturedPieceType == PieceType.GUNG;
    }

    public int getCapturedPieceScore() {
        return capturedPieceType.getScore();
    }
}
