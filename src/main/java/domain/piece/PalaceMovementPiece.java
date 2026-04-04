package domain.piece;

import domain.movement.PalaceMovement;
import domain.movement.TeamPalaceMovement;

public abstract class PalaceMovementPiece extends Piece {

    protected final PalaceMovement palaceMovement;

    protected PalaceMovementPiece(Team team) {
        super(team);
        this.palaceMovement = new TeamPalaceMovement(team.reverse());
    }
}
