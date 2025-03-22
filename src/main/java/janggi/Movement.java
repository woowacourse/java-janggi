package janggi;

import janggi.board.Position;
import java.util.function.Function;

public enum Movement {
    UP(Position::up),
    DOWN(Position::down),
    LEFT(Position::left),
    RIGHT(Position::right),
    LEFT_UP(Position::leftUp),
    RIGHT_UP(Position::rightUp),
    LEFT_DOWN(Position::leftDown),
    RIGHT_DOWN(Position::rightDown);

    private final Function<Position, Position> movement;

    Movement(Function<Position, Position> movement) {
        this.movement = movement;
    }

    public Position movePosition(final Position position) {
        return movement.apply(position);
    }
}
