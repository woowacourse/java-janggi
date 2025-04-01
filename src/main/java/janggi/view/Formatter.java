package janggi.view;

import janggi.domain.PieceName;
import janggi.domain.Side;
import janggi.domain.piece.Piece;

public final class Formatter {

    private Formatter() {
    }

    public static String formatMessageWithHeader(String header, String message) {
        return header + message;
    }

    public static String formatFullWidthNumber(int number) {
        String string = String.valueOf(number);

        StringBuilder builder = new StringBuilder();
        for (char value : string.toCharArray()) {
            builder.append((char) (value - '0' + '０'));
        }

        return builder.toString();
    }

    public static String formatSideName(Side side) {
        if (side == Side.CHO) {
            return "\u001B[32m초나라\u001B[0m";
        }
        return "\u001B[31m한나라\u001B[0m";
    }

    public static String formatPieceName(Piece piece) {
        if (piece.isSameSide(Side.CHO)) {
            return "\u001B[32m" + PieceName.getName(piece) + "\u001B[0m";
        }
        return "\u001B[31m" + PieceName.getName(piece) + "\u001B[0m";
    }
}
