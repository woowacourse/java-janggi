package domain.piece;

import domain.coordination.Coordination;
import domain.movement.FullPalaceMovement;
import domain.movement.PalaceMovement;
import domain.piece.error.PalaceMovementException;

import java.util.List;

public abstract class FullPalaceMovementPiece extends Piece {

    private static final String OUT_OF_PALACE_MOVE_MESSAGE = "궁성 밖으로는 이동할 수 없습니다.";

    protected final PalaceMovement palaceMovement;

    protected FullPalaceMovementPiece(Team team) {
        super(team);
        this.palaceMovement = new FullPalaceMovement(team);
    }

    @Override
    public void validateRule(Coordination from, Coordination to) {
        if (palaceMovement.isPalace(from, to)) {
            palaceMovement.validateRule(from, to);
            return;
        }
        throw new PalaceMovementException(OUT_OF_PALACE_MOVE_MESSAGE);
    }

    @Override
    public List<Coordination> resolvePath(Coordination from, Coordination to) {
        return List.of();
    }
}
