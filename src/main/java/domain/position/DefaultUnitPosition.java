package domain.position;

import domain.unit.Team;
import domain.unit.Unit;
import domain.unit.rule.CannonUnitRule;
import domain.unit.rule.ChariotUnitRule;
import domain.unit.rule.ElephantUnitRule;
import domain.unit.rule.HorseUnitRule;
import domain.unit.rule.NoneUnitRule;
import domain.unit.rule.SoldierUnitRule;
import domain.unit.rule.UnitRule;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public enum DefaultUnitPosition {

    GENERAL(1, 8, List.of(4), NoneUnitRule::new),
    GUARD(0, 9, List.of(3, 5), NoneUnitRule::new),
    CHARIOT(0, 9, List.of(0, 8), ChariotUnitRule::new),
    CANNON(2, 7, List.of(1, 7), CannonUnitRule::new),
    SOLDIER(3, 6, List.of(0, 2, 4, 6, 8), SoldierUnitRule::new),
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
        if (team == Team.CHO) {
            return position.xPositions.stream()
                    .map(x -> Position.of(x, position.choY))
                    .collect(Collectors.toMap(pos -> pos, pos -> Unit.of(team, position.rule.get())
                    ));
        }
        return position.xPositions.stream()
                .map(x -> Position.of(x, position.hanY))
                .collect(Collectors.toMap(pos -> pos, pos -> Unit.of(team, position.rule.get())));
    }
}
