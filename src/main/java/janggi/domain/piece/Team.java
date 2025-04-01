package janggi.domain.piece;

import java.util.Arrays;
import java.util.Objects;

public enum Team {
    RED("홍"),
    BLUE("청"),
    NONE("없음");

    private final String name;

    Team(final String name) {
        this.name = name;
    }

    public static Team findByName(final String value) {
        return Arrays.stream(Team.values())
                .filter(team -> Objects.equals(team.getName(), value))
                .findAny()
                .orElseThrow(()-> new IllegalStateException("팀을 찾을 수 없습니다."));
    }

    public String getName() {
        return name;
    }
}
