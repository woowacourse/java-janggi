package domain.piece.route.palacerule;

import domain.piece.route.Route;
import domain.position.JanggiPosition;
import janggiexception.InvalidPathException;

public class PalaceForwardMovementRule implements PalaceMovementRule {

    @Override
    public void validateCanMoveInPalace(Route route, JanggiPosition origin, JanggiPosition destination) {
        if (route.isDiagonalDirection()) {
            checkDiagonalMovable(origin, destination);
        }
    }

    private void checkDiagonalMovable(JanggiPosition beforePosition, JanggiPosition afterPosition) {
        if (!(beforePosition.isDiagonalMovablePalace() && afterPosition.isDiagonalMovablePalace())) {
            throw new InvalidPathException();
        }
    }
}
