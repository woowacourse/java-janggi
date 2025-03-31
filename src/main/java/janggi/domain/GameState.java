package janggi.domain;

import java.util.List;

public enum GameState {

    HAN("한나라"),
    CHO("초나라"),
    ENDED("게임 종료");

    private final String name;

    GameState(final String name) {
        this.name = name;
    }

    public static GameState getStateByName(String name) {
        return GameState.getTurns().stream()
                .filter(turn -> name.equals(turn.getName()))
                .findFirst()
                .orElse(ENDED);
    }

    public static List<GameState> getTurns() {
        return List.of(HAN, CHO);
    }

    public List<GameState> getTurnsByState() {
        if (this == HAN) {
            return List.of(HAN, CHO);
        }
        if (this == CHO) {
            return List.of(CHO, HAN);
        }
        return List.of(ENDED);
    }

    public String getName() {
        return name;
    }
}
