package janggi;

import java.util.List;

public enum PieceType {
    GUNG(0, 1, List.of(4)),
    SA(3, 0, List.of(3,5)),
    CHA(13, 0, List.of(0,8)),
    PO(7, 2, List.of(1,7)),
    MA(5, 0, List.of(1,7)),
    SANG(3, 0, List.of(2,6)),
    JOL(2, 3, List.of(0,2,4,6,8)),
    ;

    private final int score;
    private final int height;
    private final List<Integer> xPositions;

    PieceType(final int score, final int height, final List<Integer> xPositions) {
        this.score = score;
        this.height = height;
        this.xPositions = xPositions;
    }

    public int getHeight() {
        return height;
    }

    public int getScore() {
        return score;
    }

    public List<Integer> getxPositions() {
        return xPositions;
    }
}
