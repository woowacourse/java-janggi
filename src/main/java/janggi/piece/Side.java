package janggi.piece;

import java.util.List;

public enum Side {

    CHO("초나라"),
    HAN("한나라"),
    NONE("");

    private final String name;

    Side(final String name) {
        this.name = name;
    }

    public static List<Side> getSides() {
        return List.of(Side.CHO, Side.HAN);
    }

    public String getName() {
        return name;
    }
}
