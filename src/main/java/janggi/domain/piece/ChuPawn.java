package janggi.domain.piece;

import janggi.domain.Dynasty;
import java.util.Arrays;
import java.util.Objects;

public class ChuPawn extends Pawn {

    private static final Dynasty DYNASTY = Dynasty.CHU;

    private final int[][] directions = {
            {-1, 0}, {0, 1}, {0, -1}
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChuPawn chuPawn = (ChuPawn) o;
        return Objects.deepEquals(directions, chuPawn.directions);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(directions);
    }
}
