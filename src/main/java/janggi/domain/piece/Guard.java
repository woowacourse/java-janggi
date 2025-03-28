package janggi.domain.piece;

import janggi.domain.Team;
import janggi.domain.piece.movement.Movement;

import java.util.List;

public class Guard extends PathMovingPiece {

    public Guard(final Position position, final Team team) {
        super(PieceType.GUARD, position, team);
        validateIsPalace(position);
    }

    @Override
    public Piece from(Position position) {
        return new Guard(position, team);
    }

    @Override
    public int getScore() {
        return 3;
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
