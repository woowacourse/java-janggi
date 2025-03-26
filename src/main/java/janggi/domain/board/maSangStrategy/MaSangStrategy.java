package janggi.domain.board.maSangStrategy;

import janggi.domain.Team;
import janggi.domain.Piece;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface MaSangStrategy {

    Map<Team, List<Integer>> TEAM_X_COORDINATE = Map.of(
        Team.HAN, List.of(8, 7, 3, 2),
        Team.CHO, List.of(2, 3, 7, 8)
    );

    Map<Team, Integer> TEAM_Y_COORDINATE = Map.of(
        Team.HAN, 1,
        Team.CHO, 10
    );

    Set<Piece> createMaAndSang(Team team);
}
