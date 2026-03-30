package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.board.BoardChecker;
import java.util.List;

public record Piece(PieceType pieceType, Camp camp) {

    public void validateMove(Position source, Position destination, BoardChecker board) {
        List<Position> path = pieceType.findPath(source, destination, camp);
        pieceType.checkPath(path, camp, board);
    }

    public boolean isSamePieceRule(PieceType pieceType) {
        return this.pieceType == pieceType;
    }

    public boolean isSameCamp(Camp camp) {
        return this.camp == camp;
    }
}
