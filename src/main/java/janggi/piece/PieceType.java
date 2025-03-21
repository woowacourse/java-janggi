package janggi.piece;

import java.util.List;

public enum PieceType {
    GUNG("궁", 0, 1, List.of(4)),
    SA("사", 3, 0, List.of(3, 5)),
    CHA("차", 13, 0, List.of(0, 8)),
    PO("포", 7, 2, List.of(1, 7)),
    MA("마", 5, 0, List.of(1, 7)),
    SANG("상", 3, 0, List.of(2, 6)),
    JOL("졸", 2, 3, List.of(0, 2, 4, 6, 8)),
    ;
    private final String name;
    private final int score;
    private final int height;
    private final List<Integer> defaultXPositions;

    PieceType(final String name, final int score, final int height, List<Integer> defaultXPositions) {
        this.name = name;
        this.score = score;
        this.height = height;
        this.defaultXPositions = defaultXPositions;
    }

    public String getName() {
        return name;
    }

    public int getHeight() {
        return height;
    }

    public List<Integer> getDefaultXPositions() {
        return defaultXPositions;
    }
}
