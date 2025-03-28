package janggi.game;

import janggi.position.Position;
import janggi.unit.DefaultUnitPosition;
import janggi.unit.Team;
import janggi.unit.Unit;
import janggi.unit.UnitType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Units {
    private final Map<Position, Unit> units;

    public Units() {
        units = new HashMap<>();
        settingUnits(Team.HAN);
        settingUnits(Team.CHO);
    }

    private void settingUnits(Team team) {
        for (DefaultUnitPosition value : DefaultUnitPosition.values()) {
            units.putAll(DefaultUnitPosition.createDefaultUnits(value, team));
        }
    }

    public Unit findUnitByPosition(Position startPoint) {
        Unit unit = units.getOrDefault(startPoint, null);
        if (unit == null) {
            throw new IllegalArgumentException("해당 위치에 기물이 없습니다.");
        }
        return unit;
    }

    public boolean isExistUnit(Position pick) {
        Unit unit = units.getOrDefault(pick, null);
        return unit != null;
    }

    public boolean isBombUnit(Position position) {
        Unit unit = units.getOrDefault(position, null);
        if (unit == null) {
            return false;
        }
        return unit.getType() == UnitType.BOMB;
    }

    public void moveAndCaptureIfEnemyExists(Position startPoint, Position endPoint) {
        Unit unit = findUnitByPosition(startPoint);

        if (isExistUnit(endPoint)) {
            units.remove(endPoint);
        }
        units.remove(startPoint);
        units.put(endPoint, unit);
    }

    public Team findTeamByPosition(Position endPosition) {
        Unit unit = findUnitByPosition(endPosition);
        return unit.getTeam();
    }

    public boolean isNoneSameTeamUnit(Team turn) {
        return units.values().stream().noneMatch(unit -> unit.getTeam() != turn);
    }

    public boolean isEmptyPoint(Position pick) {
        return units.keySet().stream().noneMatch(position -> position.isSamePoint(pick));
    }

    public boolean isExistBombInRoute(List<Position> route) {
        return route.stream().anyMatch(this::isBombUnit);
    }

    public HashMap<Position, Unit> getUnits() {
        return new HashMap<>(units);
    }
}
