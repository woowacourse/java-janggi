package domain.pieces;

import java.util.ArrayList;
import java.util.List;

import domain.Country;
import domain.Piece;
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
}
