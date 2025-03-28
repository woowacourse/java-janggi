package domain.piece.move.area;

import domain.position.Position;

public class PalaceOnlyConstraint implements MoveAreaConstraint {

    @Override
    public boolean canMoveArea(Position position) {
        return position.isInPalace();
    }
}
