package domain.pieces;

import java.util.ArrayList;
import java.util.List;

import domain.Country;
import domain.PieceType;
import domain.Position;

public class Jol extends Piece {

    public Jol(Country country) {
        super(country, PieceType.JOL);
    }

    @Override
    public List<Position> getAvailablePositions(Position nowPosition) {
        List<Position> positions = new ArrayList<>();
        try{
            positions.add(nowPosition.getNextUpDownPosition());
            positions.add(nowPosition.getNextLeftRightPosition());
        } catch(IndexOutOfBoundsException e){
        }
        return positions;
    }

    @Override
    public boolean canMovePosition(Position start, Position end) {
        int diffX = end.getX() - start.getX();
        int diffY = end.getY() - start.getY();
        if (Math.abs(diffX) + Math.abs(diffY) != 1) {
            return false;
        }
        if (getCountry().equals(Country.CHO)) {
            return diffX >= 0;
        }
        return diffX <= 0;
    }
}
