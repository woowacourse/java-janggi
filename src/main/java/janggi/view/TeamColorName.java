package janggi.view;

import janggi.domain.piece.TeamColor;
import java.util.Arrays;

public enum TeamColorName {
    CHO(TeamColor.BLUE, "초나라"),
    HAN(TeamColor.RED, "한나라"),
    ;

    private final TeamColor teamColor;
    private final String teamName;

    TeamColorName(TeamColor teamColor, String teamName) {
        this.teamColor = teamColor;
        this.teamName = teamName;
    }

    public static String getNameFrom(TeamColor teamColor) {
        return Arrays.stream(TeamColorName.values())
                .filter(teamColorName -> teamColorName.teamColor == teamColor)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("해당 팀 색상은 존재하지 않습니다."))
                .teamName;
    }

    public String getTeamName() {
        return teamName;
    }
}
