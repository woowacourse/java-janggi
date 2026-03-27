package janggi.domain;

import java.util.Arrays;

public enum Arrangement {
    마상마상("마상마상"),
    마상상마("마상상마"),
    상마마상("상마마상"),
    상마상마("상마상마");

    private final String openningSetup;

    private static final String INVALID_OPENING_TEXT = "존재하지 않는 배치입니다.";

    Arrangement(String openningSetup) {
        this.openningSetup = openningSetup;
    }

    public static Arrangement from(String openning) {
        return Arrays.stream(Arrangement.values())
                .filter(arrangement -> arrangement.openningSetup.equals(openning))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(INVALID_OPENING_TEXT));
    }
}
