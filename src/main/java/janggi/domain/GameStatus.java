package janggi.domain;

import java.util.Arrays;

public enum GameStatus {
    CONTINUE,
    RED_WIN,
    GREEN_WIN,
    DRAW,
    ;

    public static GameStatus convert(String gameStatusName) {
        return Arrays.stream(GameStatus.values())
                .filter(gameStatus -> gameStatus.name().equals(gameStatusName))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 게임 상태명입니다: " + gameStatusName));
    }
}
