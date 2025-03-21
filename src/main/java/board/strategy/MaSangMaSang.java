package board.strategy;

import coordinate.Coordinate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import piece.Ma;
import piece.Piece;
import piece.Sang;
import team.Team;

public class MaSangMaSang extends BoardCreateStrategy {

    @Override
    public Map<Coordinate, Piece> createPiecesByTeam(Team team) {
        List<Integer> x = TEAM_X_COORDINATE.get(team);
        int y = TEAM_Y_COORDINATE.get(team);

        Map<coordinate.Coordinate, Piece> pieces = new HashMap<>();
        pieces.put(new coordinate.Coordinate(x.get(0), y), new Ma(team));
        pieces.put(new coordinate.Coordinate(x.get(1), y), new Sang(team));
        pieces.put(new coordinate.Coordinate(x.get(2), y), new Ma(team));
        pieces.put(new coordinate.Coordinate(x.get(3), y), new Sang(team));
        return pieces;
    }
}
