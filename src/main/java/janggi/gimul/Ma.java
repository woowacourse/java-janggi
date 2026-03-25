package janggi.gimul;

import janggi.Path;
import janggi.Position;
import janggi.Team;
import java.util.List;

public class Ma extends Gimul{

    protected Ma(Team team) {
        super(team);
    }

    @Override
    public Path getLegalPath(Position from, Position to) {
        int rowDistance = from.getRowDistance(to);
        int columnDistance = from.getColumnDistance(to);

        if ((rowDistance != 1 || columnDistance != 2)
                && (rowDistance != 2 || columnDistance != 1)) {
            throw new IllegalArgumentException("이동할 수 없는 위치입니다.");
        }

        return null;
    }

    @Override
    public boolean canPassThrough(List<Gimul> gimuls) {
        return gimuls.isEmpty();
    }
}
