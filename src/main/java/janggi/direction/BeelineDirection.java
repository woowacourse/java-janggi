package janggi.direction;

import janggi.value.Position;
import java.util.List;
import java.util.function.BiFunction;

public enum BeelineDirection {
    LEFT((current, destination) -> Position.makePositionInXLine(destination.x() + 1, current.x() - 1, current.y())),
    RIGHT((current, destination) -> Position.makePositionInXLine(current.x() + 1, destination.x() - 1, current.y())),
    UP((current, destination) -> Position.makePositionInYLine(destination.y() + 1, current.y() - 1, current.x())),
    DOWN((current, destination) -> Position.makePositionInYLine(current.y() + 1, destination.y() - 1, current.x())),
    NONE((current, destination) -> List.of());

    private final BiFunction<Position, Position, List<Position>> calculatePositionsInPath;

    BeelineDirection(BiFunction<Position, Position, List<Position>> calculatePositionsInPath) {
        this.calculatePositionsInPath = calculatePositionsInPath;
    }

    public static BeelineDirection parse(Position current, Position destination) {
        if (current.isEqualsXPosition(destination.x())) {
            return parseWithYAxis(current.y(), destination.y());
        }
        if (current.isEqualsYPosition(destination.y())) {
            return parseWithXAxis(current.x(), destination.x());
        }
        return NONE;
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

    public BiFunction<Position, Position, List<Position>> getCalculatePositionsInPath() {
        return calculatePositionsInPath;
    }
}
