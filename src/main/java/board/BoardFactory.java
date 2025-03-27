package board;

import piece.Country;
import piece.Piece;
import position.PieceInitialPosition1;
import position.Position;
import position.UpAndDown;

import java.util.HashMap;
import java.util.Map;

public class BoardFactory {

    public Board generateBoard(Country designatedCountry, UpAndDown designatedUpAndDown) {
        final Map<Position, Piece> initMap = new HashMap<>();

        Country.assignDirection(designatedCountry, designatedUpAndDown);
        for (final Country country : Country.values()) {
            for (PieceInitialPosition1 pieceType : PieceInitialPosition1.values()) {
                Map<Position, Piece> absolutePositions1 = pieceType.getAbsolutePositions1(country);
                initMap.putAll(absolutePositions1);
            }
        }
        return new Board(initMap);
    }
}
