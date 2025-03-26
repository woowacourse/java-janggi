package janggi.domain.piece;

import janggi.domain.piece.movement.Movement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Soldier extends PathMovingPiece {
    private static final List<Position> INITIAL_POSITIONS_BLUE = List.of(
            new Position(7, 1),
            new Position(7, 3),
            new Position(7, 5),
            new Position(7, 7),
            new Position(7, 9)
    );
    private static final List<Position> INITIAL_POSITIONS_RED = List.of(
            new Position(4, 1),
            new Position(4, 3),
            new Position(4, 5),
            new Position(4, 7),
            new Position(4, 9)
    );

    public Soldier(final Position position, final Team team) {
        super("졸", position, team);
    }

    public static List<Piece> createWithInitialPositions(final Team team) {
        List<Piece> soldiers = new ArrayList<>();
        if (team.equals(Team.BLUE)) {
            INITIAL_POSITIONS_BLUE.forEach(position ->
                    soldiers.add(new Soldier(position, team)));
            return soldiers;
        }
        INITIAL_POSITIONS_RED.forEach(position ->
                soldiers.add(new Soldier(position, team)));
        return soldiers;
    }

    @Override
    protected List<Movement> findMovements(Position positionToMove) {
        return List.of(Movement.getDiagonal(
                positionToMove.x() - getPosition().x(),
                positionToMove.y() - getPosition().y()
        ));
    }

    @Override
    protected boolean checkPieceCondition(Piece pieceInPositionToMove, Position checkingPosition) {
        System.out.println(checkingPosition + " " + getPosition());
        Movement movement = Movement.getDiagonal(
                checkingPosition.x() - getPosition().x(),
                checkingPosition.y() - getPosition().y()
        );
        if(team == Team.BLUE) {
            if(movement.getX() > 0) {
                return false;
            }
        }
        if(team == Team.RED) {
            if(movement.getX() < 0) {
                return false;
            }
        }
        return pieceInPositionToMove.isNone();
    }

    @Override
    public Piece from(Position position) {
        return new Soldier(position, team);
    }
}
