package domain.strategy;

import domain.Position;
import domain.Piece;

public class SangMoveRule implements MoveRule {

    @Override
    public boolean canMovePosition(Position start, Position end, Piece piece) {
        int diffRow = end.getRow() - start.getRow();
        int diffCol = end.getCol() - start.getCol();

        return (Math.abs(diffRow) == 3 && Math.abs(diffCol) == 2) || (Math.abs(diffRow) == 2 && Math.abs(diffCol) == 3);
    }
}
