package janggi.gimul;

import janggi.Path;
import janggi.Position;
import janggi.Team;
import java.util.List;

public class Sang extends Gimul {

    protected Sang(Team team) {
        super(team);
    }

    @Override
    public Path getLegalPath(Position from, Position to) {
        int rowDistance = to.getRowDistance(from);
        int columnDistance = to.getColumnDistance(from);

        int absRowDistance = Math.abs(rowDistance);
        int absColumnDistance = Math.abs(columnDistance);

        if ((absRowDistance != 2 || absColumnDistance != 3)
                && (absRowDistance != 3 || absColumnDistance != 2)) {
            throw new IllegalArgumentException("이동할 수 없는 위치입니다.");
        }

        Path first = from.moveVertical(-1);
        Path second = first.getDestination().moveNorthAndWest();

        Path third = Path.concatenate(first, second);
        Path four = third.getDestination().moveNorthAndWest();

        if (rowDistance == -2 && columnDistance == 1) {
            first = from.moveVertical(-1);
            second = first.getDestination().moveNorthAndEast();
            third = Path.concatenate(first, second);
            four = third.getDestination().moveNorthAndEast();
        }

        if (rowDistance == -1 && columnDistance == 2) {
            first = from.moveHorizontal(1);
            second = first.getDestination().moveNorthAndEast();
            third = Path.concatenate(first, second);
            four = third.getDestination().moveNorthAndEast();
        }

        if (rowDistance == 1 && columnDistance == 2) {
            first = from.moveHorizontal(1);
            second = first.getDestination().moveSouthAndEast();
            third = Path.concatenate(first, second);
            four = third.getDestination().moveSouthAndEast();
        }

        if (rowDistance == 2 && columnDistance == 1) {
            first = from.moveVertical(1);
            second = first.getDestination().moveSouthAndEast();
            third = Path.concatenate(first, second);
            four = third.getDestination().moveSouthAndEast();
        }

        if (rowDistance == 2 && columnDistance == -1) {
            first = from.moveVertical(1);
            second = first.getDestination().moveSouthAndWest();
            third = Path.concatenate(first, second);
            four = third.getDestination().moveSouthAndWest();
        }

        if (rowDistance == 1 && columnDistance == -2) {
            first = from.moveHorizontal(-1);
            second = first.getDestination().moveSouthAndWest();
            third = Path.concatenate(first, second);
            four = third.getDestination().moveSouthAndWest();
        }

        if (rowDistance == -1 && columnDistance == -2) {
            first = from.moveHorizontal(-1);
            second = first.getDestination().moveNorthAndWest();
            third = Path.concatenate(first, second);
            four = third.getDestination().moveNorthAndWest();
        }

        return Path.concatenate(third, four);
    }

    @Override
    public boolean canPassThrough(List<Gimul> gimuls) {
        return gimuls.isEmpty();
    }
}
