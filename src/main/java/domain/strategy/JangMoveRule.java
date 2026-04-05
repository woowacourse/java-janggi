package domain.strategy;

import domain.Position;
import domain.Piece;

public class JangMoveRule implements MoveRule {

    @Override
    public boolean canMovePosition(Position start, Position end, Piece piece) {
        int diffX = Math.abs(end.getX() - start.getX());
        int diffY = Math.abs(end.getY() - start.getY());

        boolean isStraight = (diffX + diffY) == 1;
        boolean isDiagonal = (diffX == 1 && diffY == 1);

        return isStraight || isDiagonal;
    }
}
