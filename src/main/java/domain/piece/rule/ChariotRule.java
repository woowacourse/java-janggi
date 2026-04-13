package domain.piece.rule;

import domain.move.PalaceDiagonalMovement;
import domain.move.StraightMovement;
import java.util.List;

public class ChariotRule extends MovementPieceRule {

    public ChariotRule() {
        super(List.of(
                new StraightMovement(),
                new PalaceDiagonalMovement()
        ));
    }
}
