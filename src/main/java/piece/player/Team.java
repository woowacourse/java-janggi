package piece.player;

import java.util.Arrays;
import java.util.List;

public enum Team {

    RED,
    BLUE,
    EMPTY,
    ;

    private static final String NOT_SUPPORTED_TEAM = "지원하지 않는 팀입니다.";

    public static List<Team> playableTeams() {
        return List.of(Team.BLUE, Team.RED);
    }

    public static Team from(String teamName) {
        return Arrays.stream(Team.values())
                .filter((currentTeam) -> currentTeam.name().equals(teamName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(NOT_SUPPORTED_TEAM));
    }

    public Team opposite() {
        if (this == RED) {
            return BLUE;
        }
        if (this == BLUE) {
            return RED;
        }
        throw new IllegalStateException(NOT_SUPPORTED_TEAM);
    }
}


