package janggi.model.movement.palace;

import janggi.model.palace.Palaces;
import janggi.model.position.absolute.Position;
import janggi.model.position.absolute.PositionPath;

public class PalaceAdjacentMovement extends PalaceMovement {

    public PalaceAdjacentMovement(Palaces palaces) {
        super(palaces);
    }

    @Override
    public PositionPath move(Position from, Position to) {
        if (!supports(from, to)) {
            throw new IllegalArgumentException("from과 to는 같은 궁성 안에 있어야 합니다.");
        }

        return moveOneStep(from, to);
    }
}
