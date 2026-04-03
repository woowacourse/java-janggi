package domain.strategy;

import domain.Position;
import domain.Piece;
import domain.constant.Palace;

public class ChaMoveRule implements MoveRule {

    @Override
    public boolean canMovePosition(Position start, Position end, Piece piece) {
        if (start.getX() == end.getX() || start.getY() == end.getY()) {
            return true;
        }
        Palace palace = Palace.from(piece.getCountry());
        return palace.isDiagonalPath(start, end);
    }
}
