package domain.piece.route.palacerule;

import domain.MovingPattern;
import domain.position.JanggiPosition;
import java.util.List;

public class PalaceForwardMovementRule implements PalaceMovementRule {

    @Override
    public void validateCanMove(List<MovingPattern> route, JanggiPosition origin, JanggiPosition destination) {
        MovingPattern direction = route.getFirst();
        if (direction.isDiagonalPattern()) {
            checkDiagonalMovable(origin, destination);
        }
    }

    private void checkDiagonalMovable(JanggiPosition beforePosition, JanggiPosition afterPosition) {
        if (!(beforePosition.isDiagonalMovablePalace() && afterPosition.isDiagonalMovablePalace())) {
            throw new IllegalStateException("해당 위치에서는 대각선으로 이동할 수 없습니다.");
        }
    }
}
