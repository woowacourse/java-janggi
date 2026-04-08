package domain.strategy;

import domain.constant.Country;
import domain.Position;
import domain.Piece;

public class JolMoveRule implements MoveRule {

    @Override
    public boolean canMovePosition(Position start, Position end, Piece piece) {
        int diffRow = end.getRow() - start.getRow();
        int diffCol = end.getCol() - start.getCol();
        if (Math.abs(diffRow) + Math.abs(diffCol) != 1) {
            return false;
        }
        if (piece.getCountry().equals(Country.CHO)) {
            return diffRow >= 0;
        }
        return diffRow <= 0;
    }
}
