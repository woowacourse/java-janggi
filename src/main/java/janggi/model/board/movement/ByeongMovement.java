package janggi.model.board.movement;

import janggi.model.board.PositionPath;
import janggi.model.board.position.Position;

public class ByeongMovement extends StraightMovement{

    private static final int MAX_MOVE_DISTANCE = 1;

    @Override
    public PositionPath move(Position from, Position to) {
        if (from.getDistanceTo(to) > MAX_MOVE_DISTANCE) {
            throw new IllegalArgumentException("이동할 수 없는 위치입니다.");
        }

        if (from.isSameColumn(to) == from.isSameRow(to)) {
            throw new IllegalArgumentException("직선 관계에 위치해 있지 않습니다.");
        }

        if (from.isSameRow(to)) {
            return moveHorizontally(from, to);
        }

        return moveVertically(from, to);
    }
}
