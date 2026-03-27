package janggi.model.gimul.diagonalMove;

import janggi.model.Team;
import janggi.model.position.DiagonalDelta;
import janggi.model.position.Position;
import janggi.model.position.PositionDelta;
import janggi.model.position.PositionPath;

public class Ma extends AbstractDiagonalGimul {

    private static final int FIRST_MOVE = 1;
    private static final int SECOND_MOVE = 2;


    public Ma(Team team) {
        super(team);
    }

    @Override
    public PositionPath getLegalPath(Position from, Position to) {
        PositionDelta positionDelta = PositionDelta.between(from, to);

        if (positionDelta.notMatchStepPattern(FIRST_MOVE, SECOND_MOVE)) {
            throw new IllegalArgumentException("이동할 수 없는 위치입니다.");
        }

        int firstDistance = positionDelta.getStepSign();
        PositionPath first = from.moveVertical(firstDistance);
        PositionDelta moved = positionDelta.movedVertically(firstDistance);

        if (positionDelta.isHorizontalDominant()) {
            first = from.moveHorizontal(firstDistance);
            moved = positionDelta.movedHorizontally(firstDistance);
        }

        PositionPath second = first.getDestination().moveDiagonal(
                new DiagonalDelta(moved.rowDistance(), moved.columnDistance())
        );

        return PositionPath.concatenate(first, second).removeFromAndTo();
    }

    @Override
    public String getSymbol() {
        return "마";
    }
}
