package domain;

import domain.unit.BombUnitRule;
import domain.unit.CarUnitRule;
import domain.unit.ElephantUnitRule;
import domain.unit.HorseUnitRule;
import domain.unit.JolUnitRule;
import domain.unit.NoneUnitRule;
import domain.unit.Point;
import domain.unit.Team;
import domain.unit.Unit;
import domain.unit.UnitRule;
import java.util.List;
import java.util.function.Supplier;

public enum DefaultUnitPosition {
    KING(1, 8, List.of(4), NoneUnitRule::new),
    SCHOLAR(0, 9, List.of(3, 5), NoneUnitRule::new),
    CAR(0, 9, List.of(0, 8), CarUnitRule::new),
    BOMB(2, 7, List.of(1, 7), BombUnitRule::new),
    JOL(3, 6, List.of(0, 2, 4, 6, 8), JolUnitRule::new),
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

    public static List<Unit> createDefaultUnits(DefaultUnitPosition position, Team team) {
        if (team == Team.CHO) {
            return position.xPositions.stream()
                    .map(x -> Unit.of(new Point(x, position.choY), team, position.rule.get())).toList();
        }
        return position.xPositions.stream()
                .map(x -> Unit.of(new Point(x, position.hanY), team, position.rule.get())).toList();
    }
}
