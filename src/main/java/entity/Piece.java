package entity;

import domain.Janggi;
import domain.position.Position;
import domain.unit.Team;
import domain.unit.Unit;
import domain.unit.UnitType;
import domain.unit.Units;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public record Piece(Long pieceId, int positionX, int positionY, UnitType unitType, Team team) {

    public static List<Piece> from(Janggi janggi) {
        List<Piece> pieces = new ArrayList<>();
        Map<Position, Unit> units = janggi.getUnits();
        Set<Position> positions = units.keySet();
        for (Position position : positions) {
            Unit unit = units.get(position);
            pieces.add(
                    new Piece(null,
                            position.getX(),
                            position.getY(),
                            unit.getType(),
                            unit.getTeam())
            );
        }
        return pieces;
    }

    public static Janggi toDomain(Room room, List<Piece> pieces) {
        Map<Position, Unit> units = new HashMap<>();
        for (Piece piece : pieces) {
            Position position = Position.of(piece.positionX(), piece.positionY());
            Unit unit = piece.unitType.createUnit(piece.team);
            units.put(position, unit);
        }
        return Janggi.of(Units.of(units), room.turn());
    }
}
