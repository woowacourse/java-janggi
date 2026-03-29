package janggi.model.gimul.diagonalMove;

import janggi.model.Team;
import janggi.model.position.DiagonalDelta;
import janggi.model.position.MoveResult;
import janggi.model.position.Position;
import janggi.model.position.PositionDelta;

public class Sang extends AbstractDiagonalGimul {

    private static final int ROW_DISTANCE = 2;
    private static final int COLUMN_DISTANCE = 3;

    public Sang(Team team) {
        super(team);
    }

    @Override
    public MoveResult getLegalPath(Position from, Position to) {
        PositionDelta positionDelta = PositionDelta.between(from, to);

        if (positionDelta.notMatchStepPattern(ROW_DISTANCE, COLUMN_DISTANCE)) {
            throw new IllegalArgumentException("이동할 수 없는 위치입니다.");
        }

        int firstDistance = positionDelta.getStepSign();
        MoveResult first = from.moveVertical(firstDistance);
        PositionDelta moved = positionDelta.movedVertically(firstDistance);

        if (positionDelta.isHorizontalDominant()) {
            first = from.moveHorizontal(firstDistance);
            moved = positionDelta.movedHorizontally(firstDistance);
        }

        MoveResult second = first.getTo().moveDiagonal(
                new DiagonalDelta(moved.rowDistance(), moved.columnDistance())
        );

        return first.cancatenate(second);
    }
}
