package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.board.Board;
import java.util.List;
import java.util.Objects;

public class Piece {

    private final PieceRule pieceRule;
    private final Camp camp;

    public Piece(PieceRule pieceRule, Camp camp) {
        this.pieceRule = pieceRule;
        this.camp = camp;
    }

    public void validateMove(Position from, Position to, Board board) {
        List<Position> path = pieceRule.findPath(from, to, camp);
        pieceRule.checkPath(path, camp, board);
    }

    public boolean isSamePieceRule(PieceRule pieceRule) {
        return this.pieceRule == pieceRule;
    }

    public boolean isSameCamp(Camp camp) {
        return this.camp == camp;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Piece piece = (Piece) o;
        return pieceRule == piece.pieceRule && camp == piece.camp;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceRule, camp);
    }
}
