package board.create.strategy;

import coordinate.Coordinate;
import java.util.HashMap;
import java.util.Map;
import piece.Byeong;
import piece.Cha;
import piece.Goong;
import piece.Jol;
import piece.Piece;
import piece.Po;
import piece.Sa;
import team.Team;

public abstract class TableSettingStrategy {

    public Map<Coordinate, Piece> create(Team team) {
        return createDefaultBoard(team);
    }

    private Map<Coordinate, Piece> createDefaultBoard(Team team) {
        if (team.isSameTeam(Team.HAN)) {
            return new HashMap<>(createHanDefaultBoard());
        }
        return new HashMap<>(createChoDefaultBoard());
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
                Map.entry(new Coordinate(1, 4), new Byeong(Team.HAN)),
                Map.entry(new Coordinate(3, 4), new Byeong(Team.HAN)),
                Map.entry(new Coordinate(5, 4), new Byeong(Team.HAN)),
                Map.entry(new Coordinate(7, 4), new Byeong(Team.HAN)),
                Map.entry(new Coordinate(9, 4), new Byeong(Team.HAN))
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
}
