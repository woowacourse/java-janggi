package model.piece.strategy;

import model.coordinate.PalacePositions;
import model.coordinate.Position;

// 장, 사: 궁성 내 상하좌우 + 대각선 1칸
public class GuardKingReach implements ReachStrategy {
    private final Position current;
    private final Position next;

    public GuardKingReach(Position current, Position next) {
        this.current = current;
        this.next = next;
    }

    @Override
    public boolean isReachable(int rowDiff, int colDiff) {
        if (!isMovableOnPalace()) {
            return false;
        }

        int absRow = Math.abs(rowDiff);
        int absCol = Math.abs(colDiff);
        return absRow <= 1 && absCol <= 1 && (absRow + absCol) >= 1;
    }

    private boolean isMovableOnPalace() {
        return isMovementInPalace() && notOnPalaceDiagonal();
    }

    private boolean isMovementInPalace() {
        return PalacePositions.inPalace(current) && PalacePositions.inPalace(next);
    }

    private boolean notOnPalaceDiagonal() {
        return PalacePositions.onPalaceDiagonal(current) || PalacePositions.onPalaceDiagonal(next);
    }
}
