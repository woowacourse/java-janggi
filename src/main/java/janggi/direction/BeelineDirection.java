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
        if (current.isEqualsXPosition(destination.x())) {
            return parseWithYAxis(current.y(), destination.y());
        }
        if (current.isEqualsYPosition(destination.y())) {
            return parseWithXAxis(current.x(), destination.x());
        }
        return NONE;
    }

    public List<Position> calculatePositionsInPath(Position current, Position destination) {
        if (this == LEFT) {
            return Position.makePositionInXLine(destination.x() + 1, current.x() - 1, current.y());
        }
        if (this == RIGHT) {
            return Position.makePositionInXLine(current.x() + 1, destination.x() - 1, current.y());
        }
        if (this == UP) {
            return Position.makePositionInYLine(destination.y() + 1, current.y() - 1, current.x());
        }
        if (this == DOWN) {
            return Position.makePositionInYLine(current.y() + 1, destination.y() - 1, current.x());
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
