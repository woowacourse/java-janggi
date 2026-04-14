package domain.moverule;

import domain.MoveRoute;
import domain.MoveRules;
import domain.Position;
import java.util.Arrays;
import java.util.List;
import java.util.function.UnaryOperator;

public enum ElephantMoveRule implements MoveRules {

    UP_CROSS_RIGHT_CROSS_RIGHT(List.of(Position::up, Position::upCrossRight, Position::upCrossRight)),
    UP_CROSS_LEFT_CROSS_LEFT(List.of(Position::up, Position::upCrossLeft, Position::upCrossLeft)),

    DOWN_CROSS_RIGHT_CROSS_RIGHT(List.of(Position::down, Position::downCrossRight, Position::downCrossRight)),
    DOWN_CROSS_LEFT_CROSS_LEFT(List.of(Position::down, Position::downCrossLeft, Position::downCrossLeft)),

    RIGHT_CROSS_UP_CROSS_UP(List.of(Position::right, Position::upCrossRight, Position::upCrossRight)),
    RIGHT_CROSS_DOWN_CROSS_DOWN(List.of(Position::right, Position::downCrossRight, Position::downCrossRight)),

    LEFT_CROSS_UP_CROSS_UP(List.of(Position::left, Position::upCrossLeft, Position::upCrossLeft)),
    LEFT_CROSS_DOWN_CROSS_DOWN(List.of(Position::left, Position::downCrossLeft, Position::downCrossLeft)),
    ;

    private final List<UnaryOperator<Position>> moveSteps;

    ElephantMoveRule(List<UnaryOperator<Position>> moveSteps) {
        this.moveSteps = moveSteps;
    }

    public static List<MoveRoute> moveRoutesOf(Position currentPosition) {
        return Arrays.stream(ElephantMoveRule.values())
                .map(rule -> moveRoutesFrom(currentPosition, rule))
                .toList();
    }

    private static MoveRoute moveRoutesFrom(Position currentPosition, ElephantMoveRule rule) {
        return new MoveRoute(rule.destination(currentPosition), rule.route(currentPosition));
    }

    @Override
    public List<UnaryOperator<Position>> moveSteps() {
        return this.moveSteps;
    }
}
