package domain.piece.route.palacerule;

import domain.MovingPattern;
import domain.position.JanggiPosition;
import java.util.List;

public class PalaceNoOperationRule implements PalaceMovementRule {

    @Override
    public void validateCanMove(List<MovingPattern> route, JanggiPosition origin, JanggiPosition destination) {
        return;
    }
}
