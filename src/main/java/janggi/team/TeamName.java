package janggi.team;

import java.util.Arrays;

public enum TeamName {
    CHO("초"),
    HAN("한");

    private static final String INVALID_TEAM_NAME = "팀 이름이 존재하지 않습니다";
    private final String name;

    TeamName(String name) {
        this.name = name;
    }

    public static TeamName from(String teamName) {
        return Arrays.stream(TeamName.values())
                .filter(team -> team.name().equalsIgnoreCase(teamName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(INVALID_TEAM_NAME));
    }

    public String getName() {
        return this.name;
    }
}
