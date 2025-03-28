package domain.piece.route.palacerule;

import domain.piece.route.Route;
import domain.position.JanggiPosition;

public class PalaceNoOperationRule implements PalaceMovementRule {

    @Override
    public void validateCanMoveInPalace(Route route, JanggiPosition origin, JanggiPosition destination) {
        return;
    }
}
