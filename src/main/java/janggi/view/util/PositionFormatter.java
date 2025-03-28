package janggi.view.util;

import janggi.domain.board.Position;

public class PositionFormatter {

    public static Position formatStringToPosition(final String input) {
        char[] charArray = input.toCharArray();
        int x = charArray[0] - 'a';
        int y = charArray[1] - '0';
        return new Position(x, y);
    }

    public static String formatPositionToString(final Position position) {
        int x = position.getX();
        int y = position.getY();
        String xValue = Character.toString((char) (x + 'a'));
        String yValue = Character.toString((char) (y + '0'));
        return xValue + yValue;
    }

}
