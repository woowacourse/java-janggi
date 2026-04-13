package domain.piece.rule;

import domain.move.ElephantMovement;
import java.util.List;

public class ElephantRule extends MovementPieceRule {

    public ElephantRule() {
        super(List.of(new ElephantMovement()));
    }
}
