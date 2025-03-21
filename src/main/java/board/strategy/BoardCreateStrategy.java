package board.strategy;

import coordinate.Coordinate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import piece.Cha;
import piece.Goong;
import piece.Jol;
import piece.Piece;
import piece.Po;
import piece.Sa;
import team.Team;

public abstract class BoardCreateStrategy {

    protected static Map<Team, List<Integer>> TEAM_X_COORDINATE = Map.of(
            Team.HAN, List.of(8, 7, 3, 2),
            Team.CHO, List.of(2, 3, 7, 8)
    );

    protected static Map<Team, Integer> TEAM_Y_COORDINATE = Map.of(
            Team.HAN, 1,
            Team.CHO, 10
    );

    public final Map<Coordinate, Piece> create(Team team) {
        Map<Coordinate, Piece> board = new HashMap<>();
        board.putAll(createDefaultBoard(team));
        board.putAll(createPiecesByTeam(team));
        return board;
    }

    private Map<Coordinate, Piece> createDefaultBoard(Team team) {
        if (team.equals(Team.HAN)) {
            return createHanDefaultBoard();
        }
        return createChoDefaultBoard();
    }

    private Map<Coordinate, Piece> createHanDefaultBoard() {
        return Map.ofEntries(
                Map.entry(new Coordinate(1, 1), new Cha(Team.HAN)),
                Map.entry(new Coordinate(4, 1), new Sa(Team.HAN)),
                Map.entry(new Coordinate(6, 1), new Sa(Team.HAN)),
                Map.entry(new Coordinate(9, 1), new Cha(Team.HAN)),
                Map.entry(new Coordinate(5, 2), new Goong(Team.HAN)),
                Map.entry(new Coordinate(2, 3), new Po(Team.HAN)),
                Map.entry(new Coordinate(8, 3), new Po(Team.HAN)),
                Map.entry(new Coordinate(1, 4), new Jol(Team.HAN)),
                Map.entry(new Coordinate(3, 4), new Jol(Team.HAN)),
                Map.entry(new Coordinate(5, 4), new Jol(Team.HAN)),
                Map.entry(new Coordinate(7, 4), new Jol(Team.HAN)),
                Map.entry(new Coordinate(9, 4), new Jol(Team.HAN))
        );
    }

    private Map<Coordinate, Piece> createChoDefaultBoard() {
        return Map.ofEntries(
                Map.entry(new Coordinate(1, 10), new Cha(Team.CHO)),
                Map.entry(new Coordinate(4, 10), new Sa(Team.CHO)),
                Map.entry(new Coordinate(6, 10), new Sa(Team.CHO)),
                Map.entry(new Coordinate(9, 10), new Cha(Team.CHO)),
                Map.entry(new Coordinate(5, 9), new Goong(Team.CHO)),
                Map.entry(new Coordinate(2, 8), new Po(Team.CHO)),
                Map.entry(new Coordinate(8, 8), new Po(Team.CHO)),
                Map.entry(new Coordinate(1, 7), new Jol(Team.CHO)),
                Map.entry(new Coordinate(3, 7), new Jol(Team.CHO)),
                Map.entry(new Coordinate(5, 7), new Jol(Team.CHO)),
                Map.entry(new Coordinate(7, 7), new Jol(Team.CHO)),
                Map.entry(new Coordinate(9, 7), new Jol(Team.CHO))
        );
    }

    protected abstract Map<Coordinate, Piece> createPiecesByTeam(Team team);
}
