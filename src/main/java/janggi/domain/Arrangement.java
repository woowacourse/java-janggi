package janggi.domain;

import java.util.Arrays;

public enum Arrangement {
    마상마상("마상마상"),
    마상상마("마상상마"),
    상마마상("상마마상"),
    상마상마("상마상마");

    private final String openingSetup;

    private static final String INVALID_OPENING_TEXT = "존재하지 않는 배치입니다.";

    Arrangement(String openingSetup) {
        this.openingSetup = openingSetup;
    }

    public static Arrangement from(String opening) {
        return Arrays.stream(Arrangement.values())
                .filter(arrangement -> arrangement.openingSetup.equals(opening))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(INVALID_OPENING_TEXT));
    }
}
