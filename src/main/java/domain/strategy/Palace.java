package domain.strategy;

import domain.vo.Position;

public class Palace {

    private static final int[][] DIAGONAL_CENTERS = {{1, 4}, {8, 4}};
    private static final int[][][] DIAGONAL_CORNERS = {
            {{0, 3}, {0, 5}, {2, 3}, {2, 5}},
            {{7, 3}, {7, 5}, {9, 3}, {9, 5}}
    };

    public static boolean isInPalace(Position pos) {
        int r = pos.getRow(), c = pos.getCol();
        return (3 <= c && c <= 5) &&
                ((0 <= r && r <= 2) || (7 <= r && r <= 9));
    }

    public static boolean canDiagonalInPalace(Position from, Position to) {
        if (!isInPalace(from) || !isInPalace(to)) {
            return false;
        }
        int rowDiff = Math.abs(from.getRow() - to.getRow());
        int colDiff = Math.abs(from.getCol() - to.getCol());
        if (rowDiff < 1 || colDiff < 1) {
            return false;
        }

        if (isCenter(from)) {
            return true;
        }

        if (isCorner(from) && isCenter(to)) {
            return true;
        }

        if (isCorner(from) && isCorner(to) && isSamePalace(from, to)) {
            return true;
        }
        return false;
    }

    private static boolean isSamePalace(Position a, Position b) {
        return (a.getRow() <= 2 && b.getRow() <= 2) || (a.getRow() >= 7 && b.getRow() >= 7);
    }

    private static boolean isCenter(Position pos) {
        for (int[] c : DIAGONAL_CENTERS) {
            if (pos.getRow() == c[0] && pos.getCol() == c[1]) return true;
        }
        return false;
    }

    private static boolean isCorner(Position pos) {
        for (int[][] group : DIAGONAL_CORNERS) {
            for (int[] c : group) {
                if (pos.getRow() == c[0] && pos.getCol() == c[1]) return true;
            }
        }
        return false;
    }
}
