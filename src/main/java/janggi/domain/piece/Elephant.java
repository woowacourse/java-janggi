package janggi.domain.piece;

import janggi.domain.piece.movement.Movement;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Elephant extends PathMovingPiece {
    private static final List<Position> INITIAL_POSITIONS_BLUE_LEFT = List.of(
            new Position(10, 3),
            new Position(10, 2)
    );
    private static final List<Position> INITIAL_POSITIONS_BLUE_RIGHT = List.of(
            new Position(10, 8),
            new Position(10, 7)
    );
    private static final List<Position> INITIAL_POSITIONS_RED_LEFT = List.of(
            new Position(1, 3),
            new Position(1, 2)
    );
    private static final List<Position> INITIAL_POSITIONS_RED_RIGHT = List.of(
            new Position(1, 8),
            new Position(1, 7)
    );

    private static final List<List<Movement>> movements = List.of(
            List.of(Movement.UP, Movement.TOP_LEFT, Movement.TOP_LEFT),
            List.of(Movement.UP, Movement.TOP_RIGHT, Movement.TOP_RIGHT),
            List.of(Movement.LEFT, Movement.TOP_LEFT, Movement.TOP_LEFT),
            List.of(Movement.LEFT, Movement.BOTTOM_LEFT, Movement.BOTTOM_LEFT),
            List.of(Movement.DOWN, Movement.BOTTOM_LEFT, Movement.BOTTOM_LEFT),
            List.of(Movement.DOWN, Movement.BOTTOM_RIGHT, Movement.BOTTOM_RIGHT),
            List.of(Movement.RIGHT, Movement.TOP_RIGHT, Movement.TOP_RIGHT),
            List.of(Movement.RIGHT, Movement.BOTTOM_RIGHT, Movement.BOTTOM_RIGHT)
    );

    public Elephant(final Position position, final Team team) {
        super("상", position, team);
    }

    public static List<Piece> createWithInitialPositions(
            Team team,
            HorseSide leftHorsePosition,
            HorseSide rightHorsePosition) {
        if (team.equals(Team.BLUE)) {
            return createBlueInitialPositions(leftHorsePosition, rightHorsePosition);
        }
        return createRedInitialPositions(leftHorsePosition, rightHorsePosition);
    }

    private static List<Piece> createBlueInitialPositions(
            HorseSide leftHorsePosition,
            HorseSide rightHorsePosition
    ) {
        List<Piece> elephants = new ArrayList<>();
        elephants.add(new Elephant(INITIAL_POSITIONS_BLUE_LEFT.get(leftHorsePosition.value()), Team.BLUE));
        elephants.add(new Elephant(INITIAL_POSITIONS_BLUE_RIGHT.get(rightHorsePosition.value()), Team.BLUE));
        return elephants;
    }

    private static List<Piece> createRedInitialPositions(
            HorseSide leftHorsePosition,
            HorseSide rightHorsePosition
    ) {
        List<Piece> elephants = new ArrayList<>();
        elephants.add(new Elephant(INITIAL_POSITIONS_RED_LEFT.get(leftHorsePosition.value()), Team.RED));
        elephants.add(new Elephant(INITIAL_POSITIONS_RED_RIGHT.get(rightHorsePosition.value()), Team.RED));
        return elephants;
    }

    @Override
    protected boolean checkPieceCondition(Piece pieceInPositionToMove, Position checkingPosition) {
        return pieceInPositionToMove.isNone();
    }

    @Override
    protected List<Movement> findMovements(Position positionToMove) {
        for(List<Movement> checkingMovements : movements) {
            if(canReachPositionToMove(checkingMovements, positionToMove)) {
                return checkingMovements;
            }
        }
        throw new IllegalArgumentException("불가능한 이동입니다");
    }

    private boolean canReachPositionToMove(List<Movement> checkingMovements, Position positionToMove) {
        Position currentPosition = getPosition();
        for(Movement movement : checkingMovements) {
            currentPosition = currentPosition.plus(movement.getX(), movement.getY());
            System.out.println(currentPosition);
            if(currentPosition.equals(positionToMove)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Piece from(Position position) {
        return new Elephant(position, team);
    }
}
