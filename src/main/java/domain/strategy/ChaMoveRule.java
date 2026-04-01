package domain.strategy;

import domain.Position;
import domain.Piece;

public class ChaMoveRule implements MoveRule {

    @Override
    public boolean canMovePosition(Position start, Position end, Piece piece) {
        return start.getX() == end.getX() || start.getY() == end.getY();
    }
}
