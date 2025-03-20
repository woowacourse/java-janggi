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

    public static List<MovingRule> generalOrGuard() {
        MovingRule rule1 = new MovingRule(List.of(RIGHT));
        MovingRule rule2 = new MovingRule(List.of(LEFT));
        MovingRule rule3 = new MovingRule(List.of(UP));
        MovingRule rule4 = new MovingRule(List.of(DOWN));
        return List.of(rule1, rule2, rule3, rule4);
    }

    public static List<MovingRule> hanSoldier() {
        MovingRule rule1 = new MovingRule(List.of(RIGHT));
        MovingRule rule2 = new MovingRule(List.of(LEFT));
        MovingRule rule3 = new MovingRule(List.of(DOWN));
        return List.of(rule1, rule2, rule3, rule3);
    }

    public static List<MovingRule> choSoldier() {
        MovingRule rule1 = new MovingRule(List.of(RIGHT));
        MovingRule rule2 = new MovingRule(List.of(LEFT));
        MovingRule rule3 = new MovingRule(List.of(UP));
        return List.of(rule1, rule2, rule3, rule3);
    }

    public static List<MovingRule> horse() {
        final MovingRule movingRule1 = new MovingRule(List.of(RIGHT, RIGHT_UP));
        final MovingRule movingRule2 = new MovingRule(List.of(RIGHT, RIGHT_DOWN));
        final MovingRule movingRule3 = new MovingRule(List.of(DOWN, RIGHT_DOWN));
        final MovingRule movingRule4 = new MovingRule(List.of(DOWN, LEFT_DOWN));
        final MovingRule movingRule5 = new MovingRule(List.of(LEFT, LEFT_DOWN));
        final MovingRule movingRule6 = new MovingRule(List.of(LEFT, LEFT_UP));
        final MovingRule movingRule7 = new MovingRule(List.of(UP, LEFT_UP));
        final MovingRule movingRule8 = new MovingRule(List.of(UP, RIGHT_UP));
        return List.of(movingRule1, movingRule2, movingRule3, movingRule4, movingRule5, movingRule6, movingRule7,
                movingRule8);
    }

    public static List<MovingRule> elephant() {
        final MovingRule movingRule1 = new MovingRule(List.of(RIGHT, RIGHT_UP, RIGHT_UP));
        final MovingRule movingRule2 = new MovingRule(List.of(RIGHT, RIGHT_DOWN, RIGHT_DOWN));
        final MovingRule movingRule3 = new MovingRule(List.of(DOWN, RIGHT_DOWN, RIGHT_DOWN));
        final MovingRule movingRule4 = new MovingRule(List.of(DOWN, LEFT_DOWN, LEFT_DOWN));
        final MovingRule movingRule5 = new MovingRule(List.of(LEFT, LEFT_DOWN, LEFT_DOWN));
        final MovingRule movingRule6 = new MovingRule(List.of(LEFT, LEFT_UP, LEFT_UP));
        final MovingRule movingRule7 = new MovingRule(List.of(UP, LEFT_UP, LEFT_UP));
        final MovingRule movingRule8 = new MovingRule(List.of(UP, RIGHT_UP, RIGHT_UP));
        return List.of(movingRule1, movingRule2, movingRule3, movingRule4, movingRule5, movingRule6, movingRule7,
                movingRule8);
    }

    public static List<MovingRule> cannonOrChariot() {
        List<MovingRule> movingRules = new ArrayList<>();
        addStraightRules(movingRules, LEFT, HORIZONTAL_RANGE);
        addStraightRules(movingRules, RIGHT, HORIZONTAL_RANGE);
        addStraightRules(movingRules, UP, VERTICAL_RANGE);
        addStraightRules(movingRules, DOWN, VERTICAL_RANGE);
        return movingRules;
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
