package domain.board;

public record File(
        int value
) {

    private static final int LEFTMOST = 1;
    private static final int RIGHTMOST = 9;

    public static File minimumFile() {
        return new File(LEFTMOST);
    }

    public boolean isOutOfBoard() {
        return value < LEFTMOST || value > RIGHTMOST;
    }

    public boolean isInBoard() {
        return !isOutOfBoard();
    }

    public File nextFile() {
        return new File(value + 1);
    }
}
