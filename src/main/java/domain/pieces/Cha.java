package domain.pieces;

import java.util.List;

import domain.enums.Country;
import domain.enums.PieceType;
import domain.Position;

public class Cha extends Piece {

    public Cha(Country country) {
        super(country, PieceType.CHA);
    }

    @Override
    public boolean canMovePosition(Position start, Position end) {
        return start.getX() == end.getX() || start.getY() == end.getY();
    }

    @Override
    public boolean isAvailableRoute(List<Piece> pieces, PieceType endPieceType) {
        int count = 0;
        for (Piece piece : pieces) {
            if (piece.getPieceType()!=PieceType.NONE){
                count++;
            }
        }
        if (!endPieceType.equals(PieceType.NONE)){
            count--;
        }
        return !(count>=1);
    }
}
