package domain.board;

import domain.coordination.Coordination;
import domain.piece.EmptyPiece;
import domain.piece.Piece;
import domain.piece.Team;

import java.util.HashMap;
import java.util.Map;

public class BoardFactory {

    public static Board create(FormationType choFormation, FormationType hanFormation) {
        Map<Coordination, Piece> board = new HashMap<>();
        placeEmpty(board);

        board.putAll(PlacementStrategyRegistry.forHan(hanFormation).place());
        board.putAll(PlacementStrategyRegistry.forCho(choFormation).place());

        return new Board(board);
    }

    private static void placeEmpty(Map<Coordination, Piece> map) {
        for (int row = 1; row <= 10; row++) {
            placeColumns(map, row);
        }
    }

    private static void placeColumns(Map<Coordination, Piece> map, int row) {
        for (int col = 1; col <= 9; col++) {
            map.put(Coordination.of(col, row), new EmptyPiece(Team.NONE));
        }
    }
}
