package domain.strategy;

import domain.Position;
import domain.Piece;

public class SaMoveRule implements MoveRule {

    @Override
    public boolean canMovePosition(Position start, Position end, Piece piece) {
        int diffX = end.getX() - start.getX();
        int diffY = end.getY() - start.getY();

        return Math.abs(diffX) + Math.abs(diffY) == 1;
    }
}
