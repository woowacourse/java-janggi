package location;

public record Position(int x, int y) {
    private static final int HORIZONTAL_START = 1;
    private static final int HORIZONTAL_END = 9;
    private static final int VERTICAL_START = 1;
    private static final int VERTICAL_END = 10;

    public Position {
        validateRange(x, y);
    }

    public Position apply(Direction direction) {
        return new Position(x + direction.getX(), y + direction.getY());
    }

    private void validateRange(int x, int y) {
        if (x < HORIZONTAL_START || x > HORIZONTAL_END ||
                y < VERTICAL_START || y > VERTICAL_END) {
            throw new IllegalArgumentException("[ERROR] 위치할 수 없는 좌표입니다.");
        }
    }
}
