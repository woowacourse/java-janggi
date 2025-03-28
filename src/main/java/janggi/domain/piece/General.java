package janggi.domain.piece;

import janggi.domain.Team;
import janggi.domain.piece.movement.Movement;

import java.util.List;

public class General extends PathMovingPiece {

    public General(final Position position, final Team team) {
        super(PieceType.GENERAL, position, team);
        validateIsPalace(position);
    }

    @Override
    public Piece from(Position position) {
        return new General(position, team);
    }

    @Override
    protected List<Movement> findMovements(Position positionToMove) {
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

    private void validateIsPalace(Position position) {
        if(position.isNotPalace()) {
            throw new IllegalArgumentException("궁성에만 존재 가능합니다");
        }
    }
}
