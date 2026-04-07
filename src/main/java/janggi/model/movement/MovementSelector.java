package janggi.model.movement;

import janggi.model.movement.palace.PalaceMovement;
import janggi.model.position.absolute.Position;

public class MovementSelector {

    private final Movement defaultMovement;
    private final PalaceMovement palaceMovement;

    public MovementSelector(Movement defaultMovement, PalaceMovement palaceMovement) {
        this.defaultMovement = defaultMovement;
        this.palaceMovement = palaceMovement;
    }

    public Movement select(Position from, Position to) {
        if (palaceMovement.supports(from, to)) {
            return palaceMovement;
        }

        return defaultMovement;
    }
}
