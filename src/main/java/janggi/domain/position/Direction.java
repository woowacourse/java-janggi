package janggi.domain.position;

public enum Direction {

    UP(0, 1),
    DOWN(0, -1),
    LEFT(-1, 0),
    RIGHT(1, 0),

    UP_LEFT(-1, 1),
    UP_RIGHT(1, 1),
    DOWN_LEFT(-1, -1),
    DOWN_RIGHT(1, -1),
    ;

    private final int dFile;
    private final int dRank;

    Direction(final int dFile, final int dRank) {
        this.dFile = dFile;
        this.dRank = dRank;
    }

    public int getFileToAdd() {
        return dFile;
    }

    public int getRankToAdd() {
        return dRank;
    }
}
