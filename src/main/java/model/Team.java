package model;

import java.util.Arrays;

public enum Team {
    RED("한"),
    BLUE("초");
    private final String teamName;

    Team(String teamName) {
        this.teamName = teamName;
    }

    public static Team getTeamWithName(String teamName) {
        return Arrays.stream(values())
                .filter(team1 -> team1.getTeamName().equals(teamName))
                .findFirst()
                .orElseThrow(() -> new NullPointerException("해당하는 이름의 팀이 존재하지 않습니다."));
    }

    public String getTeamName() {
        return teamName;
    }
}
