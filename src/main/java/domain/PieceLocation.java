package domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum PieceLocation {
    GENERAL(initGeneralPosition()),
    GUARD(initGuardPosition()),
    HORSE(initHorsePosition()),
    CANNON(initCannonPosition()),
    ELEPHANT(initElephantPosition()),
    SOLDIER(initSoldierPosition()),
    CHARIOT(initChariotPosition());

    private final Map<Camp, List<Position>> positions;

    PieceLocation(Map<Camp, List<Position>> positions) {
        this.positions = positions;
    }

    private static Map<Camp, List<Position>> initGeneralPosition() {
        Map<Camp, List<Position>> generalPosition = new HashMap<>();
        generalPosition.put(Camp.HAN, List.of(
                new Position(5, 2)
        ));

        generalPosition.put(Camp.CHO, List.of(
                new Position(5, 9)
        ));

        return generalPosition;
    }

    private static Map<Camp, List<Position>> initGuardPosition() {
        Map<Camp, List<Position>> guardPosition = new HashMap<>();
        guardPosition.put(Camp.HAN, List.of(
                new Position(4, 1),
                new Position(6, 1)
        ));

        guardPosition.put(Camp.CHO, List.of(
                new Position(4, 0),
                new Position(6, 0)
        ));

        return guardPosition;
    }

    private static Map<Camp, List<Position>> initHorsePosition() {
        Map<Camp, List<Position>> horsePosition = new HashMap<>();

        horsePosition.put(Camp.HAN, List.of(
                new Position(3, 1),
                new Position(8, 1)
        ));

        horsePosition.put(Camp.CHO, List.of(
                new Position(2, 0),
                new Position(8, 0)
        ));

        return horsePosition;
    }

    private static Map<Camp, List<Position>> initCannonPosition() {
        Map<Camp, List<Position>> cannonPosition = new HashMap<>();

        cannonPosition.put(Camp.HAN, List.of(
                new Position(2, 3),
                new Position(8, 3)
        ));
        cannonPosition.put(Camp.CHO, List.of(
                new Position(2, 8),
                new Position(8, 8)
        ));

        return cannonPosition;
    }

    private static Map<Camp, List<Position>> initElephantPosition() {
        Map<Camp, List<Position>> elephantPosition = new HashMap<>();

        elephantPosition.put(Camp.HAN, List.of(
                new Position(2, 1),
                new Position(7, 1)
        ));
        elephantPosition.put(Camp.CHO, List.of(
                new Position(3, 0),
                new Position(7, 0)
        ));

        return elephantPosition;
    }

    private static Map<Camp, List<Position>> initSoldierPosition() {
        Map<Camp, List<Position>> soldierPosition = new HashMap<>();

        soldierPosition.put(Camp.HAN, List.of(
                new Position(1, 4),
                new Position(3, 4),
                new Position(5, 4),
                new Position(7, 4),
                new Position(9, 4)
        ));
        soldierPosition.put(Camp.CHO, List.of(
                new Position(1, 7),
                new Position(3, 7),
                new Position(5, 7),
                new Position(7, 7),
                new Position(9, 7)
        ));

        return soldierPosition;
    }

    private static Map<Camp, List<Position>> initChariotPosition() {
        Map<Camp, List<Position>> chariotPosition = new HashMap<>();

        chariotPosition.put(Camp.HAN, List.of(
                new Position(1, 1),
                new Position(9, 1))
        );
        chariotPosition.put(Camp.CHO, List.of(
                new Position(1, 0),
                new Position(9, 0))
        );

        return chariotPosition;
    }

    public List<Position> getPositions(Camp camp) {
        return positions.get(camp);
    }

    public List<Position> getPositions(Camp camp, ElephantFormation formation) {
        return formation.getPositions(this, camp);
    }
}
