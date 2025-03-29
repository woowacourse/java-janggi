package domain.unit;

import domain.position.Position;
import domain.unit.move.ElephantMovingStrategy;
import domain.unit.move.HorseMovingStrategy;
import domain.unit.move.MovingStrategy;
import domain.unit.move.OneStepMovingStrategy;
import domain.unit.move.StraightMovingStrategy;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public enum UnitType {

    GENERAL(1, 8, List.of(4), OneStepMovingStrategy::new),
    GUARD(0, 9, List.of(3, 5), OneStepMovingStrategy::new),
    CHARIOT(0, 9, List.of(0, 8), StraightMovingStrategy::new),
    CANNON(2, 7, List.of(1, 7), StraightMovingStrategy::new),
    SOLDIER(3, 6, List.of(0, 2, 4, 6, 8), OneStepMovingStrategy::new),
    HORSE(0, 9, List.of(2, 7), HorseMovingStrategy::new),
    ELEPHANT(0, 9, List.of(1, 6), ElephantMovingStrategy::new),
    ;

    private final int hanY;
    private final int choY;
    private final List<Integer> xPositions;
    private final Supplier<MovingStrategy> rule;

    UnitType(int hanY, int choY, List<Integer> xPositions, Supplier<MovingStrategy> rule) {
        this.hanY = hanY;
        this.choY = choY;
        this.xPositions = xPositions;
        this.rule = rule;
    }

    public static Map<Position, Unit> createDefaultUnits(UnitType position, Team team) {
        if (team == Team.CHO) {
            return position.xPositions.stream()
                    .map(x -> Position.of(x, position.choY))
                    .collect(Collectors.toMap(pos -> pos,
                            pos -> Unit.of(team, position.rule.get(), position)));
        }
        return position.xPositions.stream()
                .map(x -> Position.of(x, position.hanY))
                .collect(Collectors.toMap(pos -> pos,
                        pos -> Unit.of(team, position.rule.get(), position)));
    }

    public boolean canNotMoveOutOfPalace() {
        return (this == GENERAL || this == GUARD);
    }
}
