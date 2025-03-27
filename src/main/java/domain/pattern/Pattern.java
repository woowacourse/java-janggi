package domain.pattern;

public enum Pattern {
    RIGHT(1, 0),
    DOWN(0, 1),
    LEFT(-1, 0),
    UP(0, -1),

    DIAGONAL_UP_RIGHT(1, -1),
    DIAGONAL_DOWN_RIGHT(1, 1),
    DIAGONAL_DOWN_LEFT(-1, 1),
    DIAGONAL_UP_LEFT(-1, -1);

    private final int rank;
    private final int file;

    Pattern(int rank, int file) {
        this.rank = rank;
        this.file = file;
    }

    public int getRank() {
        return rank;
    }

    public int getFile() {
        return file;
    }
}
