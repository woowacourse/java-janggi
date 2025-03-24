package Janggi.board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import Janggi.piece.Piece;
import Janggi.piece.Country;

public class BoardFactory {

    public Board generateBoard() {
        final Map<Position, Piece> initMap = new HashMap<>();

        for (final Country country : Country.values()) {
            for (final PieceInitialPosition pieceType : PieceInitialPosition.values()) {
                final List<Position> initPositions = pieceType.getInitPositions(country);
                for (final Position position : initPositions) {
                    initMap.put(position, pieceType.createPiece(country));
                }
            }
        }

        return new Board(initMap);
    }
}
