package domain.piece.route.palacerule;

import domain.piece.route.Route;
import domain.position.JanggiPosition;
import janggiexception.InvalidPathException;

public class PalaceLinearMovementRule implements PalaceMovementRule {

    @Override
    public void validateCanMoveInPalace(Route route, JanggiPosition origin, JanggiPosition destination) {
        if (route.isDiagonalDirection()) {
            validateMoveInPalace(origin, destination);
        }
    }

    private void validateMoveInPalace(JanggiPosition origin, JanggiPosition destination) {
        if (origin.isDiagonalMovablePalace() && destination.isDiagonalMovablePalace()) {
            return;
        }
        throw new InvalidPathException();
    }
}
