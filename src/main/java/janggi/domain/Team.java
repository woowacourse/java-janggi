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
            .orElseThrow(() -> new IllegalArgumentException("적절하지 않은 진영입니다."));
    }
}
