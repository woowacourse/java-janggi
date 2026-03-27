package janggi.formatter;

import janggi.domain.piece.Camp;

public final class CampFormatter {

    private CampFormatter() {
    }

    public static String format(Camp camp) {
        return switch (camp) {
            case CHO -> "초";
            case HAN -> "한";
        };
    }
}
