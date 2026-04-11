package domain;

import exception.TeamErrorMessage;
import exception.custom.InvalidTeamException;
import java.util.Arrays;

public enum Team {
    CHO("초"),
    HAN("한");

    private final String koreanName;

    Team(String koreanName) {
        this.koreanName = koreanName;
    }

    public String getKoreanName() {
        return koreanName;
    }

    public Team changeTurn() {
        if (this == CHO) {
            return HAN;
        }

        return CHO;
    }

    public static Team getTeam(String koreanName) {
        return Arrays.stream(Team.values())
                .filter(team -> team.koreanName.equals(koreanName))
                .findFirst()
                .orElseThrow(() -> new InvalidTeamException(TeamErrorMessage.INVALID_TEAM_NAME + koreanName));
    }
}
