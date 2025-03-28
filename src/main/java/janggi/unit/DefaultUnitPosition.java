package janggi.unit;

import janggi.position.Position;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Supplier;

public enum DefaultUnitPosition {
    KING(1, 8, List.of(4), NoneUnitRule::new),
    SCHOLAR(0, 9, List.of(3, 5), NoneUnitRule::new),
    CAR(0, 9, List.of(0, 8), ChariotUnitRule::new),
    BOMB(2, 7, List.of(1, 7), CannonUnitRule::new),
    JOL(3, 6, List.of(0, 2, 4, 6, 8), SoldierUnitRule::new),
    HORSE(0, 9, List.of(2, 7), HorseUnitRule::new),
    ELEPHANT(0, 9, List.of(1, 6), ElephantUnitRule::new),
    ;

    private final int hanY;
    private final int choY;
    private final List<Integer> xPositions;
    private final Supplier<UnitRule> rule;

    DefaultUnitPosition(int hanY, int choY, List<Integer> xPositions, Supplier<UnitRule> rule) {
        this.hanY = hanY;
        this.choY = choY;
        this.xPositions = xPositions;
        this.rule = rule;
    }

    public static Map<Position, Unit> createDefaultUnits(DefaultUnitPosition position, Team team) {
        Map<Position, Unit> units = new HashMap<>();
        if (team == Team.CHO) {
            position.xPositions.forEach(xPosition -> units.put(new Position(xPosition, position.choY),
                    Unit.of(team, position.rule.get())));
        }
        if (team == Team.HAN) {
            position.xPositions.forEach(xPosition -> units.put(new Position(xPosition, position.hanY),
                    Unit.of(team, position.rule.get())));
        }
        return units;
    }
}
