package team.janggi.domain;

import java.util.Arrays;

public enum Team {
    NONE(0.0),
    CHO(0.0),
    HAN(1.5);

    private final double handicap;

    Team(double handicap) {
        this.handicap = handicap;
    }

    public double getHandicap() {
        return handicap;
    }

    public static Team from(String team) {
        return Arrays.stream(Team.values())
                .filter(t -> t.name().equals(team))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 잘못된 팀 이름입니다: " + team));
    }

}
