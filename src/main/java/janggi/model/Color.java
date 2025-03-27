package janggi.model;

import java.util.Arrays;

public enum Color {
    RED(1),
    BLUE(10),
    ;

    private final int initRow;

    Color(int initRow) {
        this.initRow = initRow;
    }

    public int getInitRow() {
        return initRow;
    }

    public Color reverse() {
        if (this == Color.RED) {
            return Color.BLUE;
        }
        return Color.RED;
    }

    public static Color from(String colorName) {
        return Arrays.stream(values())
                .filter(color -> color.name().equals(colorName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 팀 색입니다."));
    }
}
