package object.board.creator;

import java.util.HashMap;
import java.util.Map;
import object.coordinate.Coordinate;
import object.piece.Piece;
import object.piece.PieceType;
import object.team.Country;

public abstract class TableSettingCreator {

    public Map<Coordinate, Piece> create(Country country) {
        return createDefaultBoard(country);
    }

    private Map<Coordinate, Piece> createDefaultBoard(Country country) {
        if (country.isSameCountry(Country.HAN)) {
            return createHanDefaultBoard();
        }
        return createChoDefaultBoard();
    }

    private Map<Coordinate, Piece> createHanDefaultBoard() {
        return new HashMap<>(Map.ofEntries(
                Map.entry(new Coordinate(1, 1), new Piece(Country.HAN, PieceType.CHA)),
                Map.entry(new Coordinate(4, 1), new Piece(Country.HAN, PieceType.SA)),
                Map.entry(new Coordinate(6, 1), new Piece(Country.HAN, PieceType.SA)),
                Map.entry(new Coordinate(9, 1), new Piece(Country.HAN, PieceType.CHA)),
                Map.entry(new Coordinate(5, 2), new Piece(Country.HAN, PieceType.GOONG)),
                Map.entry(new Coordinate(2, 3), new Piece(Country.HAN, PieceType.PO)),
                Map.entry(new Coordinate(8, 3), new Piece(Country.HAN, PieceType.PO)),
                Map.entry(new Coordinate(1, 4), new Piece(Country.HAN, PieceType.BYEONG)),
                Map.entry(new Coordinate(3, 4), new Piece(Country.HAN, PieceType.BYEONG)),
                Map.entry(new Coordinate(5, 4), new Piece(Country.HAN, PieceType.BYEONG)),
                Map.entry(new Coordinate(7, 4), new Piece(Country.HAN, PieceType.BYEONG)),
                Map.entry(new Coordinate(9, 4), new Piece(Country.HAN, PieceType.BYEONG))
        ));
    }

    private Map<Coordinate, Piece> createChoDefaultBoard() {
        return new HashMap<>(Map.ofEntries(
                Map.entry(new Coordinate(1, 10), new Piece(Country.CHO, PieceType.CHA)),
                Map.entry(new Coordinate(4, 10), new Piece(Country.CHO, PieceType.SA)),
                Map.entry(new Coordinate(6, 10), new Piece(Country.CHO, PieceType.SA)),
                Map.entry(new Coordinate(9, 10), new Piece(Country.CHO, PieceType.CHA)),
                Map.entry(new Coordinate(5, 9), new Piece(Country.CHO, PieceType.GOONG)),
                Map.entry(new Coordinate(2, 8), new Piece(Country.CHO, PieceType.PO)),
                Map.entry(new Coordinate(8, 8), new Piece(Country.CHO, PieceType.PO)),
                Map.entry(new Coordinate(1, 7), new Piece(Country.CHO, PieceType.JOL)),
                Map.entry(new Coordinate(3, 7), new Piece(Country.CHO, PieceType.JOL)),
                Map.entry(new Coordinate(5, 7), new Piece(Country.CHO, PieceType.JOL)),
                Map.entry(new Coordinate(7, 7), new Piece(Country.CHO, PieceType.JOL)),
                Map.entry(new Coordinate(9, 7), new Piece(Country.CHO, PieceType.JOL))
        ));
    }
}
