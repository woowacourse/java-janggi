package domain.piece.route.palacerule;

import domain.piece.route.Route;
import domain.position.JanggiPosition;
import janggiexception.InvalidPathException;

public class InsideOnlyPalaceMovementRule implements PalaceMovementRule {

    @Override
    public void validateCanMoveInPalace(Route route, JanggiPosition origin, JanggiPosition destination) {
        if (!destination.isPalace()) {
            throw new IllegalStateException("해당 기물은 궁성 밖을 벗어날 수 없습니다.");
        }
        if (route.isDiagonalDirection()) {
            validateMoveInPalace(origin, destination);
        }
    }

    private void validateMoveInPalace(JanggiPosition origin, JanggiPosition destination) {
        if (!(origin.isDiagonalMovablePalace() && destination.isDiagonalMovablePalace())) {
            throw new InvalidPathException();
        }
    }
}
