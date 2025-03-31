package domain.unit;

import domain.position.Position;
import domain.position.Routes;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Units {
    private final Map<Position, Unit> units;

    private Units(Map<Position, Unit> units) {
        this.units = new HashMap<>(units);
    }

    public static Units of(Map<Position, Unit> hanUnits, Map<Position, Unit> choUnits) {
        Map<Position, Unit> units = new HashMap<>();
        units.putAll(hanUnits);
        units.putAll(choUnits);
        return new Units(units);
    }

    public static Units of(Map<Position, Unit> units) {
        return new Units(units);
    }

    public void moveUnit(Position source, Position destination) {
        if (isEmptyPosition(source)) {
            return;
        }
        Unit sourceUnit = units.get(source);
        removeUnitAt(source);
        units.put(destination, sourceUnit);
    }

    public Routes getUnitRoutes(Position pick) {
        if (isNotEmptyPosition(pick)) {
            return units.get(pick).calculateRoutes(pick);
        }
        return Routes.of(List.of());
    }

    public void removeUnitAt(Position position) {
        units.remove(position);
    }

    public void killGeneralOf(Team team) {
        units.entrySet().removeIf(entry -> {
            Unit unit = entry.getValue();
            return unit.getTeam() == team && unit.getType() == UnitType.GENERAL;
        });
    }

    public int calculateScoreOf(Team team) {
        return units.values().stream()
                .filter(unit -> unit.isSameTeam(team))
                .mapToInt(Unit::getScore)
                .sum();
    }

    public boolean isGeneralNotExistIn(Team team) {
        return units.values().stream()
                .noneMatch(unit -> unit.isSameTeam(team) && unit.isSameType(UnitType.GENERAL));
    }

    public boolean existOnlyGeneralAndGuard() {
        return units.values().stream()
                .noneMatch(unit -> !unit.isSameType(UnitType.GENERAL) &&
                        !unit.isSameType(UnitType.GUARD));
    }

    public boolean isOppositeTeam(Position position, Position other) {
        if (isEmptyPosition(position) || isEmptyPosition(other)) {
            return false;
        }
        return units.get(position).isOppositeTeamWith(units.get(other));
    }

    public boolean isUnitTeamNotEqualAt(Position position, Team compare) {
        if (isNotEmptyPosition(position)) {
            return !units.get(position).isSameTeam(compare);
        }
        return true;
    }

    public boolean isUnitSameType(Position position, UnitType type) {
        if (isEmptyPosition(position)) {
            return false;
        }
        return units.get(position).isSameType(type);
    }

    public boolean isEmptyPosition(Position position) {
        return !isNotEmptyPosition(position);
    }

    public boolean isNotEmptyPosition(Position position) {
        return units.containsKey(position);
    }

    public Map<Position, Unit> getAllUnits() {
        return new HashMap<>(units);
    }
}
