package domain.game;

public enum Side {
    HAN(1, 1, 1),
    CHO(10, -1, -1),
    ;

    private static final int DEFAULT_MOVE_AMOUNT = 1;

    private final int baseRow;
    private final int forwardDirection;
    private final int backwardDirection;
    private final int leftDirection;
    private final int rightDirection;

    Side(
            int baseRow,
            int forwardDirection,
            int leftDirection
    ) {
        this.baseRow = baseRow;
        this.forwardDirection = forwardDirection;
        this.backwardDirection = forwardDirection * -1;
        this.leftDirection = leftDirection;
        this.rightDirection = leftDirection * -1;
    }

    public int getBaseRow() {
        return baseRow;
    }

    public int getForwardedRow(int currentRow) {
        return getForwardedRow(currentRow, DEFAULT_MOVE_AMOUNT);
    }

    public int getForwardedRow(int currentRow, int forwardAmount) {
        return currentRow + (forwardDirection * forwardAmount);
    }

    public int getBackwardRow(int currentRow) {
        return getBackwardRow(backwardDirection, DEFAULT_MOVE_AMOUNT);
    }

    public int getBackwardRow(int currentRow, int forwardAmount) {
        return currentRow + (backwardDirection * forwardAmount);
    }

    public int getLeftFile(int currentFile) {
        return getLeftFile(currentFile, DEFAULT_MOVE_AMOUNT);
    }

    public int getLeftFile(int currentFile, int moveAmount) {
        return currentFile + (leftDirection * moveAmount);
    }

    public int getRightFile(int currentFile) {
        return getRightFile(currentFile, DEFAULT_MOVE_AMOUNT);
    }

    public int getRightFile(int currentFile, int moveAmount) {
        return currentFile + (rightDirection * moveAmount);
    }
}
