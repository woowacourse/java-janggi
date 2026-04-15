package domain.board;

import view.FormationType;
import domain.board.formation.PlacementStrategyRegistry;
import domain.coordination.Coordination;
import domain.piece.EmptyPiece;
import domain.piece.Piece;
import domain.piece.Team;
import java.util.HashMap;
import java.util.Map;

public class BoardFactory {

    private static final int COLUMN_MIN = 1;
    private static final int COLUMN_MAX = 9;
    private static final int ROW_MIN = 1;
    private static final int ROW_MAX = 10;

    public static Board create(FormationType choFormation, FormationType hanFormation) {
        Map<Coordination, Piece> board = new HashMap<>();
        placeEmpty(board);

        board.putAll(PlacementStrategyRegistry.forHan(hanFormation).place());
        board.putAll(PlacementStrategyRegistry.forCho(choFormation).place());

        return new Board(board);
    }

    private static void placeEmpty(Map<Coordination, Piece> map) {
        for (int row = ROW_MIN; row <= ROW_MAX; row++) {
            placeColumns(map, row);
        }
    }

    private static void placeColumns(Map<Coordination, Piece> map, int row) {
        for (int col = COLUMN_MIN; col <= COLUMN_MAX; col++) {
            map.put(Coordination.of(col, row), new EmptyPiece(Team.NONE));
        }
    }
}
