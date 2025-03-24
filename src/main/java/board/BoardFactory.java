package board;

import piece.Piece;
import piece.TeamType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class BoardFactory {

    public Board generateBoard() {
        final Map<Position, Piece> initMap = new HashMap<>();

        for (final TeamType teamType : TeamType.values()) {
            Arrays.stream(PieceInitialPosition.values())
                    .map(pieceType -> pieceType.getPositions(teamType))
                    .forEachOrdered(initMap::putAll);
        }
        return new Board(initMap);
    }
}
