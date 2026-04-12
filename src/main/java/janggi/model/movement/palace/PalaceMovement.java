package janggi.model.movement.palace;

import janggi.model.movement.Movement;
import janggi.model.palace.Palaces;
import janggi.model.position.absolute.Position;
import janggi.model.position.absolute.PositionPath;
import java.util.List;

public abstract class PalaceMovement implements Movement {

    protected final Palaces palaces;

    public PalaceMovement(Palaces palaces) {
        this.palaces = palaces;
    }

    public boolean supports(Position from, Position to) {
        return palaces.areInSamePalace(from, to);
    }

    protected PositionPath moveOneStep(Position from, Position to) {
        if (!palaces.isAdjacentInSamePalace(from, to)) {
            throw new IllegalArgumentException("해당 경로로 이동할 수 없습니다.");
        }

        return new PositionPath(List.of());
    }
}
