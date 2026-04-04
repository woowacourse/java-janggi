package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.board.BoardChecker;

public record Piece(PieceStrategy pieceStrategy, Camp camp) {

    public void validateMove(Position source, Position destination, BoardChecker board) {
        pieceStrategy.validateMove(source, destination, camp, board);
    }

    public boolean isSamePieceRule(PieceStrategy pieceStrategy) {
        return this.pieceStrategy == pieceStrategy;
    }

    public boolean isSameCamp(Camp camp) {
        return this.camp == camp;
    }
}
