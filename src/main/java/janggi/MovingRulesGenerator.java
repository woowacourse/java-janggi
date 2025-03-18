package janggi;

import java.util.ArrayList;
import java.util.List;

public final class MovingRulesGenerator {

    public static List<MovingRule> generalOrGuard() {
        MoveUnit moveUnit1 = new MoveUnit(0, 1);
        MovingRule rule1 = new MovingRule(List.of(moveUnit1));

        MoveUnit moveUnit2 = new MoveUnit(0, -1);
        MovingRule rule2 = new MovingRule(List.of(moveUnit2));

        MoveUnit moveUnit3 = new MoveUnit(-1, 0);
        MovingRule rule3 = new MovingRule(List.of(moveUnit3));

        MoveUnit moveUnit4 = new MoveUnit(1, 0);
        MovingRule rule4 = new MovingRule(List.of(moveUnit4));

        return List.of(rule1, rule2, rule3, rule4);
    }

    public static List<MovingRule> hanSoldier() {
        MoveUnit moveUnit1 = new MoveUnit(0, 1);
        MovingRule rule1 = new MovingRule(List.of(moveUnit1));

        MoveUnit moveUnit2 = new MoveUnit(0, -1);
        MovingRule rule2 = new MovingRule(List.of(moveUnit2));

        MoveUnit moveUnit3 = new MoveUnit(1, 0);
        MovingRule rule3 = new MovingRule(List.of(moveUnit3));

        return List.of(rule1, rule2, rule3, rule3);
    }

    public static List<MovingRule> choSoldier() {
        MoveUnit moveUnit1 = new MoveUnit(0, 1);
        MovingRule rule1 = new MovingRule(List.of(moveUnit1));

        MoveUnit moveUnit2 = new MoveUnit(0, -1);
        MovingRule rule2 = new MovingRule(List.of(moveUnit2));

        MoveUnit moveUnit3 = new MoveUnit(-1, 0);
        MovingRule rule3 = new MovingRule(List.of(moveUnit3));

        return List.of(rule1, rule2, rule3, rule3);
    }

    public static List<MovingRule> horse() {
        final MovingRule movingRule1 = new MovingRule(List.of(new MoveUnit(0, 1), new MoveUnit(-1, 1)));
        final MovingRule movingRule2 = new MovingRule(List.of(new MoveUnit(0, 1), new MoveUnit(1, 1)));
        final MovingRule movingRule3 = new MovingRule(List.of(new MoveUnit(1, 0), new MoveUnit(1, 1)));
        final MovingRule movingRule4 = new MovingRule(List.of(new MoveUnit(1, 0), new MoveUnit(1, -1)));
        final MovingRule movingRule5 = new MovingRule(List.of(new MoveUnit(0, -1), new MoveUnit(1, -1)));
        final MovingRule movingRule6 = new MovingRule(List.of(new MoveUnit(0, -1), new MoveUnit(-1, -1)));
        final MovingRule movingRule7 = new MovingRule(List.of(new MoveUnit(-1, 0), new MoveUnit(-1, -1)));
        final MovingRule movingRule8 = new MovingRule(List.of(new MoveUnit(-1, 0), new MoveUnit(-1, 1)));
        return List.of(movingRule1, movingRule2, movingRule3, movingRule4, movingRule5, movingRule6, movingRule7,
                movingRule8);
    }

    public static List<MovingRule> elephant() {
        final MovingRule movingRule1 = new MovingRule(
                List.of(new MoveUnit(0, 1), new MoveUnit(-1, 1), new MoveUnit(-1, 1)));
        final MovingRule movingRule2 = new MovingRule(
                List.of(new MoveUnit(0, 1), new MoveUnit(1, 1), new MoveUnit(1, 1)));
        final MovingRule movingRule3 = new MovingRule(
                List.of(new MoveUnit(1, 0), new MoveUnit(1, 1), new MoveUnit(1, 1)));
        final MovingRule movingRule4 = new MovingRule(
                List.of(new MoveUnit(1, 0), new MoveUnit(1, -1), new MoveUnit(1, -1)));
        final MovingRule movingRule5 = new MovingRule(
                List.of(new MoveUnit(0, -1), new MoveUnit(1, -1), new MoveUnit(1, -1)));
        final MovingRule movingRule6 = new MovingRule(
                List.of(new MoveUnit(0, -1), new MoveUnit(-1, -1), new MoveUnit(-1, -1)));
        final MovingRule movingRule7 = new MovingRule(
                List.of(new MoveUnit(-1, 0), new MoveUnit(-1, -1), new MoveUnit(-1, -1)));
        final MovingRule movingRule8 = new MovingRule(
                List.of(new MoveUnit(-1, 0), new MoveUnit(-1, 1), new MoveUnit(-1, 1)));
        return List.of(movingRule1, movingRule2, movingRule3, movingRule4, movingRule5, movingRule6, movingRule7,
                movingRule8);
    }

    public static List<MovingRule> cannonOrChariot() {
        List<MovingRule> movingRules = new ArrayList<>();

        for (int i = 1; i < 9; i++) {
            List<MoveUnit> units = new ArrayList<>();
            for (int j = 0; j < i; j++) {
                units.add(new MoveUnit(0, 1));
            }
            movingRules.add(new MovingRule(units));
        }

        for (int i = 1; i < 9; i++) {
            List<MoveUnit> units = new ArrayList<>();
            for (int j = 0; j < i; j++) {
                units.add(new MoveUnit(0, -1));
            }
            movingRules.add(new MovingRule(units));
        }

        for (int i = 1; i < 10; i++) {
            List<MoveUnit> units = new ArrayList<>();
            for (int j = 0; j < i; j++) {
                units.add(new MoveUnit(-1, 0));
            }
            movingRules.add(new MovingRule(units));
        }

        for (int i = 1; i < 10; i++) {
            List<MoveUnit> units = new ArrayList<>();
            for (int j = 0; j < i; j++) {
                units.add(new MoveUnit(+1, 0));
            }
            movingRules.add(new MovingRule(units));
        }

        return movingRules;
    }
}
