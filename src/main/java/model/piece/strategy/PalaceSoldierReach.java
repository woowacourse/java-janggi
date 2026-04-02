package model.piece.strategy;

// 졸, 병 궁성: 전진 + 좌우 + 전진 대각선 1칸
public class PalaceSoldierReach implements ReachStrategy {

    private final int forwardDirection;

    public PalaceSoldierReach(int forwardDirection) {
        this.forwardDirection = forwardDirection;
    }

    @Override
    public boolean isReachable(int rowDiff, int colDiff) {
        int absCol = Math.abs(colDiff);
        if ((rowDiff == forwardDirection && absCol == 0) || (rowDiff == 0 && absCol == 1)) {
            return true;
        }
        return rowDiff == forwardDirection && absCol == 1;
    }
}
