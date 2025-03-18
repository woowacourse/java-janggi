package janggi;

import java.awt.Point;

public enum TeamColor {
    RED("빨강"),
    BLUE("파랑");

    private final String text;

//    private final Point basePoint;

    TeamColor(String text) {
        this.text = text;
    }
}
