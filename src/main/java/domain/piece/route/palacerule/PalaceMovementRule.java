package domain.piece.route.palacerule;

import domain.MovingPattern;
import domain.position.JanggiPosition;
import java.util.List;

public interface PalaceMovementRule {

    void validateCanMoveInPalace(List<MovingPattern> route, JanggiPosition origin, JanggiPosition destination);
}
