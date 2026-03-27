import java.util.Objects;

public class Position {
    public static final int MIN = 0;
    public static final int MAX_X = 8;
    public static final int MAX_Y = 9;
    private final int x;
    private final int y;

    public Position(int x, int y) {
        validate(x, y);
        this.x = x;
        this.y = y;
    }

    private void validate(int x, int y) {
        validateRange(x, MAX_X);
        validateRange(y, MAX_Y);
    }

    private void validateRange(int number, int max) {
        if (number < MIN || number > max) {
            throw new IllegalArgumentException("범위를 벗어난 좌표를 입력했습니다.");
        }
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Position position = (Position) object;
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public Position up() {
        return new Position(x, y + 1);
    }

    public Position down() {
        return new Position(x, y - 1);
    }

    public Position left() {
        return new Position(x - 1, y);
    }

    public Position right() {
        return new Position(x + 1, y);
    }

    public Position leftUp() {
        return new Position(x - 1, y + 1);
    }

    public Position leftDown() {
        return new Position(x - 1, y - 1);
    }

    public Position rightUp() {
        return new Position(x + 1, y + 1);
    }

    public Position rightDown() {
        return new Position(x + 1, y - 1);
    }

    public boolean upPossible() {
        return y + 1 <= MAX_Y;
    }

    public boolean downPossible() {
        return y - 1 >= MIN;
    }

    public boolean leftPossible() {
        return x - 1 >= MIN;
    }

    public boolean rightPossible() {
        return x + 1 <= MAX_X;
    }

    public boolean leftUpPossible() {
        return x - 1 >= MIN && y + 1 <= MAX_Y;
    }

    public boolean leftDownPossible() {
        return x - 1 >= MIN && y - 1 >= MIN;
    }

    public boolean rightUpPossible() {
        return x + 1 <= MAX_X && y + 1 <= MAX_Y;
    }

    public boolean rightDownPossible() {
        return x + 1 <= MAX_X && y - 1 >= MIN;
    }
}
