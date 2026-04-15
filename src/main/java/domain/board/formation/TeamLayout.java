package domain.board.formation;

import domain.game.Team;
import java.util.Map;

public record TeamLayout(int backRow, int generalRow, int cannonRow, int soldierRow) {

    private static final Map<Team, TeamLayout> LAYOUTS = Map.of(
            Team.CHO, new TeamLayout(1, 2, 3, 4),
            Team.HAN, new TeamLayout(10, 9, 8, 7)
    );

    public static TeamLayout of(Team team) {
        TeamLayout layout = LAYOUTS.get(team);
        if (layout == null) {
            throw new IllegalArgumentException("해당 팀의 배치 정보가 존재하지 않습니다.");
        }
        return layout;
    }
}
