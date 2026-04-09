package fixture;

import domain.board.formation.FormationType;
import domain.board.formation.PlacementStrategyRegistry;
import domain.coordination.Coordination;
import domain.piece.EmptyPiece;
import domain.piece.Piece;
import domain.piece.Team;
import java.util.HashMap;
import java.util.Map;

public class BoardFixtureFactory {

    public static BoardFixture create(String choOption, String hanOption) {
        Map<Coordination, Piece> board = new HashMap<>();

        placeEmpty(board);
        board.putAll(PlacementStrategyRegistry.forHan(FormationType.from(hanOption)).place());
        board.putAll(PlacementStrategyRegistry.forCho(FormationType.from(choOption)).place());

        return new BoardFixture(board);
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
