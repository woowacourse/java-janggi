package janggi.domain.board;

import janggi.domain.piece.Camp;
import java.util.Arrays;

public final class Palace {

    private static final String INVALID_PALACE_MOVEMENT = "[ERROR] 해당 기물은 아군 궁성 영역 밖으로 이동할 수 없습니다.";

    private static final int PALACE_CENTER_ROW_DISTANCE = 1;
    private static final int PALACE_CENTER_COLUMN = 4;

    private static final int PALACE_START_COLUMN = 3;
    private static final int PALACE_END_COLUMN = 5;

    private static final int PALACE_ROW_RANGE_FROM_START_ROW = 2;

    private Palace() {
    }

    public static void validateFriendlyPalace(Camp camp, Position position) {
        if (!(isFriendlyPalaceRow(camp, position) && isPalaceColumn(position))) {
            throw new IllegalArgumentException(INVALID_PALACE_MOVEMENT);
        }
    }

    private static boolean isFriendlyPalaceRow(Camp camp, Position position) {
        int absRowDifference = Math.abs(position.row() - camp.getStartRowPosition());
        return absRowDifference <= PALACE_ROW_RANGE_FROM_START_ROW;
    }

    public static boolean isPalace(Position position) {
        return isPalaceRow(position) && isPalaceColumn(position);
    }

    private static boolean isPalaceRow(Position position) {
        return Arrays.stream(Camp.values())
                .anyMatch(camp -> {
                    int absRowDifference = Math.abs(position.row() - camp.getStartRowPosition());
                    return absRowDifference <= PALACE_ROW_RANGE_FROM_START_ROW;
                });
    }

    private static boolean isPalaceColumn(Position position) {
        return position.column() >= PALACE_START_COLUMN
                && position.column() <= PALACE_END_COLUMN;
    }

    public static boolean isPalaceCenter(Position position) {
        return Arrays.stream(Camp.values())
                .anyMatch(camp -> {
                    int absRowDifference = Math.abs(position.row() - camp.getStartRowPosition());
                    return position.column() == PALACE_CENTER_COLUMN
                            && absRowDifference == PALACE_CENTER_ROW_DISTANCE;
                });
    }
}
