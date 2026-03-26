package janggi.model.gimul;

import janggi.model.Team;
import janggi.model.position.Position;
import janggi.model.position.PositionPath;
import java.util.List;

public class Byeong extends Gimul {
    public Byeong(Team team) {
        super(team);
    }

    @Override
    public PositionPath getLegalPath(Position from, Position to) {
        int rowDistance = to.getRowDistance(from);
        int columnDistance = to.getColumnDistance(from);

        int absRowDistance = Math.abs(rowDistance);
        int absColumnDistance = Math.abs(columnDistance);

        if ((absRowDistance + absColumnDistance) >= 2) {
            throw new IllegalArgumentException("이동할 수 없는 위치입니다.");
        }

        if (rowDistance == 0) {
            return from.moveHorizontal(columnDistance).removeFromAndTo();
        }

        if ((Team.CHO.equals(team) && rowDistance == 1) ||
                (Team.HAN.equals(team) && rowDistance == -1)) {
            throw new IllegalArgumentException("이동할 수 없는 위치입니다.");
        }
        return from.moveVertical(rowDistance).removeFromAndTo();
    }

    @Override
    public boolean canPassThrough(List<Gimul> gimulsOnPath, Gimul gimulAtTo) {
        return gimulsOnPath.isEmpty() && (gimulAtTo == null || !this.isSameTeam(gimulAtTo));
    }

    @Override
    public String getSymbol() {
        return "병";
    }
}
