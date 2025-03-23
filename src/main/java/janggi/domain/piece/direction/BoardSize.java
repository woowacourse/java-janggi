package janggi.domain.piece.direction;

public enum BoardSize {

    MIN_X(0),
    MAX_X(8),
    MIN_Y(0),
    MAX_Y(9);

    private final int size;

    BoardSize(final int size) {
        this.size = size;
    }

    public static boolean isInBoard(final int x, final int y) {
        return x >= MIN_X.size && x <= MAX_X.size && y >= MIN_Y.size && y <= MAX_Y.size;
    }

    public int getSize() {
        return size;
    }
}
