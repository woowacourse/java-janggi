package domain.strategy;

import domain.Country;
import domain.Position;
import domain.Piece;

public class JolMoveRule implements MoveRule {

    @Override
    public boolean canMovePosition(Position start, Position end, Piece piece) {
        int diffX = end.getX() - start.getX();
        int diffY = end.getY() - start.getY();
        if (Math.abs(diffX) + Math.abs(diffY) != 1) {
            return false;
        }
        if (piece.getCountry().equals(Country.CHO)) {
            return diffX >= 0;
        }
        return diffX <= 0;
    }
}
