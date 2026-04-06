package db.parser;

import domain.game.Side;

public class SideParser {

    public static String sideToString(Side side) {
        return side.name();
    }

    public static Side stringToSide(String sideString) {
        return Side.valueOf(sideString);
    }
}
