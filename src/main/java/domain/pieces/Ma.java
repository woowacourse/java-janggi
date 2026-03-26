package domain.pieces;

import java.util.ArrayList;
import java.util.List;

import domain.Country;
import domain.PieceType;
import domain.Position;

public class Ma extends Piece {

    public Ma(Country country) {
        super(country, PieceType.MA);
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

        return (Math.abs(diffX) == 2 && Math.abs(diffY) == 1) || (Math.abs(diffX) == 1 && Math.abs(diffY) == 2);
    }
}
