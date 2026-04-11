package model.piece.strategy;

// 차, 포 궁성: 상하좌우 N칸 + 대각선
public class PalaceLinearReach implements ReachStrategy {

    @Override
    public boolean isReachable(int rowDiff, int colDiff) {
        int absRow = Math.abs(rowDiff);
        int absCol = Math.abs(colDiff);
        if ((absCol >= 1 && absRow == 0) || (absCol == 0 && absRow >= 1)) {
            return true;
        }
        return absRow == absCol && absRow >= 1;
    }
}
