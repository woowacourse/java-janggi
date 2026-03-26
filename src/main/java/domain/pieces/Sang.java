package domain.pieces;

import java.util.ArrayList;
import java.util.List;

import domain.Country;
import domain.PieceType;
import domain.Position;

public class Sang extends Piece {

    public Sang(Country country) {
        super(country, PieceType.SANG);
    }

    @Override
    public List<Position> getAvailablePositions(Position nowPosition) {
        List<Position> positions = new ArrayList<>();
        try{
//            positions.add(nowPosition.getNextUpDownPosition());
//            positions.add(nowPosition.getNextLeftRightPosition());
        } catch(IndexOutOfBoundsException e){
        }
        return positions;
    }

    @Override
    protected boolean canMovePosition(Position start, Position end) {
        int diffX = end.getX() - start.getX();
        int diffY = end.getY() - start.getY();

        return (Math.abs(diffX) == 3 && Math.abs(diffY) == 2) || (Math.abs(diffX) == 2 && Math.abs(diffY) == 3);
    }

}