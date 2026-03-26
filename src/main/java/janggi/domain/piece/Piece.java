package janggi.domain.piece;

import janggi.domain.Position;
import java.util.List;
import java.util.Objects;

public class Piece {

    private final PieceRule pieceRule;
    private final Camp camp;

    public Piece(PieceRule pieceRule, Camp camp) {
        this.pieceRule = pieceRule;
        this.camp = camp;
    }

    public boolean canMove(Position from, Position to) {
        List<Position> path = pieceRule.findPath(from, to, camp);
        return true;
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
