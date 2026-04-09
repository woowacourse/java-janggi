package janggi.model.gimul.diagonalMove;

import janggi.model.Score;
import janggi.model.Team;
import janggi.model.gimul.GimulType;
import janggi.model.position.DiagonalDelta;
import janggi.model.position.Position;
import janggi.model.position.PositionDelta;
import janggi.model.position.PositionPath;

public class Ma extends AbstractDiagonalGimul {
    private static final int SCORE_VALUE = 5;
    private static final int FIRST_MOVE = 1;
    private static final int SECOND_MOVE = 2;

    public Ma(Team team) {
        super(team, GimulType.MA);
    }

    @Override
    public PositionPath getLegalPath(Position from, Position to) {
        PositionDelta positionDelta = PositionDelta.between(from, to);
        if (positionDelta.notMatchStepPattern(FIRST_MOVE, SECOND_MOVE)) {
            throw new IllegalArgumentException("이동할 수 없는 위치입니다.");
        }
        return calculatePath(from, positionDelta);
    }

    private PositionPath calculatePath(Position from, PositionDelta positionDelta) {
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
        return PositionPath.concatenate(first, second).getMiddlePath();
    }

    @Override
    public String getSymbol() {
        return "마";
    }

    @Override
    public Score getScore() {
        return new Score(SCORE_VALUE);
    }

    @Override
    public boolean canBeJumpedOver() {
        return true;
    }

    @Override
    public boolean isKing() {
        return false;
    }
}
