package janggi.view.format;

import janggi.domain.piece.Camp;

public enum CampFormat {

    CHO("초", "\u001B[32m"),
    HAN("한", "\u001B[31m"),
    ;

    private final String displayName;
    private final String color;

    CampFormat(String displayName, String color) {
        this.displayName = displayName;
        this.color = color;
    }

    public static CampFormat from(Camp camp) {
        return switch (camp) {
            case CHO -> CHO;
            case HAN -> HAN;
        };
    }

    public String displayName() {
        return displayName;
    }

    public String color() {
        return color;
    }
}
