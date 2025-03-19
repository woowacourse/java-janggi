package janggi.view;

import janggi.domain.Side;

public class Formatter {

    public static String formatSide(Side side) {
        String sideName = "초나라";
        if (side == Side.HAN) {
            sideName = "한나라";
        }

        return sideName;
    }

}
