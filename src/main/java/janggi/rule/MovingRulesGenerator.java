package janggi.rule;

import java.util.ArrayList;
import java.util.List;

public final class MovingRulesGenerator {

    private static final MoveVector RIGHT = new MoveVector(0, 1);
    private static final MoveVector LEFT = new MoveVector(0, -1);
    private static final MoveVector UP = new MoveVector(-1, 0);
    private static final MoveVector DOWN = new MoveVector(1, 0);
    private static final MoveVector RIGHT_UP = new MoveVector(-1, 1);
    private static final MoveVector RIGHT_DOWN = new MoveVector(1, 1);
    private static final MoveVector LEFT_DOWN = new MoveVector(1, -1);
    private static final MoveVector LEFT_UP = new MoveVector(-1, -1);
    private static final int HORIZONTAL_RANGE = 9;
    private static final int VERTICAL_RANGE = 10;

    public static MovingRules generalOrGuard() {
        return new MovingRules(
                List.of(
                        new MovingRule(List.of(RIGHT)),
                        new MovingRule(List.of(LEFT)),
                        new MovingRule(List.of(UP)),
                        new MovingRule(List.of(DOWN))
                )
        );
    }

    public static MovingRules hanSoldier() {
        return new MovingRules(
                List.of(
                        new MovingRule(List.of(RIGHT)),
                        new MovingRule(List.of(LEFT)),
                        new MovingRule(List.of(DOWN))
                )
        );
    }

    public static MovingRules choSoldier() {
        return new MovingRules(
                List.of(
                        new MovingRule(List.of(RIGHT)),
                        new MovingRule(List.of(LEFT)),
                        new MovingRule(List.of(UP))
                )
        );
    }

    public static MovingRules horse() {
        return new MovingRules(
                List.of(
                        new MovingRule(List.of(RIGHT, RIGHT_UP)),
                        new MovingRule(List.of(RIGHT, RIGHT_DOWN)),
                        new MovingRule(List.of(DOWN, RIGHT_DOWN)),
                        new MovingRule(List.of(DOWN, LEFT_DOWN)),
                        new MovingRule(List.of(LEFT, LEFT_DOWN)),
                        new MovingRule(List.of(LEFT, LEFT_UP)),
                        new MovingRule(List.of(UP, LEFT_UP)),
                        new MovingRule(List.of(UP, RIGHT_UP))
                )
        );
    }

    public static MovingRules elephant() {
        return new MovingRules(
                List.of(
                        new MovingRule(List.of(RIGHT, RIGHT_UP, RIGHT_UP)),
                        new MovingRule(List.of(RIGHT, RIGHT_DOWN, RIGHT_DOWN)),
                        new MovingRule(List.of(DOWN, RIGHT_DOWN, RIGHT_DOWN)),
                        new MovingRule(List.of(DOWN, LEFT_DOWN, LEFT_DOWN)),
                        new MovingRule(List.of(LEFT, LEFT_DOWN, LEFT_DOWN)),
                        new MovingRule(List.of(LEFT, LEFT_UP, LEFT_UP)),
                        new MovingRule(List.of(UP, LEFT_UP, LEFT_UP)),
                        new MovingRule(List.of(UP, RIGHT_UP, RIGHT_UP))
                )
        );
    }

    public static MovingRules cannonOrChariot() {
        List<MovingRule> movingRules = new ArrayList<>();
        addStraightRules(movingRules, LEFT, HORIZONTAL_RANGE);
        addStraightRules(movingRules, RIGHT, HORIZONTAL_RANGE);
        addStraightRules(movingRules, UP, VERTICAL_RANGE);
        addStraightRules(movingRules, DOWN, VERTICAL_RANGE);
        return new MovingRules(movingRules);
    }

    private static void addStraightRules(final List<MovingRule> movingRules, final MoveVector direction,
                                         final int range) {
        for (int i = 1; i < range; i++) {
            List<MoveVector> units = new ArrayList<>();
            addUnit(direction, i, units);
            movingRules.add(new MovingRule(units));
        }
    }

    private static void addUnit(final MoveVector direction, final int count, final List<MoveVector> units) {
        for (int j = 0; j < count; j++) {
            units.add(direction);
        }
    }
}
