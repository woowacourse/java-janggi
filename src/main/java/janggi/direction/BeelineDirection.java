package janggi.direction;

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
        if (current.isEqualsYPosition(destination.getY())) {
            return parseWithXAxis(current.getX(), destination.getX());
        }
        return NONE;
    }

    public List<Position> calculatePositionsInPath(Position current, Position destination) {
        if (this == LEFT) {
            return Position.makePositionInXLine(destination.getX() + 1, current.getX() - 1, current.getY());
        }
        if (this == RIGHT) {
            return Position.makePositionInXLine(current.getX() + 1, destination.getX() - 1, current.getY());
        }
        if (this == UP) {
            return Position.makePositionInYLine(destination.getY() + 1, current.getY() - 1, current.getX());
        }
        if (this == DOWN) {
            return Position.makePositionInYLine(current.getY() + 1, destination.getY() - 1, current.getX());
        }
        return List.of();
    }

    private static BeelineDirection parseWithYAxis(int currentY, int destinationY) {
        if (currentY < destinationY) {
            return DOWN;
        }
        if (currentY > destinationY) {
            return UP;
        }
        return NONE;
    }

    private static BeelineDirection parseWithXAxis(int currentX, int destinationX) {
        if (currentX < destinationX) {
            return RIGHT;
        }
        if (currentX > destinationX) {
            return LEFT;
        }
        return NONE;
    }
}
