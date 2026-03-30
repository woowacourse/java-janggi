package janggi.model.board.movement;

import janggi.model.board.moveResult.MoveResult;
import janggi.model.board.position.Position;
import janggi.model.board.moveResult.PositionPath;
import java.util.List;

public class OneStepMovement implements Movement{

    private static final int ONE_STEP = 1;

    @Override
    public MoveResult move(Position from, Position to) {
        if (from.getDistanceTo(to) > ONE_STEP
                || from.equals(to)) {
            throw new IllegalArgumentException("두 지점이 한 칸 떨어져 있지 않습니다.");
        }

        return new MoveResult(
                new PositionPath(List.of()),
                from,
                to
        );
    }
}
