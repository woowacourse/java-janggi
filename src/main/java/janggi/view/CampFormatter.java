package janggi.view;

import janggi.piece.Camp;

public final class CampFormatter {

    private CampFormatter() {
    }

    public static String format(Camp camp) {
        if (camp == Camp.CHU) {
            return "초";
        }
        if (camp == Camp.HAN) {
            return "한";
        }
        throw new IllegalArgumentException("출력할 수 없는 진영입니다.");
    }
}
