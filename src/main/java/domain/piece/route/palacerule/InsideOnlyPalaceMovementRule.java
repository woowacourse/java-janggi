package domain.piece.route.palacerule;

import domain.MovingPattern;
import domain.position.JanggiPosition;
import janggiexception.InvalidPathException;
import java.util.List;

public class InsideOnlyPalaceMovementRule implements PalaceMovementRule {

    @Override
    public void validateCanMoveInPalace(List<MovingPattern> route, JanggiPosition origin, JanggiPosition destination) {
        if (!destination.isPalace()) {
            throw new IllegalStateException("해당 기물은 궁성 밖을 벗어날 수 없습니다.");
        }
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
