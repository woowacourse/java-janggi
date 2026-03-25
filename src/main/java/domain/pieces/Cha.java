package domain.pieces;

import java.util.ArrayList;
import java.util.List;

import domain.Country;
import domain.Piece;
import domain.PieceType;
import domain.Position;

public class Cha extends Piece {

    public Cha(Country country) {
        super(country, PieceType.CHA);
    }

    @Override
    public List<Position> getAvailablePositions(Position nowPosition) {
        List<Position> positions = new ArrayList<>();
        try{

        } catch(IndexOutOfBoundsException e){
        }
        return positions;
    }
}
