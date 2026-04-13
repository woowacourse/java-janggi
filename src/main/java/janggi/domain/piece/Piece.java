package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.board.BoardChecker;
import janggi.domain.piece.camp.CampType;

public record Piece(PieceRule pieceRule, CampType campType) {

    public void validateMove(Position source, Position destination, BoardChecker board) {
        pieceRule.validateMove(source, destination, board);
    }

    public boolean isSamePieceRule(PieceRule pieceRule) {
        return this.pieceRule == pieceRule;
    }

    public boolean isSameCampType(CampType campType) {
        return this.campType() == campType;
    }

    public double getScore() {
        return pieceRule.getScore();
    }
}
