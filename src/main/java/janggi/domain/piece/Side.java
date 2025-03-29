package janggi.domain.piece;

import java.util.List;

public enum Side {

    CHO("초나라"),
    HAN("한나라"),
    NONE("");

    private final String name;

    Side(final String name) {
        this.name = name;
    }

    public static Side findSideByName(String name) {
        return Side.getSides().stream()
                .filter(side -> name.equals(side.getName()))
                .findFirst()
                .orElse(NONE);
    }

    public Side getEnemySide() {
        if (this == Side.HAN) {
            return Side.CHO;
        }
        if (this == Side.CHO) {
            return Side.HAN;
        }
        return Side.NONE;
    }

    public static List<Side> getSides() {
        return List.of(Side.CHO, Side.HAN);
    }

    public String getName() {
        return name;
    }
}
