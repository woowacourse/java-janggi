package domain.moverule;

import domain.MoveRoute;
import domain.MoveRules;
import domain.Position;
import java.util.Arrays;
import java.util.List;
import java.util.function.UnaryOperator;

public enum HorseMoveRule implements MoveRules {

    UP_CROSS_RIGHT(List.of(Position::up, Position::upCrossRight)),
    UP_CROSS_LEFT(List.of(Position::up, Position::upCrossLeft)),

    DOWN_CROSS_RIGHT(List.of(Position::down, Position::downCrossRight)),
    DOWN_CROSS_LEFT(List.of(Position::down, Position::downCrossLeft)),

    RIGHT_CROSS_UP(List.of(Position::right, Position::upCrossRight)),
    RIGHT_CROSS_DOWN(List.of(Position::right, Position::downCrossRight)),

    LEFT_CROSS_UP(List.of(Position::left, Position::upCrossLeft)),
    LEFT_CROSS_DOWN(List.of(Position::left, Position::downCrossLeft)),
    ;

    private final List<UnaryOperator<Position>> moveSteps;

    HorseMoveRule(List<UnaryOperator<Position>> moveSteps) {
        this.moveSteps = moveSteps;
    }

    public static List<MoveRoute> moveRoutesOf(Position currentPosition) {
        return Arrays.stream(HorseMoveRule.values())
                .map(rule -> moveRoutesFrom(currentPosition, rule))
                .toList();
    }

    private static MoveRoute moveRoutesFrom(Position currentPosition, HorseMoveRule rule) {
        return new MoveRoute(rule.destination(currentPosition), rule.route(currentPosition));
    }

    @Override
    public List<UnaryOperator<Position>> moveSteps() {
        return this.moveSteps;
    }
}
