package domain.board;

public record Row(
        int value
) {

    private static final int TOPMOST = 1;
    private static final int BOTTOMMOST = 10;

    public boolean isOutOfBoard() {
        return value < TOPMOST || value > BOTTOMMOST;
    }
}
