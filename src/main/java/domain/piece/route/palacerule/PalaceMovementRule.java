package domain.piece.route.palacerule;

import domain.piece.route.Route;
import domain.position.JanggiPosition;

public interface PalaceMovementRule {

    void validateCanMoveInPalace(Route route, JanggiPosition origin, JanggiPosition destination);
}
