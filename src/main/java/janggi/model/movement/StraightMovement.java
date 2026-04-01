package janggi.model.movement;

import janggi.model.position.Position;
import janggi.model.position.PositionPath;

public class StraightMovement extends MultiStepMovement {

    @Override
    public PositionPath move(Position from, Position to) {
        if (from.isSameColumn(to) == from.isSameRow(to)) {
            throw new IllegalArgumentException("직선 관계에 위치해 있지 않습니다.");
        }

         if (from.isSameRow(to)) {
            return moveHorizontally(from, to);
         }

        return moveVertically(from, to);
    }
}
