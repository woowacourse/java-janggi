package object.coordinate;

import static object.board.Board.BOARD_MAX_HEIGHT;
import static object.board.Board.BOARD_MAX_WIDTH;
import static object.board.Board.BOARD_MIN_HEIGHT;
import static object.board.Board.BOARD_MIN_WIDTH;

import java.util.List;
import java.util.Objects;

public class Coordinate {

    private static final int CASTLE_MIN_X_COORDINATE = 4;
    private static final int CASTLE_MAX_X_COORDINATE = 6;
    private static final int CASTLE_HAN_MIN_Y_COORDINATE = 1;
    private static final int CASTLE_HAN_MAX_Y_COORDINATE = 3;
    private static final int CASTLE_CHO_MIN_Y_COORDINATE = 8;
    private static final int CASTLE_CHO_MAX_Y_COORDINATE = 10;

    private final int x;
    private final int y;

    public Coordinate(int x, int y) {
        validateXCoordinate(x);
        validateYCoordinate(y);
        this.x = x;
        this.y = y;
    }

    public Coordinate moveBy(MoveVector moveVector) {
        int deltaX = moveVector.deltaX();
        int deltaY = moveVector.deltaY();

        int newX = this.x + deltaX;
        int newY = this.y + deltaY;

        if (isInvalidX(newX) || isInvalidY(newY)) {
            return null;
        }
        return new Coordinate(newX, newY);
    }

    public Coordinate moveBy(List<MoveVector> moveVectors) {
        int deltaX = moveVectors.stream()
                .mapToInt(MoveVector::deltaX)
                .sum();
        int deltaY = moveVectors.stream()
                .mapToInt(MoveVector::deltaY)
                .sum();

        int newX = this.x + deltaX;
        int newY = this.y + deltaY;

        if (isInvalidX(newX) || isInvalidY(newY)) {
            return null;
        }
        return new Coordinate(newX, newY);
    }

    public boolean isInCastle() {
        if ((x >= CASTLE_MIN_X_COORDINATE && x <= CASTLE_MAX_X_COORDINATE)
                && (y >= CASTLE_HAN_MIN_Y_COORDINATE && y <= CASTLE_HAN_MAX_Y_COORDINATE)) {
            return true;
        }
        if ((x >= CASTLE_MIN_X_COORDINATE && x <= CASTLE_MAX_X_COORDINATE)
                && (y >= CASTLE_CHO_MIN_Y_COORDINATE && y <= CASTLE_CHO_MAX_Y_COORDINATE)) {
            return true;
        }
        return false;
    }

    private boolean isInvalidX(int x) {
        return x < BOARD_MIN_WIDTH || x > BOARD_MAX_WIDTH;
    }

    private void validateXCoordinate(int x) {
        if (isInvalidX(x)) {
            throw new IllegalArgumentException(
                    String.format("가로 좌표는 %d에서 %d사이여야 합니다.", BOARD_MIN_WIDTH, BOARD_MAX_WIDTH));
        }
    }

    private boolean isInvalidY(int y) {
        return y < BOARD_MIN_HEIGHT || y > BOARD_MAX_HEIGHT;
    }

    private void validateYCoordinate(int y) {
        if (isInvalidY(y)) {
            throw new IllegalArgumentException(
                    String.format("세로 좌표는 %d에서 %d사이여야 합니다.", BOARD_MIN_HEIGHT, BOARD_MAX_HEIGHT));
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Coordinate that = (Coordinate) object;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "coordinate.Coordinate{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
