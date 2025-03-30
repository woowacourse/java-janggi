package janggi.team;

import java.util.Arrays;

public enum TeamName {
    CHO("초"),
    HAN("한");

    private static final String INVALID_NAME = "존재하지 않는 진영 이름입니다.";

    private final String name;

    TeamName(String name) {
        this.name = name;
    }

    public static TeamName from(String teamName) {
        return Arrays.stream(TeamName.values())
                .filter(name -> name.matchTeamName(teamName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(INVALID_NAME));
    }

    public boolean matchTeamName(String name) {
        return this.name.equals(name);
    }

    public String getName() {
        return this.name;
    }
}
