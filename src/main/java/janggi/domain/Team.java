package janggi.domain;

import java.util.Arrays;

public enum Team {
    HAN("한"),
    CHO("초"),
    ;

    private final String name;

    Team(String name) {
        this.name = name;
    }

    public static Team from(String name) {
        return Arrays.stream(values())
            .filter(team -> team.name.equals(name))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("[ERROR] 적절하지 않은 진영입니다."));
    }

    public static Team initialTeam() {
        return CHO;
    }

    public Team next() {
        if (this == CHO) {
            return HAN;
        }
        return CHO;
    }

    public String getName() {
        return name;
    }
}
