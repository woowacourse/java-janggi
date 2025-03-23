package domain.board.maSangStrategy;

import domain.Coordinate;
import domain.Team;
import domain.piece.Piece;
import java.util.List;
import java.util.Map;

public interface MaSangStrategy {

    Map<Team, List<Integer>> TEAM_X_COORDINATE = Map.of(
        Team.HAN, List.of(8, 7, 3, 2),
        Team.CHO, List.of(2, 3, 7, 8)
    );

    Map<Team, Integer> TEAM_Y_COORDINATE = Map.of(
        Team.HAN, 1,
        Team.CHO, 10
    );

    Map<Coordinate, Piece> createMaAndSang(Team team);
}
