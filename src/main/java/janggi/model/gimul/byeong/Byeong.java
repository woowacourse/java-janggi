package janggi.model.gimul.byeong;

import janggi.model.Score;
import janggi.model.Team;
import janggi.model.gimul.AbstractGimul;
import janggi.model.gimul.GimulType;
import janggi.model.position.DiagonalDelta;
import janggi.model.position.Position;
import janggi.model.position.PositionPath;
import java.util.List;
import java.util.Optional;

public class Byeong extends AbstractGimul {
    private static final int SCORE_VALUE = 2;
    private static final int MAX_DISTANCE = 2;
    private static final int CHO_BACKWARD = 1;
    private static final int HAN_BACKWARD = -1;
    private static final int HORIZONTAL = 0;

    public Byeong(Team team) {
        super(team, GimulType.BYEONG);
    }

    @Override
    public PositionPath getLegalPath(Position from, Position to) {
        int rowDistance = to.getRowDistance(from);
        int columnDistance = to.getColumnDistance(from);

        if (isPalaceMove(from, to, rowDistance, columnDistance)) {
            return from.moveDiagonal(new DiagonalDelta(rowDistance, columnDistance)).getMiddlePath();
        }

        validateDistance(rowDistance, columnDistance);
        return calculatePath(from, rowDistance, columnDistance);
    }

    private boolean isPalaceMove(Position from, Position to, int rowDistance, int columnDistance) {
        return from.isOnPalaceDiagonal()
                && to.isOnPalaceDiagonal()
                && Math.abs(rowDistance) == Math.abs(columnDistance)
                && !isBackward(rowDistance);
    }

    private boolean isBackward(int rowDistance) {
        return (Team.CHO.equals(team) && rowDistance == CHO_BACKWARD)
                || (Team.HAN.equals(team) && rowDistance == HAN_BACKWARD);
    }

    private void validateDistance(int rowDistance, int columnDistance) {
        if ((Math.abs(rowDistance) + Math.abs(columnDistance)) >= MAX_DISTANCE) {
            throw new IllegalArgumentException("이동할 수 없는 위치입니다.");
        }
        if ((Team.CHO.equals(team) && rowDistance == CHO_BACKWARD) ||
                (Team.HAN.equals(team) && rowDistance == HAN_BACKWARD)) {
            throw new IllegalArgumentException("이동할 수 없는 위치입니다.");
        }
    }

    private PositionPath calculatePath(Position from, int rowDistance, int columnDistance) {
        if (rowDistance == HORIZONTAL) {
            return from.moveHorizontal(columnDistance).getMiddlePath();
        }
        return from.moveVertical(rowDistance).getMiddlePath();
    }

    @Override
    public boolean canPassThrough(List<AbstractGimul> gimulsOnPath, Optional<AbstractGimul> gimulAtTo) {
        return gimulsOnPath.isEmpty()
                && gimulAtTo.map(gimul -> !this.isSameTeam(gimul)).orElse(true);
    }

    @Override
    public String getSymbol() {
        return "병";
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
