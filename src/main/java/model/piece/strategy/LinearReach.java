package model.piece.strategy;

// 차, 포: 상하좌우 N칸 직선
public class LinearReach implements ReachStrategy {

    @Override
    public boolean isReachable(int rowDiff, int colDiff) {
        int absRow = Math.abs(rowDiff);
        int absCol = Math.abs(colDiff);
        return (absCol >= 1 && absRow == 0) || (absCol == 0 && absRow >= 1);
    }
}
