package janggi.view.format;

import janggi.domain.piece.Camp;
import janggi.exception.ExceptionMessage;
import java.util.Arrays;

public enum CampFormat {

    CHO(Camp.CHO, "초", "\u001B[32m"),
    HAN(Camp.HAN, "한", "\u001B[31m"),
    ;

    private final Camp camp;
    private final String name;
    private final String color;

    CampFormat(Camp camp, String name, String color) {
        this.camp = camp;
        this.name = name;
        this.color = color;
    }

    public static CampFormat from(Camp camp) {
        return Arrays.stream(CampFormat.values())
                .filter(element -> element.camp.equals(camp))
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
