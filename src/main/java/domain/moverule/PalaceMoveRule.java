package domain.moverule;

import domain.MoveRoute;
import domain.MoveRules;
import domain.Position;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public enum PalaceMoveRule implements MoveRules {

    UP(List.of(Position::up)),
    DOWN(List.of(Position::down)),
    LEFT(List.of(Position::left)),
    RIGHT(List.of(Position::right)),
    ;

    private final List<UnaryOperator<Position>> moveSteps;

    PalaceMoveRule(List<UnaryOperator<Position>> moveSteps) {
        this.moveSteps = moveSteps;
    }

    public static List<MoveRoute> moveRoutesOf(Position currentPosition) {
        Stream<MoveRoute> destinations = Arrays.stream(PalaceMoveRule.values())
                .map(rule -> moveRoutesFrom(currentPosition, rule));

        Stream<MoveRoute> additionalDestinations = ExtraPalaceMoveRule.additionalDestinations(currentPosition).stream();

        return Stream.concat(destinations, additionalDestinations).toList();
    }

    @Override
    public List<UnaryOperator<Position>> moveSteps() {
        return this.moveSteps;
    }

    private static MoveRoute moveRoutesFrom(Position currentPosition, PalaceMoveRule rule) {
        return new MoveRoute(rule.destination(currentPosition), rule.route(currentPosition));
    }

    private enum ExtraPalaceMoveRule implements MoveRules {

        DOWN_CROSS_RIGHT(List.of(Position::downCrossRight),
                position -> position.isPalaceRedWestNorth() || position.isPalaceRedCenter()
                        || position.isPalaceGreenWestNorth() || position.isPalaceGreenCenter()),

        DOWN_CROSS_LEFT(List.of(Position::downCrossLeft),
                position -> position.isPalaceRedEastNorth() || position.isPalaceRedCenter()
                        || position.isPalaceGreenEastNorth() || position.isPalaceGreenCenter()),

        UP_CROSS_RIGHT(List.of(Position::upCrossRight),
                position -> position.isPalaceRedWestSouth() || position.isPalaceRedCenter()
                        || position.isPalaceGreenWestSouth() || position.isPalaceGreenCenter()),

        UP_CROSS_LEFT(List.of(Position::upCrossLeft),
                position -> position.isPalaceRedEastSouth() || position.isPalaceRedCenter()
                        || position.isPalaceGreenEastSouth() || position.isPalaceGreenCenter()),
        ;

        private final List<UnaryOperator<Position>> moveSteps;
        private final Predicate<Position> condition;

        ExtraPalaceMoveRule(List<UnaryOperator<Position>> moveSteps, Predicate<Position> condition) {
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

        private static MoveRoute moveRoutesFrom(Position currentPosition, ExtraPalaceMoveRule rule) {
            return new MoveRoute(rule.destination(currentPosition), rule.route(currentPosition));
        }
    }
}
