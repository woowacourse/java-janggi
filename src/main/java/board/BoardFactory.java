package board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import piece.Piece;
import piece.TeamType;

public class BoardFactory {

    public Board generateBoard() {
        final Map<Position, Piece> initMap = new HashMap<>();

        for (final TeamType teamType : TeamType.values()) {
            for (final PieceInitialPosition pieceType : PieceInitialPosition.values()) {
                final List<Position> initPositions = pieceType.getInitPositions(teamType);
                for (final Position position : initPositions) {
                    initMap.put(position, pieceType.createPiece(teamType));
                }
            }
        }

        return new Board(initMap);
    }
}
