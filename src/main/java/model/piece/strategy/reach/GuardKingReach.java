package model.piece.strategy.reach;

// 장, 사: 궁성 내 상하좌우 + 대각선 1칸
public class GuardKingReach implements ReachStrategy {

    @Override
    public boolean isReachable(int rowDiff, int colDiff) {
        int absRow = Math.abs(rowDiff);
        int absCol = Math.abs(colDiff);
        return absRow <= 1 && absCol <= 1 && (absRow + absCol) >= 1;
    }
}
