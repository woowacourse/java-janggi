package domain.board.maSangStrategy;

import domain.Coordinate;
import domain.Team;
import domain.piece.pathPiece.Ma;
import domain.piece.Piece;
import domain.piece.pathPiece.Sang;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SangMaMaSang implements MaSangStrategy {

    @Override
    public Map<Coordinate, Piece> createMaAndSang(Team team) {
        List<Integer> x = TEAM_X_COORDINATE.get(team);
        int y = TEAM_Y_COORDINATE.get(team);
        final var pieces = new HashMap<Coordinate, Piece>();

        final var coordinate1 = new Coordinate(x.get(0), y);
        final var coordinate2 = new Coordinate(x.get(1), y);
        final var coordinate3 = new Coordinate(x.get(2), y);
        final var coordinate4 = new Coordinate(x.get(3), y);

        pieces.put(coordinate1, new Sang(team, coordinate1));
        pieces.put(coordinate2, new Ma(team, coordinate2));
        pieces.put(coordinate3, new Ma(team, coordinate3));
        pieces.put(coordinate4, new Sang(team, coordinate4));
        return pieces;
    }
}
