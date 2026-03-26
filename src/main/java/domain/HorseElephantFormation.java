package domain;

import java.util.Arrays;

public enum HorseElephantFormation {
    INNER_ELEPHANT("마상상마"),
    OUTER_ELEPHANT("상마마상"),
    RIGHT_ELEPHANT("마상마상"),
    LEFT_ELEPHANT("상마상마");

    private final String pattern;

    HorseElephantFormation(String pattern) {
        this.pattern = pattern;
    }

    public String getPattern() {
        return pattern;
    }

    public static HorseElephantFormation from(String pattern) {
        return Arrays.stream(values())
                .filter(type -> type.pattern.equals(pattern))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 포메이션입니다: " + pattern));
    }
}