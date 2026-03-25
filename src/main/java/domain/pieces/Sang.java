package domain.pieces;

import java.util.ArrayList;
import java.util.List;

import domain.Country;
import domain.Piece;
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

}