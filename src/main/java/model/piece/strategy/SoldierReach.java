package model.piece.strategy;

// 졸, 병: 전진 + 좌우 1칸
public class SoldierReach implements ReachStrategy {

    private final int forwardDirection;

    public SoldierReach(int forwardDirection) {
        this.forwardDirection = forwardDirection;
    }

    @Override
    public boolean isReachable(int rowDiff, int colDiff) {
        int absCol = Math.abs(colDiff);
        return (rowDiff == forwardDirection && absCol == 0) || (rowDiff == 0 && absCol == 1);
    }
}
