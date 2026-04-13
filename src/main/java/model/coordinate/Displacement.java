package model.coordinate;

public record Displacement(int rowDiff, int colDiff) {

    public Direction extractCardinal() {
        if (Math.abs(rowDiff) > Math.abs(colDiff)) {
            return Direction.of(rowDiff, 0);
        }
        return Direction.of(0, colDiff);
    }

    public Direction extractDiagonal() {
        return Direction.of(rowDiff, colDiff);
    }

    public boolean isNotStraight() {
        return colDiff != 0 && rowDiff != 0;
    }

    public boolean isNotStepCombination(int longStep, int shortStep) {
        return (absRowDiff() != longStep || absColDiff() != shortStep) &&
                (absRowDiff() != shortStep || absColDiff() != longStep);
    }

    public boolean isForwardBy(int forwardDirection) {
        return rowDiff == forwardDirection && colDiff == 0;
    }

    public boolean isSameForwardDirection(int forwardDirection) {
        return rowDiff == forwardDirection;
    }

    public boolean isSideOneStep() {
        return rowDiff == 0 && absColDiff() == 1;
    }

    public boolean isOneStepInRange() {
        int row = absRowDiff();
        int col = absColDiff();
        return (row <= 1 && col <= 1) && (row + col > 0);
    }

    private int absRowDiff() {
        return Math.abs(rowDiff);
    }

    private int absColDiff() {
        return Math.abs(colDiff);
    }
}
