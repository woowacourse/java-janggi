package janggi.gimul;

import janggi.Distance;
import janggi.Path;
import janggi.Position;
import janggi.Team;
import java.util.List;

public class Ma extends Gimul {

    protected Ma(Team team) {
        super(team);
    }

    @Override
    public Path getLegalPath(Position from, Position to) {
        Distance distance = Distance.of(from, to);

        if (distance.isMoreThanOneStepAndDiagonal()) {
            throw new IllegalArgumentException("이동할 수 없는 위치입니다.");
        }

        if (distance.isHorizontalLonger()) {
            Path first = from.moveHorizontal(distance.straightDistance());
            Path second = first.getDestination().moveVertical(distance.diagonalDistance());
            return Path.concatenate(first, second);
        }
        Path first = from.moveVertical(distance.straightDistance());
        Path second = first.getDestination().moveHorizontal(distance.diagonalDistance());
        return Path.concatenate(first, second);
    }

    @Override
    public boolean canPassThrough(List<Gimul> gimuls) {
        return gimuls.isEmpty();
    }
}
