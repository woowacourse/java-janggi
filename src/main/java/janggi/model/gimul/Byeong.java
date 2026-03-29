package janggi.model.gimul;

import janggi.model.Team;
import janggi.model.position.MoveResult;
import janggi.model.position.Position;
import java.util.List;

public class Byeong extends AbstractGimul {

    private static final int MAX_MOVE_DISTANCE = 1;
    private static final int FORWARD_STEP = 1;
    private static final int BACKWARD_STEP = -1;
    private static final int NO_MOVEMENT = 0;

    public Byeong(Team team) {
        super(team);
    }

    @Override
    public MoveResult getLegalPath(Position from, Position to) {
        int rowDistance = to.getRowDistance(from);
        int columnDistance = to.getColumnDistance(from);

        validateMoveDistance(rowDistance, columnDistance);

        if (isHorizontalMove(rowDistance)) {
            return from.moveHorizontal(columnDistance);
        }

        if (isBackwardMove(rowDistance)) {
            throw new IllegalArgumentException("이동할 수 없는 위치입니다.");
        }

        return from.moveVertical(rowDistance);
    }

    private void validateMoveDistance(int rowDistance, int columnDistance) {
        int absRowDistance = Math.abs(rowDistance);
        int absColumnDistance = Math.abs(columnDistance);

        if ((absRowDistance + absColumnDistance) > MAX_MOVE_DISTANCE) {
            throw new IllegalArgumentException("이동할 수 없는 위치입니다.");
        }
    }

    private boolean isHorizontalMove(int rowDistance) {
        return rowDistance == NO_MOVEMENT;
    }

    private boolean isBackwardMove(int rowDistance) {
        return (Team.CHO.equals(team) && rowDistance == FORWARD_STEP) ||
                (Team.HAN.equals(team) && rowDistance == BACKWARD_STEP);
    }

    @Override
    public boolean canPassThrough(
            List<AbstractGimul> gimulsOnPath,
            AbstractGimul gimulAtTo
    ) {
        return gimulsOnPath.isEmpty() && !this.isSameTeam(gimulAtTo);
    }

    @Override
    public boolean canPassThrough(List<AbstractGimul> gimulsOnPath) {
        return gimulsOnPath.isEmpty();
    }

    @Override
    public String getSymbol() {
        return "병";
    }
}
