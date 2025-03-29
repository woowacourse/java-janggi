package janggi.domain.piece;

import java.util.List;

public enum Side {

    CHO("초나라"),
    HAN("한나라"),
    NONE("게임 종료");

    private final String name;

    Side(final String name) {
        this.name = name;
    }

    public static List<Side> getSides() {
        return List.of(Side.CHO, Side.HAN);
    }

    public static Side getSideByName(String name) {
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

    public List<Side> getSideByState() {
        if(this == Side.HAN) {
            return List.of(Side.HAN, Side.CHO);
        }
        if(this == Side.CHO) {
            return List.of(Side.CHO, Side.HAN);
        }
        return List.of(Side.NONE);
    }

    public String getName() {
        return name;
    }
}
