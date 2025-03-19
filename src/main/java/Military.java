import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public class Military {
    private final List<Unit> units;
    private final String team;
    private final int strategy;

    private Military(List<Unit> units, String team, int strategy) {
        this.units = new ArrayList<>(units);
        this.team = team;
        this.strategy = strategy;
    }

    public static Military of(String team, int strategy) {
        return new Military(getUnits(team, strategy), team, strategy);
    }

    // todo: strategy 추가
    private static List<Unit> getUnits(String team, int strategy) {
        List<Unit> units = new ArrayList<>();
        units.addAll(createUnits(DefaultUnitPosition.KING, team, KingUnit::new));
        units.addAll(createUnits(DefaultUnitPosition.SCHOLAR, team, ScholarUnit::new));

        units.addAll(createUnits(DefaultUnitPosition.JOL, team, JolUnit::new));
        units.addAll(createUnits(DefaultUnitPosition.CAR, team, CarUnit::new));
        units.addAll(createUnits(DefaultUnitPosition.BOMB, team, BombUnit::new));

        units.addAll(createUnits(DefaultUnitPosition.ELEPHANT, team, ElephantUnit::new));
        units.addAll(createUnits(DefaultUnitPosition.HORSE, team, HorseUnit::new));

        return units;
    }

    private static List<Unit> createUnits(DefaultUnitPosition position, String team,
                                          BiFunction<Point, String, Unit> factory) {
        List<Point> positions = DefaultUnitPosition.createPositions(position, team);
        return positions.stream()
                .map(point -> factory.apply(point, team))
                .toList();
    }
}
