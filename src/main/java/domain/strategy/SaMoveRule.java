package domain.strategy;

import domain.Position;
import domain.Piece;

public class SaMoveRule implements MoveRule {

    @Override
    public boolean canMovePosition(Position start, Position end, Piece piece) {
        int diffRow = Math.abs(end.getRow() - start.getRow());
        int diffCol = Math.abs(end.getCol() - start.getCol());

        boolean isStraight = (diffRow + diffCol) == 1;
        boolean isDiagonal = (diffRow == 1 && diffCol == 1);

        return isStraight || isDiagonal;
    }
}
