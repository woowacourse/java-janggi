package domain.moverule;

import domain.MoveRoute;
import domain.MoveRules;
import domain.Position;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public enum PalaceMoveRule  {
//
//    UP(List.of(Position::up)),
//    DOWN(List.of(Position::down)),
//    LEFT(List.of(Position::left)),
//    RIGHT(List.of(Position::right)),
//
//    BASIC_MOVE_RULE(Arrays.stream(BasicMoveRule.values()).map(BasicMoveRule::addBasicMoveRules).toList()),
//    EXTRA_MOVE_RULE(Arrays.stream(ExtraMoveRule.values()).map(ExtraMoveRule::addExtraDirection).toList()),
////    PALACE_SIDE(Arrays.stream(BasicMoveRule.values()).map(rule -> rule.addExtraDirection).toList()),
////
////    PALACE_CONNER(Stream.concat(
////                    Arrays.stream(BasicMoveRule.values()).map(BasicMoveRule::addBasicMoveRules),
////                    Arrays.stream(ExtraMoveRule.values()).map(ExtraMoveRule::addExtraDirection)).toList()
////    ),
////
////    PALACE_CENTER(
////            Arrays.stream(BasicMoveRule.values()).map(BasicMoveRule::addBasicMoveRules).toList()
////    ),
//    ;
//
//    private final List<Function<Position, Position>> moveSteps;
//
//    PalaceMoveRule(List<Function<Position, Position>> moveSteps) {
//        this.moveSteps = moveSteps;
//    }
//
//    @Override
//    public Position destination(Position currentPosition) {
//        return MoveRules.super.destination(currentPosition);
//    }
//
//    @Override
//    public List<Position> route(Position currentPosition) {
//        return MoveRules.super.route(currentPosition);
//    }
//
//    public static List<MoveRoute> moveRoutes(Position currentPosition) {
//        Arrays.stream(BasicMoveRule.values())
//                .flatMap(rule -> {
//                  rule.moveSteps.stream().map(steps -> steps.apply(currentPosition));
//                }).
//
//
//
//        List<MoveRoute> moveRoutes = BASIC_MOVE_RULE.moveSteps.stream()
//                .map(action -> action.apply(currentPosition))
//                .map(destination -> new MoveRoute(destination, rule.route(currentPosition)))
//                .toList();
//
//        if (currentPosition.isInPalace()) {
//            Arrays.stream(ExtraMoveRule.values())
//                    .map(ExtraMoveRule::addExtraDirection)
//                    .map(action -> action.apply(currentPosition))
//                    .filter(Objects::isNull)
//                    .forEach(destination -> moveRoutes.add(new Position(destination, )))
////
////                    .forEach(position -> moveRoutes.add());
////            new MoveRoute(ExtraMoveRule.va);
////            moveRoutes.add()
////            new MoveRoute(destination(currentPosition), route(currentPosition));
//        }
//    }
//
//    @Override
//    public List<Function<Position, Position>> moveSteps() {
//        return this.moveSteps;
//    }
//
//    private enum BasicMoveRule implements MoveRules{
//        UP(List.of(Position::up)),
//        DOWN(List.of(Position::down)),
//        LEFT(List.of(Position::left)),
//        RIGHT(List.of(Position::right)),
//        ;
//
//        private final List<Function<Position, Position>> moveSteps;
//
//        BasicMoveRule(List<Function<Position, Position>> moveSteps) {
//            this.moveSteps = moveSteps;
//        }
//
//        @Override
//        public List<Function<Position, Position>> moveSteps() {
//            return this.moveSteps;
//        }
//    }



//    private enum ExtraMoveRule  {
//        IN_CENTER_OR_IN_EAST_NORTH_CONNER(position -> {
//            if (position.isPalaceGreenCenter()
//                    || position.isPalaceRedCenter()
//                    || position.isPalaceRedEastNorth()
//                    || position.isPalaceGreenEastSouth()) {
//                return position.downCrossLeft();
//            }
//            return null;
//        }),
//
//        IN_CENTER_OR_IN_EAST_SOUTH_CONNER(position -> {
//            if (position.isPalaceGreenCenter()
//                    || position.isPalaceRedCenter()
//                    || position.isPalaceRedEastSouth()
//                    || position.isPalaceGreenEastSouth()) {
//                return position.upCrossLeft();
//            }
//            return null;
//        }),
//
//        IN_CENTER_OR_IN_WEST_NORTH_CONNER(position -> {
//            if (position.isPalaceGreenCenter()
//                    || position.isPalaceRedCenter()
//                    || position.isPalaceGreenWestNorth()
//                    || position.isPalaceGreenWestNorth()) {
//                return position.downCrossRight();
//            }
//            return null;
//        }),
//
//        IN_CENTER_OR_IN_WEST_SOUTH_CONNER(position -> {
//            if (position.isPalaceGreenCenter()
//                    || position.isPalaceRedCenter()
//                    || position.isPalaceRedWestSouth()
//                    || position.isPalaceGreenWestSouth()) {
//                return position.upCrossRight();
//            }
//            return null;
//        }),
//        ;
//
//        private final Function<Position, Position> addExtraDirection;
//
//        ExtraMoveRule(Function<Position, Position> addExtraDirection) {
//            this.addExtraDirection = addExtraDirection;
//        }
//
//        public Function<Position, Position> addExtraDirection() {
//            return addExtraDirection;
//        }
//    }

//    private enum ExtraMoveRule {
//        IN_CENTER_OR_IN_EAST_NORTH_CONNER((position, moveSteps) -> {
//            if (position.isPalaceGreenCenter()
//                    || position.isPalaceRedCenter()
//                    || position.isPalaceRedEastNorth()
//                    || position.isPalaceGreenEastSouth()) {
//                return moveSteps;
//            }
//            return moveSteps;
//        }),
//
//        IN_CENTER_OR_IN_EAST_SOUTH_CONNER((position, moveSteps) -> {
//            if (position.isPalaceGreenCenter()
//                    || position.isPalaceRedCenter()
//                    || position.isPalaceRedEastSouth()
//                    || position.isPalaceGreenEastSouth()) {
//                moveSteps.add(Position::upCrossLeft);
//                return moveSteps;
//            }
//            return moveSteps;
//        }),
//
//        IN_CENTER_OR_IN_WEST_NORTH_CONNER((position, moveSteps) -> {
//            if (position.isPalaceGreenCenter()
//                    || position.isPalaceRedCenter()
//                    || position.isPalaceGreenWestNorth()
//                    || position.isPalaceGreenWestNorth()) {
//                moveSteps.add(Position::downCrossRight);
//                return moveSteps;
//            }
//            return moveSteps;
//        }),
//
//        IN_CENTER_OR_IN_WEST_SOUTH_CONNER((position, moveSteps) -> {
//            if (position.isPalaceGreenCenter()
//                    || position.isPalaceRedCenter()
//                    || position.isPalaceRedWestSouth()
//                    || position.isPalaceGreenWestSouth()) {
//                moveSteps.add(Position::upCrossRight);
//                return moveSteps;
//            }
//            return moveSteps;
//        }),
//        ;
//
//        private final BiFunction<Position, List<Function<Position, Position>>, List<Function<Position, Position>>> addExtraDirection;
//
//        ExtraMoveRule(
//                BiFunction<Position, List<Function<Position, Position>>, List<Function<Position, Position>>> addExtraDirection) {
//            this.addExtraDirection = addExtraDirection;
//        }
//
//        public BiFunction<Position, List<Function<Position, Position>>, List<Function<Position, Position>>> addExtraDirection() {
//            return addExtraDirection;
//        }
//
//        public MoveRoute addedRoute() {
//            return M
//        }
//}
}
