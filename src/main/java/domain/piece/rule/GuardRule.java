package domain.piece.rule;

import domain.move.PalaceOneStepMovement;
import java.util.List;

public class GuardRule extends MovementPieceRule {

    public GuardRule() {
        super(List.of(new PalaceOneStepMovement()));
    }
}
