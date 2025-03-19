package domain;

public record ChessPosition(
        int row,
        int column
) {
    public ChessPosition {
        if (row < 0 || row > 9 || column < 0 || column > 8) {
            throw new IllegalArgumentException("위치는 (0, 0) ~ (9, 8) 값만 가능합니다.");
        }
    }
}
