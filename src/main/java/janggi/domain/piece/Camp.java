package janggi.domain.piece;

import janggi.domain.Position;
import java.util.Arrays;
import java.util.List;

public enum Camp {

    HAN(-1, 9, 1.5) {
        @Override
        public Camp next() {
            return CHO;
        }
    },
    CHO(1, 0, 0) {
        @Override
        public Camp next() {
            return HAN;
        }
    };
    private static final String INVALID_BACKWARD_MOVEMENT = "[ERROR] 해당 기물은 후진할 수 없습니다.";
    private static final String INVALID_PALACE_MOVEMENT = "[ERROR] 해당 기물은 아군 궁성 영역 밖으로 이동할 수 없습니다.";

    private static final int PALACE_CENTER_ROW_DISTANCE = 1;
    private static final int FRIENDLY_PALACE_ROW_RANGE = 2;
    private static final int PALACE_START_COLUMN = 3;
    private static final int PALACE_CENTER_COLUMN = 4;
    private static final int PALACE_END_COLUMN = 5;

    private final int forwardDirection;
    private final int startRowPosition;
    private final double bonusScoreForSecondPlayer;

    Camp(int forwardDirection, int startRowPosition, double bonusScoreForSecondPlayer) {
        this.forwardDirection = forwardDirection;
        this.startRowPosition = startRowPosition;
        this.bonusScoreForSecondPlayer = bonusScoreForSecondPlayer;
    }

    public abstract Camp next();

    public void validateForwardDirection(int rowDirection) {
        if (forwardDirection != rowDirection && rowDirection != 0) {
            throw new IllegalArgumentException(INVALID_BACKWARD_MOVEMENT);
        }
    }

    public int getStartRowPosition() {
        return startRowPosition;
    }

    public double getBonusScoreForSecondPlayer() {
        return bonusScoreForSecondPlayer;
    }

    public void validateFriendlyPalace(Position position) {
        if (!(isFriendlyPalaceRow(position) && isPalaceColumn(position))) {
            throw new IllegalArgumentException(INVALID_PALACE_MOVEMENT);
        }
    }

    private boolean isFriendlyPalaceRow(Position position) {
        int absRowDifference = Math.abs(position.row() - startRowPosition);
        return absRowDifference <= FRIENDLY_PALACE_ROW_RANGE;
    }

    public static boolean isPalace(Position position) {
        return isPalaceRow(position) && isPalaceColumn(position);
    }

    private static boolean isPalaceRow(Position position) {
        return Arrays.stream(values())
                .anyMatch(camp -> {
                    int absRowDifference = Math.abs(position.row() - camp.startRowPosition);
                    return absRowDifference <= FRIENDLY_PALACE_ROW_RANGE;
                });
    }

    private static boolean isPalaceColumn(Position position) {
        return position.column() >= PALACE_START_COLUMN
                && position.column() <= PALACE_END_COLUMN;
    }

    public static boolean isPalaceCenter(Position position) {
        return Arrays.stream(values())
                .anyMatch(camp -> {
                    int absRowDifference = Math.abs(position.row() - camp.startRowPosition);
                    return position.column() == PALACE_CENTER_COLUMN
                            && absRowDifference == PALACE_CENTER_ROW_DISTANCE;
                });
    }

    public static List<Camp> getAllCamp() {
        return Arrays.stream(values())
                .toList();
    }
}
