package janggi.domain.piece;

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

    private final int forwardDirection;
    private final int startRowPosition;
    private final double bonusScore;

    Camp(int forwardDirection, int startRowPosition, double bonusScore) {
        this.forwardDirection = forwardDirection;
        this.startRowPosition = startRowPosition;
        this.bonusScore = bonusScore;
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

    public double applyBonusScore(double score) {
        return score + bonusScore;
    }

    public static List<Camp> getAllCamp() {
        return Arrays.stream(values())
                .toList();
    }
}
