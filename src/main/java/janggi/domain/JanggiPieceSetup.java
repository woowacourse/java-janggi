package janggi.domain;

import janggi.domain.piece.*;

import java.util.ArrayList;
import java.util.List;

public class JanggiPieceSetup {

    public static final List<Piece> INITIAL_CANNON = List.of(
            new Cannon(new Position(8, 2), Team.BLUE),
            new Cannon(new Position(8, 8), Team.BLUE),
            new Cannon(new Position(3, 2), Team.RED),
            new Cannon(new Position(3, 8), Team.RED)
    );

    public static final List<Piece> INITIAL_CHARIOT = List.of(
            new Chariot(new Position(10, 1), Team.BLUE),
            new Chariot(new Position(10, 9), Team.BLUE),
            new Chariot(new Position(1, 1), Team.RED),
            new Chariot(new Position(1, 9), Team.RED)
    );

    public static final List<Piece> INITIAL_GENERAL = List.of(
            new General(new Position(9, 5), Team.BLUE),
            new General(new Position(2, 5), Team.RED)
    );

    public static final List<Piece> INITIAL_GUARD = List.of(
            new Guard(new Position(10, 4), Team.BLUE),
            new Guard(new Position(10, 6), Team.BLUE),
            new Guard(new Position(1, 4), Team.RED),
            new Guard(new Position(1, 6), Team.RED)
    );

    public static final List<Piece> INITIAL_SOLDIER = List.of(
            new Soldier(new Position(7, 1), Team.BLUE),
            new Soldier(new Position(7, 3), Team.BLUE),
            new Soldier(new Position(7, 5), Team.BLUE),
            new Soldier(new Position(7, 7), Team.BLUE),
            new Soldier(new Position(7, 9), Team.BLUE),
            new Soldier(new Position(4, 1), Team.RED),
            new Soldier(new Position(4, 3), Team.RED),
            new Soldier(new Position(4, 5), Team.RED),
            new Soldier(new Position(4, 7), Team.RED),
            new Soldier(new Position(4, 9), Team.RED)
    );

    private static final List<Position> INITIAL_POSITIONS_BLUE_LEFT_ELEPHANT = List.of(
            new Position(10, 3),
            new Position(10, 2)
    );
    private static final List<Position> INITIAL_POSITIONS_BLUE_RIGHT_ELEPHANT = List.of(
            new Position(10, 8),
            new Position(10, 7)
    );
    private static final List<Position> INITIAL_POSITIONS_RED_LEFT_ELEPHANT = List.of(
            new Position(1, 3),
            new Position(1, 2)
    );
    private static final List<Position> INITIAL_POSITIONS_RED_RIGHT_ELEPHANT = List.of(
            new Position(1, 8),
            new Position(1, 7)
    );

    private static final List<Position> INITIAL_POSITIONS_BLUE_LEFT_HORSE = List.of(
            new Position(10, 2),
            new Position(10, 3)
    );
    private static final List<Position> INITIAL_POSITIONS_BLUE_RIGHT_HORSE = List.of(
            new Position(10, 7),
            new Position(10, 8)
    );
    private static final List<Position> INITIAL_POSITIONS_RED_LEFT_HORSE = List.of(
            new Position(1, 2),
            new Position(1, 3)
    );
    private static final List<Position> INITIAL_POSITIONS_RED_RIGHT_HORSE = List.of(
            new Position(1, 7),
            new Position(1, 8)
    );

    public static List<Piece> createElephantWithInitialPositions(
            Team team,
            HorseSide leftHorsePosition,
            HorseSide rightHorsePosition) {
        if (team.equals(Team.BLUE)) {
            return createElephantBlueInitialPositions(leftHorsePosition, rightHorsePosition);
        }
        return createElephantRedInitialPositions(leftHorsePosition, rightHorsePosition);
    }

    public static List<Piece> createHorseWithInitialPositions(
            final Team team,
            final HorseSide leftHorsePosition,
            final HorseSide rightHorsePosition) {
        if (team.equals(Team.BLUE)) {
            return createHorseBlueInitialPositions(leftHorsePosition, rightHorsePosition);
        }
        return createHorseRedInitialPositions(leftHorsePosition, rightHorsePosition);
    }

    private static List<Piece> createElephantBlueInitialPositions(
            HorseSide leftHorsePosition,
            HorseSide rightHorsePosition
    ) {
        List<Piece> elephants = new ArrayList<>();
        elephants.add(new Elephant(INITIAL_POSITIONS_BLUE_LEFT_ELEPHANT.get(leftHorsePosition.value()), Team.BLUE));
        elephants.add(new Elephant(INITIAL_POSITIONS_BLUE_RIGHT_ELEPHANT.get(rightHorsePosition.value()), Team.BLUE));
        return elephants;
    }

    private static List<Piece> createElephantRedInitialPositions(
            HorseSide leftHorsePosition,
            HorseSide rightHorsePosition
    ) {
        List<Piece> elephants = new ArrayList<>();
        elephants.add(new Elephant(INITIAL_POSITIONS_RED_LEFT_ELEPHANT.get(leftHorsePosition.value()), Team.RED));
        elephants.add(new Elephant(INITIAL_POSITIONS_RED_RIGHT_ELEPHANT.get(rightHorsePosition.value()), Team.RED));
        return elephants;
    }

    private static List<Piece> createHorseBlueInitialPositions(
            final HorseSide leftHorsePosition,
            final HorseSide rightHorsePosition
    ) {
        List<Piece> horses = new ArrayList<>();
        horses.add(new Horse(INITIAL_POSITIONS_BLUE_LEFT_HORSE.get(leftHorsePosition.value()), Team.BLUE));
        horses.add(new Horse(INITIAL_POSITIONS_BLUE_RIGHT_HORSE.get(rightHorsePosition.value()), Team.BLUE));
        return horses;
    }

    private static List<Piece> createHorseRedInitialPositions(
            final HorseSide leftHorsePosition,
            final HorseSide rightHorsePosition
    ) {
        List<Piece> horses = new ArrayList<>();
        horses.add(new Horse(INITIAL_POSITIONS_RED_LEFT_HORSE.get(leftHorsePosition.value()), Team.RED));
        horses.add(new Horse(INITIAL_POSITIONS_RED_RIGHT_HORSE.get(rightHorsePosition.value()), Team.RED));
        return horses;
    }
}
