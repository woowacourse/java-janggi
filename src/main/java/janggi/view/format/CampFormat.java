package janggi.view.format;

import janggi.domain.piece.camp.CampType;
import janggi.exception.ExceptionMessage;
import java.util.Arrays;

public enum CampFormat {

    CHO(CampType.CHO, "초", "\u001B[32m"),
    HAN(CampType.HAN, "한", "\u001B[31m"),
    ;

    private final CampType campType;
    private final String name;
    private final String color;

    CampFormat(CampType campType, String name, String color) {
        this.campType = campType;
        this.name = name;
        this.color = color;
    }

    public static CampFormat from(CampType campType) {
        return Arrays.stream(CampFormat.values())
                .filter(element -> element.campType.equals(campType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ExceptionMessage.CAMP_FORMAT_NOT_FOUND.getMessage()));
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }
}
