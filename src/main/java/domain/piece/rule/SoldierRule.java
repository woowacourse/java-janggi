package domain.piece.rule;

import domain.move.EnemyPalaceForwardDiagonalMovement;
import domain.move.SoldierBasicMovement;
import java.util.List;

public class SoldierRule extends MovementPieceRule {

    public SoldierRule() {
        super(List.of(
                new SoldierBasicMovement(),
                new EnemyPalaceForwardDiagonalMovement()
        ));
    }
}
