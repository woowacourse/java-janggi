package janggi.model.movement.palace;

import janggi.model.movement.Movement;
import janggi.model.position.absolute.Position;
import janggi.model.position.absolute.PositionPath;
import java.util.List;

public abstract class PalaceMovement implements Movement {

    protected PositionPath moveOneStep(Position from, Position to) {
        if (!from.isAdjacentInPalaceWith(to)) {
            throw new IllegalArgumentException("해당 경로로 이동할 수 없습니다.");
        }

        return new PositionPath(List.of());
    }
}
