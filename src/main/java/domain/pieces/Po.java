package domain.pieces;

import java.util.ArrayList;
import java.util.List;

import domain.Country;
import domain.Piece;
import domain.PieceType;
import domain.Position;

public class Po extends Piece {

    public Po(Country country) {
        super(country, PieceType.PO);
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
