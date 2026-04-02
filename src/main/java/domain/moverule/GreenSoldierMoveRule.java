package domain.moverule;

import domain.MoveRoute;
import domain.MoveRules;
import domain.Position;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public enum GreenSoldierMoveRule implements MoveRules {

    UP(List.of(Position::up)),
    LEFT(List.of(Position::left)),
    RIGHT(List.of(Position::right)),
    ;

    private final List<UnaryOperator<Position>> moveSteps;

    GreenSoldierMoveRule(List<UnaryOperator<Position>> moveSteps) {
        this.moveSteps = moveSteps;
    }

    public static List<MoveRoute> moveRoutesOf(Position currentPosition) {
        Stream<MoveRoute> destinations = Arrays.stream(GreenSoldierMoveRule.values())
                .map(rule -> moveRoutesFrom(currentPosition, rule));

        Stream<MoveRoute> additionalDestinations = GreenSoldiersExtraPalaceMoveRule.additionalDestinations(currentPosition).stream();

        return Stream.concat(destinations, additionalDestinations).toList();
    }

    private static MoveRoute moveRoutesFrom(Position currentPosition, GreenSoldierMoveRule rule) {
        return new MoveRoute(rule.destination(currentPosition), rule.route(currentPosition));
    }

    @Override
    public List<UnaryOperator<Position>> moveSteps() {
        return this.moveSteps;
    }

    private enum GreenSoldiersExtraPalaceMoveRule implements MoveRules {
        UP_CROSS_RIGHT(List.of(Position::upCrossRight),
                position -> position.isPalaceRedWestSouth() || position.isPalaceRedCenter()),

        UP_CROSS_LEFT(List.of(Position::upCrossLeft),
                position -> position.isPalaceRedEastSouth() || position.isPalaceRedCenter()),
        ;

        private final List<UnaryOperator<Position>> moveSteps;
        private final Predicate<Position> condition;

        GreenSoldiersExtraPalaceMoveRule(List<UnaryOperator<Position>> moveSteps, Predicate<Position> condition) {
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

        private static MoveRoute moveRoutesFrom(Position currentPosition, GreenSoldiersExtraPalaceMoveRule rule) {
            return new MoveRoute(rule.destination(currentPosition), rule.route(currentPosition));
        }
    }
}
