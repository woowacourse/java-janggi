package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.board.BoardChecker;
import java.util.List;

public record Piece(PieceRule pieceRule, Camp camp) {

    public void validateMove(Position from, Position to, BoardChecker board) {
        List<Position> path = pieceRule.findPath(from, to, camp);
        pieceRule.checkPath(path, camp, board);
    }

    public boolean isSamePieceRule(PieceRule pieceRule) {
        return this.pieceRule == pieceRule;
    }

    public boolean isSameCamp(Camp camp) {
        return this.camp == camp;
    }
}
