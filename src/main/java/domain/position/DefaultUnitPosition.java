package domain.position;

import domain.unit.Team;
import domain.unit.Unit;
import domain.unit.rule.CannonMovingStrategy;
import domain.unit.rule.ChariotMovingStrategy;
import domain.unit.rule.ElephantMovingStrategy;
import domain.unit.rule.GeneralMovingStrategy;
import domain.unit.rule.GuardMovingStrategy;
import domain.unit.rule.HorseMovingStrategy;
import domain.unit.rule.MovingStrategy;
import domain.unit.rule.SoldierMovingStrategy;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public enum DefaultUnitPosition {

    GENERAL(1, 8, List.of(4), GeneralMovingStrategy::new),
    GUARD(0, 9, List.of(3, 5), GuardMovingStrategy::new),
    CHARIOT(0, 9, List.of(0, 8), ChariotMovingStrategy::new),
    CANNON(2, 7, List.of(1, 7), CannonMovingStrategy::new),
    SOLDIER(3, 6, List.of(0, 2, 4, 6, 8), SoldierMovingStrategy::new),
    HORSE(0, 9, List.of(2, 7), HorseMovingStrategy::new),
    ELEPHANT(0, 9, List.of(1, 6), ElephantMovingStrategy::new),
    ;

    private final int hanY;
    private final int choY;
    private final List<Integer> xPositions;
    private final Supplier<MovingStrategy> rule;

    DefaultUnitPosition(int hanY, int choY, List<Integer> xPositions, Supplier<MovingStrategy> rule) {
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
