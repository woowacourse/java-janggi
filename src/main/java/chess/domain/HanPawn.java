package chess.domain;

import java.util.Map;

public class HanPawn extends Pawn {

    private static final Dynasty DYNASTY = Dynasty.HAN;

    private final int[][] directions = {
            {1, 0}, {0, 1}, {0, -1}
    };


    @Override
    protected boolean isNotRemovable(Piece piece) {
        return piece.isSameDynasty(DYNASTY);
    }

    @Override
    protected boolean isMovable(int[] currentPoint, int[] destination) {

        int intervalX = destination[0] - currentPoint[0];
        int intervalY = destination[1] - currentPoint[1];
        for (int[] direction : directions) {
            if (direction[0] == intervalX && direction[1] == intervalY) {
                return true;
            }
        }

        return false;

    }

    @Override
    public boolean isSameDynasty(Dynasty dynasty) {
        return DYNASTY == dynasty;
    }
}
