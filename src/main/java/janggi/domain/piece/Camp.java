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
        return absRowDifference <= 2;
    }

    public static boolean isPalace(Position position) {
        return isPalaceRow(position) && isPalaceColumn(position);
    }

    private static boolean isPalaceRow(Position position) {
        return Arrays.stream(values())
                .anyMatch(camp -> {
                    int absRowDifference = Math.abs(position.row() - camp.startRowPosition);
                    return absRowDifference <= 2;
                });
    }

    private static boolean isPalaceColumn(Position position) {
        return position.column() >= 3 && position.column() <= 5;
    }

    public static boolean isPalaceCenter(Position position) {
        return Arrays.stream(values())
                .anyMatch(camp -> {
                    int absRowDifference = Math.abs(position.row() - camp.startRowPosition);
                    return position.column() == 4 && absRowDifference == 1;
                });
    }

    public static List<Camp> getAllCamp() {
        return Arrays.stream(values())
                .toList();
    }
}
