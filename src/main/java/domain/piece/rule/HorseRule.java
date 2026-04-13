package domain.piece.rule;

import domain.move.HorseMovement;
import java.util.List;

public class HorseRule extends MovementPieceRule {

    public HorseRule() {
        super(List.of(new HorseMovement()));
    }
}
