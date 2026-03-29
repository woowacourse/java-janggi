package janggi.model.gimul;

import janggi.model.Team;
import janggi.model.position.Position;
import janggi.model.position.PositionPath;
import java.util.List;

public class Byeong extends AbstractGimul {
    private static final int MAX_DISTANCE = 2;
    private static final int CHO_BACKWARD = 1;
    private static final int HAN_BACKWARD = -1;
    private static final int HORIZONTAL = 0;

    public Byeong(Team team) {
        super(team);
    }

    @Override
    public PositionPath getLegalPath(Position from, Position to) {
        int rowDistance = to.getRowDistance(from);
        int columnDistance = to.getColumnDistance(from);
        validateDistance(rowDistance, columnDistance);
        return calculatePath(from, rowDistance, columnDistance);
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
            return from.moveHorizontal(columnDistance).removeFromAndTo();
        }
        return from.moveVertical(rowDistance).removeFromAndTo();
    }

    @Override
    public boolean canPassThrough(List<AbstractGimul> gimulsOnPath, AbstractGimul abstractGimulAtTo) {
        return gimulsOnPath.isEmpty() && !this.isSameTeam(abstractGimulAtTo);
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
