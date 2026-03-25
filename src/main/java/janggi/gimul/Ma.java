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
        int rowDistance = to.getRowDistance(from);
        int columnDistance = to.getColumnDistance(from);

        int absRowDistance = Math.abs(rowDistance);
        int absColumnDistance = Math.abs(columnDistance);

        if ((absRowDistance != 1 || absColumnDistance != 2)
                && (absRowDistance != 2 || absColumnDistance != 1)) {
            throw new IllegalArgumentException("이동할 수 없는 위치입니다.");
        }

        Path first = from.moveVertical(-1);
        Path second = first.getDestination().moveNorthAndWest();

        if (rowDistance == -2 && columnDistance == 1) {
            first = from.moveVertical(-1);
            second = first.getDestination().moveNorthAndEast();
        }

        if (rowDistance == -1 && columnDistance == 2) {
            first = from.moveHorizontal(1);
            second = first.getDestination().moveNorthAndEast();
        }

        if (rowDistance == 1 && columnDistance == 2) {
            first = from.moveHorizontal(1);
            second = first.getDestination().moveSouthAndEast();
        }

        if (rowDistance == 2 && columnDistance == 1) {
            first = from.moveVertical(1);
            second = first.getDestination().moveSouthAndEast();
        }

        if (rowDistance == 2 && columnDistance == -1) {
            first = from.moveVertical(1);
            second = first.getDestination().moveSouthAndWest();
        }

        if (rowDistance == 1 && columnDistance == -2) {
            first = from.moveHorizontal(-1);
            second = first.getDestination().moveSouthAndWest();
        }

        if (rowDistance == -1 && columnDistance == -2) {
            first = from.moveHorizontal(-1);
            second = first.getDestination().moveNorthAndWest();
        }

        return Path.concatenate(first, second);
    }

    @Override
    public boolean canPassThrough(List<Gimul> gimuls) {
        return gimuls.isEmpty();
    }
}
