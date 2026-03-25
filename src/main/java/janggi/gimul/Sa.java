package janggi.gimul;

import janggi.Path;
import janggi.Position;
import janggi.Team;
import java.util.List;

public class Sa extends Gimul{
    protected Sa(Team team) {
        super(team);
    }

    @Override
    public Path getLegalPath(Position from, Position to) {
        int rowDistance = to.getRowDistance(from);
        int columnDistance = to.getColumnDistance(from);

        int absRowDistance = Math.abs(rowDistance);
        int absColumnDistance = Math.abs(columnDistance);

        if (absRowDistance >= 2 || absColumnDistance >= 2) {
            throw new IllegalArgumentException("이동할 수 없는 위치입니다.");
        }

        if (rowDistance == 1 && columnDistance == 1) {
            return from.moveSouthAndEast();
        }
        if (rowDistance == 1 && columnDistance == -1) {
            return from.moveSouthAndWest();
        }
        if (rowDistance == -1 && columnDistance == 1) {
            return from.moveSouthAndEast();
        }
        if (rowDistance == -1 && columnDistance == -1) {
            return from.moveNorthAndWest();
        }

        if (absRowDistance == 0) {
            return from.moveHorizontal(columnDistance);
        }

        return from.moveVertical(rowDistance);
    }

    @Override
    public boolean canPassThrough(List<Gimul> gimuls) {
        return gimuls.isEmpty();
    }
}
