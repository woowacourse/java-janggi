package domain.pieces;

import java.util.ArrayList;
import java.util.List;

import domain.Country;
import domain.PieceType;
import domain.Position;

public class Sa extends Piece {

    public Sa (Country country) {
        super(country, PieceType.SA);
    }

    @Override
    public List<Position> getAvailablePositions(Position nowPosition) {
        List<Position> positions = new ArrayList<>();
        try{

        } catch(IndexOutOfBoundsException e){
        }
        return positions;
    }

    @Override
    protected boolean canMovePosition(Position start, Position end) {
        int diffX = end.getX() - start.getX();
        int diffY = end.getY() - start.getY();

        return Math.abs(diffX) + Math.abs(diffY) == 1;
    }
}
