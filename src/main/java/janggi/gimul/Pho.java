package janggi.gimul;

import janggi.Team;
import janggi.position.Position;
import janggi.position.PositionPath;
import java.util.List;

public class Pho extends Gimul {
    public Pho(Team team) {
        super(team);
    }

    @Override
    public PositionPath getLegalPath(Position from, Position to) {
        if ((!from.isSameRow(to) && !from.isSameColumn(to)) || from.equals(to)) {
            throw new IllegalArgumentException("해당 경로로는 이동할 수 없습니다.");
        }

        if (from.isSameRow(to)) {
            return from.moveHorizontal(to.getColumnDistance(from)).removeFromAndTo();
        }

        return from.moveVertical(to.getRowDistance(from)).removeFromAndTo();
    }

    @Override
    public boolean canPassThrough(List<Gimul> gimulsOnPath, Gimul gimulAtTo) {
        return gimulsOnPath.size() == 1
                && !(gimulsOnPath.getFirst() instanceof Pho)
                && (gimulAtTo == null || !this.isSameTeam(gimulAtTo));
    }
}
