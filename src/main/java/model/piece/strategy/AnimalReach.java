package model.piece.strategy;

// 상(2), 마(1): 직선 1칸 + 대각선 N칸
public class AnimalReach implements ReachStrategy {

    private final int diagonalStep;

    public AnimalReach(int diagonalStep) {
        this.diagonalStep = diagonalStep;
    }

    @Override
    public boolean isReachable(int rowDiff, int colDiff) {
        int absRow = Math.abs(rowDiff);
        int absCol = Math.abs(colDiff);
        return (absCol == diagonalStep && absRow == diagonalStep + 1)
                || (absCol == diagonalStep + 1 && absRow == diagonalStep);
    }
}
