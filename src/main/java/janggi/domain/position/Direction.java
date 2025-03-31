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

    private final int fileToAdd;
    private final int rankToAdd;

    Direction(final int fileToAdd, final int rankToAdd) {
        this.fileToAdd = fileToAdd;
        this.rankToAdd = rankToAdd;
    }

    public int getFileToAdd() {
        return fileToAdd;
    }

    public int getRankToAdd() {
        return rankToAdd;
    }
}
