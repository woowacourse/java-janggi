package object.board.creator;

import java.util.List;
import java.util.Map;
import object.coordinate.Coordinate;
import object.piece.Piece;
import object.piece.PieceType;
import object.team.Country;

public class MaSangSangMaCreator extends TableSettingCreator {

    @Override
    public Map<Coordinate, Piece> create(Country country) {
        List<Integer> x = country.getMaSangXCoordinates();
        int y = country.getMaSangYCoordinate();

        Map<Coordinate, Piece> pieces = super.create(country);
        pieces.put(new Coordinate(x.get(0), y), new Piece(country, PieceType.MA));
        pieces.put(new Coordinate(x.get(1), y), new Piece(country, PieceType.SANG));
        pieces.put(new Coordinate(x.get(2), y), new Piece(country, PieceType.SANG));
        pieces.put(new Coordinate(x.get(3), y), new Piece(country, PieceType.MA));

        return pieces;
    }
}
