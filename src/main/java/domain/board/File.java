package domain.board;

public record File(
        int value
) {

    private static final int LEFTMOST = 1;
    private static final int RIGHTMOST = 9;

    public boolean isOutOfBoard() {
        return value < LEFTMOST || value > RIGHTMOST;
    }
}
