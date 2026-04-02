package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.board.BoardChecker;

public record Piece(PieceRule pieceRule, Camp camp) {

    public void validateMove(Position source, Position destination, BoardChecker board) {
        pieceRule.validateMove(source, destination, camp, board);
    }

    public boolean isSamePieceRule(PieceRule pieceRule) {
        return this.pieceRule == pieceRule;
    }

    public boolean isSameCamp(Camp camp) {
        return this.camp == camp;
    }
}
