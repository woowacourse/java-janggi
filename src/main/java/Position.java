public class Position {
    private final int x;
    private final int y;

    public Position(int x, int y) {
        validate(x, y);
        this.x = x;
        this.y = y;
    }

    private void validate(int x, int y) {
        validateRange(x, 0, 8);
        validateRange(y, 0, 9);
    }

    private void validateRange(int number, int min, int max) {
        if (number < min || number > max) {
            throw new IllegalArgumentException("범위를 벗어난 좌표를 입력했습니다.");
        }
    }
}
