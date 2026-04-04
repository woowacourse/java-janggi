package domain.piece;

import domain.movement.ForwardPalaceMovement;
import domain.movement.PalaceMovement;

public abstract class ForwardMovementPiece extends Piece {

    protected final PalaceMovement palaceMovement;

    protected ForwardMovementPiece(Team team) {
        super(team);
        this.palaceMovement = new ForwardPalaceMovement(team.reverse());
    }
}
