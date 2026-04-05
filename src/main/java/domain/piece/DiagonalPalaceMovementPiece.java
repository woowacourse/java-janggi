package domain.piece;

import domain.coordination.Coordination;
import domain.movement.DiagonalPalaceMovement;
import domain.movement.PalaceMovement;

public abstract class DiagonalPalaceMovementPiece extends Piece {

    protected static final String UNRESOLVABLE_PATH_MESSAGE = "이동 경로를 확인할 수 없습니다.";

    protected final PalaceMovement palaceMovement;

    protected DiagonalPalaceMovementPiece(Team team) {
        super(team);
        this.palaceMovement = new DiagonalPalaceMovement();
    }

    @Override
    public void validateRule(Coordination from, Coordination to) {
        palaceMovement.validateRule(from, to);
    }
}
