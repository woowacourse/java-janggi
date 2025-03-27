package domain.piece.route.palacerule;

import domain.MovingPattern;
import domain.position.JanggiPosition;
import janggiexception.InvalidPathException;
import java.util.List;

public class PalaceForwardMovementRule implements PalaceMovementRule {

    @Override
    public void validateCanMoveInPalace(List<MovingPattern> route, JanggiPosition origin, JanggiPosition destination) {
        MovingPattern direction = route.getFirst();
        if (direction.isDiagonalPattern()) {
            checkDiagonalMovable(origin, destination);
        }
    }

    private void checkDiagonalMovable(JanggiPosition beforePosition, JanggiPosition afterPosition) {
        if (!(beforePosition.isDiagonalMovablePalace() && afterPosition.isDiagonalMovablePalace())) {
            throw new InvalidPathException();
        }
    }
}
