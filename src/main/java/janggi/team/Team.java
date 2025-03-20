package janggi.team;

import java.util.Arrays;

public enum Team {
    CHO("초"),
    HAN("한");

    private final String value;

    Team(String value) {
        this.value = value;
    }

    public static Team from(String teamName) {
        return Arrays.stream(Team.values()).filter(team -> team.getValue().equals(teamName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("팀 이름이 존재하지 않습니다"));
    }

    public String getValue() {
        return value;
    }
}
