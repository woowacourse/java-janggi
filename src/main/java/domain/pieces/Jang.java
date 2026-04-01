package domain.pieces;

import java.util.List;

import domain.enums.Country;
import domain.enums.PieceType;
import domain.Position;

public class Jang extends Piece {

    public Jang(Country country) {
        super(country,PieceType.JANG);
    }

    @Override
    public boolean canMovePosition(Position start, Position end) {
        int diffX = end.getX() - start.getX();
        int diffY = end.getY() - start.getY();

        return Math.abs(diffX) + Math.abs(diffY) == 1;
    }

    @Override
    public boolean isAvailableRoute(List<Piece> pieces, PieceType endPieceType) {
        return true;
    }
}
