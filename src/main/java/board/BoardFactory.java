package board;

import piece.Country;
import piece.Piece;
import position.PieceInitialPosition;
import position.Position;
import position.LineDirection;

import java.util.HashMap;
import java.util.Map;

public class BoardFactory {

    public BoardFactory(Country country, LineDirection direction) {
        Country.assignDirection(country, direction);
    }

    public Board generateBoard() {
        final Map<Position, Piece> initMap = new HashMap<>();
        for (Country country : Country.values()) {
            for (PieceInitialPosition pieceType : PieceInitialPosition.values()) {
                initMap.putAll(pieceType.getAbsolutePositions(country));
            }
        }
        return new Board(initMap);
    }
}
