package domain.strategy;

import domain.Position;
import domain.Piece;

public class SangMoveRule implements MoveRule {

    @Override
    public boolean canMovePosition(Position start, Position end, Piece piece) {
        int diffX = end.getX() - start.getX();
        int diffY = end.getY() - start.getY();

        return (Math.abs(diffX) == 3 && Math.abs(diffY) == 2) || (Math.abs(diffX) == 2 && Math.abs(diffY) == 3);
    }
}
