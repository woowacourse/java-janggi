package board.creator;

import coordinate.Coordinate;
import java.util.List;
import java.util.Map;
import piece.Piece;
import piece.PieceType;
import team.Country;

public class MaSangSangMaCreator extends TableSettingCreator {

    @Override
    public Map<Coordinate, Piece> create(Country country) {
        List<Integer> x = country.getMaSangXCoordinates();
        int y = country.getMaSangYCoordinate();

        Map<Coordinate, Piece> pieces = super.create(country);
        pieces.put(new Coordinate(x.get(0), y), new Piece(country, PieceType.마));
        pieces.put(new Coordinate(x.get(1), y), new Piece(country, PieceType.상));
        pieces.put(new Coordinate(x.get(2), y), new Piece(country, PieceType.상));
        pieces.put(new Coordinate(x.get(3), y), new Piece(country, PieceType.마));

        return pieces;
    }
}
