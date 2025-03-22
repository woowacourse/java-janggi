package board.create.strategy;

import coordinate.Coordinate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import piece.Ma;
import piece.Piece;
import piece.Sang;
import team.Team;

public class SangMaSangMa extends BoardCreateStrategy {

    @Override
    public Map<Coordinate, Piece> create(Team team) {
        List<Integer> x = TEAM_X_COORDINATE.get(team);
        int y = TEAM_Y_COORDINATE.get(team);

        Map<Coordinate, Piece> pieces = new HashMap<>();
        pieces.putAll(createDefaultBoard(team));
        pieces.put(new Coordinate(x.get(0), y), new Sang(team));
        pieces.put(new Coordinate(x.get(1), y), new Ma(team));
        pieces.put(new Coordinate(x.get(2), y), new Sang(team));
        pieces.put(new Coordinate(x.get(3), y), new Ma(team));
        return pieces;
    }
}
