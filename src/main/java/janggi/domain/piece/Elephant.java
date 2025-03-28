package janggi.domain.piece;

import janggi.domain.Team;
import janggi.domain.piece.movement.Movement;

import java.util.List;

public class Elephant extends PathMovingPiece {
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
        super(PieceType.ELEPHANT, position, team);
    }

    @Override
    public Piece from(Position position) {
        return new Elephant(position, team);
    }

    @Override
    protected List<Movement> findMovements(Position positionToMove) {
        return movements.stream()
                .filter(checkingMovements -> canReachPositionToMove(checkingMovements, positionToMove))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("불가능한 이동입니다"));
    }

    private boolean canReachPositionToMove(List<Movement> checkingMovements, Position positionToMove) {
        Position currentPosition = getPosition();
        for(Movement movement : checkingMovements) {
            currentPosition = currentPosition.plus(movement.getX(), movement.getY());
            if(currentPosition.equals(positionToMove)) {
                return true;
            }
        }
        return false;
    }
}
