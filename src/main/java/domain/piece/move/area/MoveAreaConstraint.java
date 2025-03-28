package domain.piece.move.area;

import domain.position.Position;

public interface MoveAreaConstraint {

    boolean canMoveArea(Position position);
}
