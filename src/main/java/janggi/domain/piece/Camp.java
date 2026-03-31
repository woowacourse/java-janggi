package janggi.domain.piece;

import janggi.domain.Position;
import java.util.List;

public enum Camp {

    HAN(-1, 9) {
        @Override
        public List<Integer> convertElephantColumns(List<Integer> columns) {
            return columns;
        }

        @Override
        public Camp next() {
            return CHO;
        }
    },
    CHO(1, 0) {
        @Override
        public List<Integer> convertElephantColumns(List<Integer> columns) {
            return columns.reversed();
        }

        @Override
        public Camp next() {
            return HAN;
        }
    };
    private static final String INVALID_BACKWARD_MOVEMENT = "[ERROR] 해당 기물은 후진할 수 없습니다.";
    private static final String INVALID_PALACE_MOVEMENT = "[ERROR] 해당 기물은 아군 궁성 영역 밖으로 이동할 수 없습니다.";

    private final int forwardDirection;
    private final int startRowPosition;

    Camp(int forwardDirection, int startRowPosition) {
        this.forwardDirection = forwardDirection;
        this.startRowPosition = startRowPosition;
    }

    public abstract List<Integer> convertElephantColumns(List<Integer> columns);

    public abstract Camp next();

    public void validateForwardDirection(int rowDirection) {
        if (forwardDirection != rowDirection && rowDirection != 0) {
            throw new IllegalArgumentException(INVALID_BACKWARD_MOVEMENT);
        }
    }

    public int getStartRowPosition() {
        return startRowPosition;
    }

    public void validatePalace(Position destination) {
        int absRowDifference = Math.abs(destination.row() - startRowPosition);

        if (isPalaceRowOutOfRange(absRowDifference) || isPalaceColumnOutOfRange(destination)) {
            throw new IllegalArgumentException(INVALID_PALACE_MOVEMENT);
        }
    }

    private boolean isPalaceRowOutOfRange(int absRowDifference) {
        return absRowDifference > 2;
    }

    private boolean isPalaceColumnOutOfRange(Position destination) {
        return destination.column() < 3 || destination.column() > 5;
    }
}
