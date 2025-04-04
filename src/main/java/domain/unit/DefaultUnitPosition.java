package domain.unit;

import domain.position.Position;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public enum DefaultUnitPosition {
    GENERAL(
            List.of(Position.of(4, 1)),
            List.of(Position.of(4, 8)),
            UnitType.GENERAL
    ),
    GUARD(
            List.of(Position.of(3, 0), Position.of(5, 0)),
            List.of(Position.of(3, 9), Position.of(5, 9)),
            UnitType.GUARD
    ),
    CHARIOT(
            List.of(Position.of(0, 0), Position.of(8, 0)),
            List.of(Position.of(0, 9), Position.of(8, 9)),
            UnitType.CHARIOT
    ),
    CANNON(
            List.of(Position.of(1, 2), Position.of(7, 2)),
            List.of(Position.of(1, 7), Position.of(7, 7)),
            UnitType.CANNON
    ),
    SOLDIER(
            List.of(Position.of(0, 3), Position.of(2, 3), Position.of(4, 3),
                    Position.of(6, 3), Position.of(8, 3)),
            List.of(Position.of(0, 6), Position.of(2, 6), Position.of(4, 6),
                    Position.of(6, 6), Position.of(8, 6)),
            UnitType.SOLDIER
    ),
    HORSE(
            List.of(Position.of(2, 0), Position.of(7, 0)),
            List.of(Position.of(2, 9), Position.of(7, 9)),
            UnitType.HORSE
    ),
    ELEPHANT(
            List.of(Position.of(1, 0), Position.of(6, 0)),
            List.of(Position.of(1, 9), Position.of(6, 9)),
            UnitType.ELEPHANT
    ),
    ;

    private final List<Position> hanPosition;
    private final List<Position> choPosition;
    private final UnitType unitType;

    DefaultUnitPosition(List<Position> hanPosition, List<Position> choPosition, UnitType unitType) {
        this.hanPosition = hanPosition;
        this.choPosition = choPosition;
        this.unitType = unitType;
    }

    public static Map<Position, Unit> createTotalUnits(Team team) {
        Map<Position, Unit> units = new HashMap<>();
        for (DefaultUnitPosition unitPosition : DefaultUnitPosition.values()) {
            units.putAll(createDefaultUnits(unitPosition, team));
        }
        return units;
    }

    private static Map<Position, Unit> createDefaultUnits(DefaultUnitPosition defaultUnitPosition, Team team) {
        if (team == Team.CHO) {
            return defaultUnitPosition.choPosition.stream()
                    .collect(Collectors.toMap(pos -> pos,
                            pos -> defaultUnitPosition.unitType.createUnit(team)));
        }
        return defaultUnitPosition.hanPosition.stream()
                .collect(Collectors.toMap(pos -> pos,
                        pos -> defaultUnitPosition.unitType.createUnit(team)));
    }
}
