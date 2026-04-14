package domain.moverule;

import domain.MoveRoute;
import domain.MoveRules;
import domain.Position;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public enum ExtraPalaceMoveDirection {

    DOWN_CROSS_RIGHT(Position::downCrossRight,
            position -> position.isPalaceRedWestNorth() || position.isPalaceGreenWestNorth()),

    DOWN_CROSS_LEFT(Position::downCrossLeft,
            position -> position.isPalaceRedEastNorth() || position.isPalaceGreenEastNorth()),

    UP_CROSS_RIGHT(Position::upCrossRight,
            position -> position.isPalaceRedWestSouth() || position.isPalaceGreenWestSouth()),

    UP_CROSS_LEFT(Position::upCrossLeft,
            position -> position.isPalaceRedEastSouth() || position.isPalaceGreenEastSouth() ),
    ;

    private final UnaryOperator<Position> moveSteps;
    private final Predicate<Position> condition;

    ExtraPalaceMoveDirection(UnaryOperator<Position> moveSteps, Predicate<Position> condition) {
        this.moveSteps = moveSteps;
        this.condition = condition;
    }

    public static Optional<UnaryOperator<Position>> additionalDirection(Position currentPosition) {
        return Arrays.stream(values())
                .filter(rule -> rule.condition.test(currentPosition))
                .map(rule -> rule.moveSteps).findAny();
    }
}
