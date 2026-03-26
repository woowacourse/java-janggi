package janggi.gimul;

import janggi.Distance;
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
        Distance distance = Distance.of(from, to);

        if (distance.isMoreThanOneStepAndDoubleDiagonal()) {
            throw new IllegalArgumentException("이동할 수 없는 위치입니다.");
        }

        if (distance.isHorizontalLonger()) {
            Path first = from.moveHorizontal(distance.straightDistance());
            Path second = first.getDestination().moveVertical(distance.diagonalDistance());
            Path third = Path.concatenate(first, second);
            Path four = third.getDestination().moveVertical(distance.diagonalDistance());
            return Path.concatenate(third, four);
        }
        Path first = from.moveVertical(distance.straightDistance());
        Path second = first.getDestination().moveHorizontal(distance.diagonalDistance());
        Path third = Path.concatenate(first, second);
        Path four = third.getDestination().moveHorizontal(distance.diagonalDistance());
        return Path.concatenate(third, four);
    }

    @Override
    public boolean canPassThrough(List<Gimul> gimuls) {
        return gimuls.isEmpty();
    }
}
