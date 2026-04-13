package domain.piece.rule;

import domain.move.PalaceOneStepMovement;
import java.util.List;

public class GeneralRule extends MovementPieceRule {

    public GeneralRule() {
        super(List.of(new PalaceOneStepMovement()));
    }
}
