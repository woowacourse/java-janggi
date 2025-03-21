package janggi.piece;

import janggi.value.Position;
import java.util.List;

public enum BeelineDirection {
    LEFT,
    RIGHT,
    UP,
    DOWN,
    NONE;

    public static BeelineDirection parse(Position current, Position destination) {
        if (current.isEqualsXPosition(destination.getX())) {
            return parseWithYAxis(current.getY(), destination.getY());
        }
        return parseWithXAxis(current.getX(), destination.getX());
    }

    public List<Position> calculatePositionsInPath(Position current, Position destination) {
        if (this == LEFT) {
            return Position.makePositionInXLine(destination.getX() - 1, current.getX() - 1, current.getY());
        }
        if (this == RIGHT) {
            return Position.makePositionInXLine(current.getX() - 1, destination.getX() - 1, current.getY());
        }
        if (this == UP) {
            return Position.makePositionInYLine(destination.getY() - 1, current.getY() - 1, current.getX());
        }
        if (this == DOWN) {
            return Position.makePositionInYLine(current.getY() - 1, destination.getY() - 1, current.getX());
        }
        return List.of();
    }

    private static BeelineDirection parseWithYAxis(int currentY, int destinationY) {
        if (currentY == destinationY) {
            return NONE;
        }
        if (currentY < destinationY) {
            return UP;
        }
        return DOWN;
    }

    private static BeelineDirection parseWithXAxis(int currentX, int destinationX) {
        if (currentX == destinationX) {
            return NONE;
        }
        if (currentX < destinationX) {
            return RIGHT;
        }
        return LEFT;
    }
}
