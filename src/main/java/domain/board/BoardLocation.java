package domain.board;

import java.util.Objects;

public record BoardLocation(
        int x,
        int y
) {

    private static final int START_X = 1;
    private static final int START_Y = 1;
    private static final int END_X = 9;
    private static final int END_Y = 10;

    public BoardLocation {
        validateXRange(x);
        validateYRange(y);
    }

    public boolean isDown(BoardLocation current) {
        return current.y - y < 0;
    }

    public boolean isUp(BoardLocation current) {
        return current.y - y > 0;
    }

    public BoardLocation move(int x, int y) {
        return new BoardLocation(this.x + x, this.y + y);
    }

    public BoardLocation moveVertical(int dy) {
        return new BoardLocation(x, y + dy);
    }

    public BoardLocation moveHorizon(int dx) {
        return new BoardLocation(x + dx, y);
    }

    public BoardLocation moveDirection(Direction direction) {
        return this.move(direction.x(), direction.y());
    }

    private void validateYRange(int y) {
        if (y < START_Y || y > END_Y) {
            throw new IllegalArgumentException("[ERROR] 보드판 y값이 범위 밖에 위치합니다. y :" + y);
        }
    }

    private void validateXRange(int x) {
        if (x < START_X || x > END_X) {
            throw new IllegalArgumentException("[ERROR] 보드판 x값이 범위 밖에 위치합니다. x :" + x);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BoardLocation boardLocation)) {
            return false;
        }
        return x == boardLocation.x && y == boardLocation.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
