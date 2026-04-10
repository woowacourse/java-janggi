package domain;

import domain.position.Position;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum InitialPieceLocation {
    GENERAL(initGeneralPosition()),
    GUARD(initGuardPosition()),
    HORSE(initHorsePosition()),
    CANNON(initCannonPosition()),
    ELEPHANT(initElephantPosition()),
    SOLDIER(initSoldierPosition()),
    CHARIOT(initChariotPosition());

    private final Map<Camp, List<Position>> positions;

    InitialPieceLocation(Map<Camp, List<Position>> positions) {
        this.positions = positions;
    }

    private static Map<Camp, List<Position>> initGeneralPosition() {
        Map<Camp, List<Position>> generalPosition = new HashMap<>();
        generalPosition.put(Camp.HAN, List.of(
                new Position(4, 1)
        ));

        generalPosition.put(Camp.CHO, List.of(
                new Position(4, 8)
        ));

        return generalPosition;
    }

    private static Map<Camp, List<Position>> initGuardPosition() {
        Map<Camp, List<Position>> guardPosition = new HashMap<>();
        guardPosition.put(Camp.HAN, List.of(
                new Position(3, 0),
                new Position(5, 0)
        ));

        guardPosition.put(Camp.CHO, List.of(
                new Position(3, 9),
                new Position(5, 9)
        ));

        return guardPosition;
    }

    private static Map<Camp, List<Position>> initHorsePosition() {
        Map<Camp, List<Position>> horsePosition = new HashMap<>();

        horsePosition.put(Camp.HAN, List.of(
                new Position(2, 0),
                new Position(7, 0)
        ));

        horsePosition.put(Camp.CHO, List.of(
                new Position(1, 9),
                new Position(7, 9)
        ));

        return horsePosition;
    }

    private static Map<Camp, List<Position>> initCannonPosition() {
        Map<Camp, List<Position>> cannonPosition = new HashMap<>();

        cannonPosition.put(Camp.HAN, List.of(
                new Position(1, 2),
                new Position(7, 2)
        ));
        cannonPosition.put(Camp.CHO, List.of(
                new Position(1, 7),
                new Position(7, 7)
        ));

        return cannonPosition;
    }

    private static Map<Camp, List<Position>> initElephantPosition() {
        Map<Camp, List<Position>> elephantPosition = new HashMap<>();

        elephantPosition.put(Camp.HAN, List.of(
                new Position(1, 0),
                new Position(6, 0)
        ));
        elephantPosition.put(Camp.CHO, List.of(
                new Position(2, 9),
                new Position(6, 9)
        ));

        return elephantPosition;
    }

    private static Map<Camp, List<Position>> initSoldierPosition() {
        Map<Camp, List<Position>> soldierPosition = new HashMap<>();

        soldierPosition.put(Camp.HAN, List.of(
                new Position(0, 3),
                new Position(2, 3),
                new Position(4, 3),
                new Position(6, 3),
                new Position(8, 3)
        ));
        soldierPosition.put(Camp.CHO, List.of(
                new Position(0, 6),
                new Position(2, 6),
                new Position(4, 6),
                new Position(6, 6),
                new Position(8, 6)
        ));

        return soldierPosition;
    }

    private static Map<Camp, List<Position>> initChariotPosition() {
        Map<Camp, List<Position>> chariotPosition = new HashMap<>();

        chariotPosition.put(Camp.HAN, List.of(
                new Position(0, 0),
                new Position(8, 0))
        );
        chariotPosition.put(Camp.CHO, List.of(
                new Position(0, 9),
                new Position(8, 9))
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
