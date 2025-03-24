package domain.board.maSangStrategy;

import domain.Coordinate;
import domain.Team;
import domain.piece.Piece;
import domain.piece.pathPiece.Ma;
import domain.piece.pathPiece.Sang;
import java.util.List;
import java.util.Set;

public class MaSangSangMa implements MaSangStrategy {

    @Override
    public Set<Piece> createMaAndSang(Team team) {
        List<Integer> x = TEAM_X_COORDINATE.get(team);
        int y = TEAM_Y_COORDINATE.get(team);

        return Set.of(
            new Ma(team, new Coordinate(x.get(0), y)),
            new Sang(team, new Coordinate(x.get(1), y)),
            new Sang(team, new Coordinate(x.get(2), y)),
            new Ma(team, new Coordinate(x.get(3), y))
        );
    }
}
