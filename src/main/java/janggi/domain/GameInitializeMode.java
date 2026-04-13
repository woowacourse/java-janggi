package janggi.domain;

import java.util.Arrays;

public enum GameInitializeMode {
    NEW("new"),
    LOAD("load"),
    ;

    private final String name;

    GameInitializeMode(String name) {
        this.name = name;
    }

    public static GameInitializeMode from(String mode) {
        return Arrays.stream(values())
            .filter(gameMode -> gameMode.name.equals(mode))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("[ERROR] 적절하지 않은 게임 시작 방식입니다."));
    }
}
