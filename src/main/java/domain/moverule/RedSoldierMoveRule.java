package domain.moverule;

import domain.MoveRoute;
import domain.MoveRules;
import domain.Position;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public enum RedSoldierMoveRule implements MoveRules {

    DOWN(List.of(Position::down)),
    LEFT(List.of(Position::left)),
    RIGHT(List.of(Position::right)),
    ;

    private final List<UnaryOperator<Position>> moveSteps;

    RedSoldierMoveRule(List<UnaryOperator<Position>> moveSteps) {
        this.moveSteps = moveSteps;
    }

    public static List<MoveRoute> moveRoutesOf(Position currentPosition) {
        Stream<MoveRoute> destinations = Arrays.stream(RedSoldierMoveRule.values())
                .map(rule -> moveRoutesFrom(currentPosition, rule));

        Stream<MoveRoute> additionalDestinations = RedSoldiersExtraPalaceMoveRule.additionalDestinations(currentPosition).stream();

        return Stream.concat(destinations, additionalDestinations).toList();
    }

    private static MoveRoute moveRoutesFrom(Position currentPosition, RedSoldierMoveRule rule) {
        return new MoveRoute(rule.destination(currentPosition), rule.route(currentPosition));
    }

    @Override
    public List<UnaryOperator<Position>> moveSteps() {
        return this.moveSteps;
    }

    private enum RedSoldiersExtraPalaceMoveRule implements MoveRules {

        DOWN_CROSS_RIGHT(List.of(Position::downCrossRight),
                position -> position.isPalaceGreenWestNorth() || position.isPalaceGreenCenter()),

        DOWN_CROSS_LEFT(List.of(Position::downCrossLeft),
                position -> position.isPalaceGreenEastNorth() || position.isPalaceGreenCenter()),
        ;

        private final List<UnaryOperator<Position>> moveSteps;
        private final Predicate<Position> condition;

        RedSoldiersExtraPalaceMoveRule(List<UnaryOperator<Position>> moveSteps, Predicate<Position> condition) {
            this.moveSteps = moveSteps;
            this.condition = condition;
        }

        private static List<MoveRoute> additionalDestinations(Position position) {
            return Arrays.stream(values())
                    .filter(rule -> rule.condition.test(position))
                    .map(rule -> moveRoutesFrom(position, rule))
                    .toList();
        }

        @Override
        public List<UnaryOperator<Position>> moveSteps() {
            return this.moveSteps;
        }

        private static MoveRoute moveRoutesFrom(Position currentPosition, RedSoldiersExtraPalaceMoveRule rule) {
            return new MoveRoute(rule.destination(currentPosition), rule.route(currentPosition));
        }
    }
}
