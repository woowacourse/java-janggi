package domain.game;

public enum Side {
    HAN(1, 1),
    CHO(10, -1),
    ;

    private final int baseRow;
    private final int forwardDirection;

    Side(int baseRow, int forwardDirection) {
        this.baseRow = baseRow;
        this.forwardDirection = forwardDirection;
    }

    public int getBaseRow() {
        return baseRow;
    }

    public int getForwardedRow(int currentRow, int forwardAmount) {
        return currentRow + (forwardDirection * forwardAmount);
    }
}
