package domain.piece.route.palacerule;

import domain.MovingPattern;
import domain.position.JanggiPosition;
import janggiexception.InvalidPathException;
import java.util.List;

public class PalaceLinearMovementRule implements PalaceMovementRule {

    @Override
    public void validateCanMove(List<MovingPattern> route, JanggiPosition origin, JanggiPosition destination) {
        MovingPattern direction = route.getFirst();
        if (direction.isDiagonalPattern()) {
            validateMoveInPalace(origin, destination, direction);
        }
    }

    private void validateMoveInPalace(JanggiPosition origin, JanggiPosition destination, MovingPattern direction) {
        if (!(origin.isDiagonalMovablePalace() && destination.isDiagonalMovablePalace())) {
            throw new InvalidPathException();
        }
    }
}
