package domain.board.createStrategy;

import domain.Coordinate;
import domain.Team;
import domain.piece.Ma;
import domain.piece.Piece;
import domain.piece.Sang;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MaSangSangMa extends BoardCreateStrategy {

    @Override
    public Map<Coordinate, Piece> createPiecesByTeam(Team team) {
        List<Integer> x = TEAM_X_COORDINATE.get(team);
        int y = TEAM_Y_COORDINATE.get(team);

        Map<Coordinate, Piece> pieces = new HashMap<>();
        pieces.put(new Coordinate(x.get(0), y), new Ma(team));
        pieces.put(new Coordinate(x.get(1), y), new Sang(team));
        pieces.put(new Coordinate(x.get(2), y), new Sang(team));
        pieces.put(new Coordinate(x.get(3), y), new Ma(team));
        return pieces;
    }
}
