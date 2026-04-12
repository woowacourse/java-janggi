package domain.piece;

import domain.coordination.Coordination;
import domain.movement.ForwardPalaceMovement;
import domain.movement.PalaceMovement;

import java.util.List;

public abstract class ForwardMovementPiece extends Piece {

    private final PalaceMovement palaceMovement;

    protected ForwardMovementPiece(Team team) {
        this(team, new ForwardPalaceMovement(team.reverse()));
    }

    protected ForwardMovementPiece(Team team, PalaceMovement palaceMovement) {
        super(team);
        this.palaceMovement = palaceMovement;
    }

    @Override
    public void validateRule(Coordination from, Coordination to) {
        if (palaceMovement.isPalace(from, to)) {
            palaceMovement.validateRule(from, to);
            return;
        }
        validateNormalRule(from, to);
    }

    @Override
    public List<Coordination> resolvePath(Coordination from, Coordination to) {
        return List.of();
    }

    protected abstract void validateNormalRule(Coordination from, Coordination to);
}
