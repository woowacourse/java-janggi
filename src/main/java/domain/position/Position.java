package domain.position;

import domain.movement.Direction;
import domain.unit.Team;
import java.util.Objects;

public class Position {

    private static final String INVALID_POSITION_EXCEPTION = "유효하지 않은 장기판 위치입니다.";
    public static final int X_MAX = 8;
    public static final int Y_MAX = 9;

    private final int x;
    private final int y;

    private Position(int x, int y) {
        validate(x, y);
        this.x = x;
        this.y = y;
    }

    public static Position of(int x, int y) {
        return new Position(x, y);
    }

    private void validate(int x, int y) {
        if (x < 0 || x > X_MAX) {
            throw new IllegalArgumentException(INVALID_POSITION_EXCEPTION);
        }
        if (y < 0 || y > Y_MAX) {
            throw new IllegalArgumentException(INVALID_POSITION_EXCEPTION);
        }
    }

    public int calculateMaxStep(Direction direction) {
        int maxMovement = 0;
        Position current = this;
        while (current.canBePosition(direction)) {
            current = current.calculatePositionWithDirection(direction);
            maxMovement++;
        }
        return maxMovement;
    }

    public Position calculatePositionWithDirection(Direction direction) {
        return Position.of(this.x + direction.getX(), this.y + direction.getY());
    }

    public double calculateDistance(Position other) {
        int xDifference = Math.abs(this.getX() - other.getX());
        int yDifference = Math.abs(this.getY() - other.getY());
        return Math.sqrt(Math.pow(xDifference, 2) + Math.pow(yDifference, 2));
    }

    public boolean canBePosition(Direction direction) {
        int nextX = this.x + direction.getX();
        int nextY = this.y + direction.getY();
        return nextX >= 0 && nextX <= X_MAX && nextY >= 0 && nextY <= Y_MAX;
    }

    public boolean isPalace() {
        return (3 <= x && x <= 5) && ((0 <= y && y <= 2) || (7 <= y && y <= 9));
    }

    public boolean isHorizontalOrVertical(Position opposite) {
        return (this.x == opposite.x || this.y == opposite.y);
    }

    public boolean isForwardOf(Position compare, Team team) {
        if (team == Team.HAN) {
            return this.y >= compare.y;
        }
        return this.y <= compare.y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
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
}
