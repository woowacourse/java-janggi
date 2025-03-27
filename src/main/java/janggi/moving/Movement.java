package janggi.moving;

import janggi.board.position.Position;
import java.util.function.Function;
import java.util.function.Predicate;

public enum Movement {
    UP(Position::canUp, Position::up),
    DOWN(Position::canDown, Position::down),
    LEFT(Position::canLeft, Position::left),
    RIGHT(Position::canRight, Position::right),
    LEFT_UP(Position::canLeftUp, Position::leftUp),
    RIGHT_UP(Position::canRightUp, Position::rightUp),
    LEFT_DOWN(Position::canLeftDown, Position::leftDown),
    RIGHT_DOWN(Position::canRightDown, Position::rightDown),
    UP_STRAIGHT(Position::canUp, Position::up),
    DOWN_STRAIGHT(Position::canDown, Position::down),
    LEFT_STRAIGHT(Position::canLeft, Position::left),
    RIGHT_STRAIGHT(Position::canRight, Position::right);

    private final Predicate<Position> canMove;
    private final Function<Position, Position> move;

    Movement(Predicate<Position> canMove, Function<Position, Position> move) {
        this.canMove = canMove;
        this.move = move;
    }

    public Position movePosition(final Position position) {
        return move.apply(position);
    }

    public boolean canMove(Position position) {
        return canMove.test(position);
    }

    public boolean cannotMove(Position position) {
        return !canMove(position);
    }
}
