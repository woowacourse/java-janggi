package domain.piece.noPathMovement;

import domain.MoveVector;

public class GoongMovement extends InCastleNoPathMovement {

    public GoongMovement() {
        super(MoveVector.CROSS_MOVE_VECTORS);
    }
}
