package janggi.domain.piece;

import janggi.domain.piece.movement.Movement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Soldier extends PathMovingPiece {


    public Soldier(final Position position, final Team team) {
        super("졸", position, team);
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
