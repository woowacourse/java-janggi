package domain.pieces;

import domain.enums.Country;
import domain.enums.PieceType;
import domain.Position;
import java.util.List;

public class Sang extends Piece {

    public Sang(Country country) {
        super(country, PieceType.SANG);
    }

    @Override
    public boolean canMovePosition(Position start, Position end) {
        int diffX = end.getX() - start.getX();
        int diffY = end.getY() - start.getY();

        return (Math.abs(diffX) == 3 && Math.abs(diffY) == 2) || (Math.abs(diffX) == 2 && Math.abs(diffY) == 3);
    }

    @Override
    public boolean isAvailableRoute(List<Piece> pieces, PieceType endPieceType) {
        return true;
    }
}
