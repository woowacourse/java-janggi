package domain.piece.move.area;

import domain.position.Position;

public class FreeMoveConstraint implements MoveAreaConstraint {
    @Override
    public boolean canMoveArea(Position position) {
        return true;
    }
}
