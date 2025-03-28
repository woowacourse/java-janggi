package janggi.domain.piece;

import janggi.domain.Team;
import janggi.domain.piece.movement.Movement;

import java.util.List;

public class Soldier extends PathMovingPiece {

    public Soldier(final Position position, final Team team) {
        super(PieceType.SOLDIER, position, team);
    }

    @Override
    public Piece from(Position position) {
        return new Soldier(position, team);
    }

    @Override
    protected List<Movement> findMovements(Position positionToMove) {
        validateSoldierMovement(positionToMove);
        if (getPosition().isInSameDiagonalInPalace(positionToMove)) {
            return List.of(
                    Movement.getDiagonal(
                            positionToMove.x() - getPosition().x(),
                            positionToMove.y() - getPosition().y()
                    )
            );
        }
        return List.of(Movement.getOrthogonal(
                positionToMove.x() - getPosition().x(),
                positionToMove.y() - getPosition().y()
        ));
    }

    private void validateSoldierMovement(Position positionToMove) {
        if(getTeam() == Team.BLUE &&
                positionToMove.x() - getPosition().x() > 0) {
                throw new IllegalArgumentException("불가능한 이동입니다");
        }
        if(getTeam() == Team.RED &&
                positionToMove.x() - getPosition().x() < 0) {
                throw new IllegalArgumentException("불가능한 이동입니다");
        }
    }
}
