package janggi.formatter;

import janggi.domain.piece.Camp;

public final class CampFormatter {

    public static final String CHO_NAME = "초";
    public static final String HAN_NAME = "한";

    private CampFormatter() {
    }

    public static String format(Camp camp) {
        return switch (camp) {
            case CHO -> CHO_NAME;
            case HAN -> HAN_NAME;
        };
    }
}
