package janggi.model.movement;

import janggi.model.position.Position;
import janggi.model.position.PositionPath;
import java.util.List;

public class OneStepMovement implements Movement{

    private static final int ONE_STEP = 1;

    @Override
    public PositionPath move(Position from, Position to) {
        if (from.getDistanceTo(to) > ONE_STEP
                || from.equals(to)) {
            throw new IllegalArgumentException("두 지점이 한 칸 떨어져 있지 않습니다.");
        }

        return new PositionPath(List.of());
    }
}
