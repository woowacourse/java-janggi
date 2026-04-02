package domain.position;

import domain.board.Direction;

public class PositionCalculator {
    public static Position add(Position position, Direction direction) {
        return position.append(direction.getdRow(), direction.getdColumn());
    }

    public static PositionDelta subtract(Position to, Position from) {
        return to.minus(from);
    }
}
