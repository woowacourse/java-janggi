package janggi.domain;

import java.util.List;

public enum Turn {

    HAN("한나라"),
    CHO("초나라"),
    ENDED("게임 종료"),
    NONE("");

    private final String name;

    Turn(final String name) {
        this.name = name;
    }

    public static Turn getStateByName(String name) {
        return Turn.getSides().stream()
                .filter(turn -> name.equals(turn.getName()))
                .findFirst()
                .orElse(ENDED);
    }

    public static List<Turn> getSides() {
        return List.of(HAN, CHO);
    }

    public List<Turn> getTurnsByState() {
        if (this == HAN) {
            return List.of(HAN, CHO);
        }
        if (this == CHO) {
            return List.of(CHO, HAN);
        }
        return List.of(ENDED);
    }

    public Turn getEnemySide() {
        if (this == Turn.HAN) {
            return Turn.CHO;
        }
        if (this == Turn.CHO) {
            return Turn.HAN;
        }
        return Turn.NONE;
    }

    public String getName() {
        return name;
    }
}
