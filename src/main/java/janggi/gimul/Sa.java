package janggi.gimul;

import janggi.Diagonal;
import janggi.Distance;
import janggi.Path;
import janggi.Position;
import janggi.Team;
import java.util.List;

public class Sa extends Gimul {
    protected Sa(Team team) {
        super(team);
    }

    @Override
    public Path getLegalPath(Position from, Position to) {
        Distance distance = Distance.of(from, to);

        if (distance.isMoreThanOneStepIncludingDiagonal()) {
            throw new IllegalArgumentException("이동할 수 없는 위치입니다.");
        }

        if (distance.isHorizontal()) {
            return from.moveHorizontal(distance.columnDistance());
        }

        if (distance.isVertical()) {
            return from.moveVertical(distance.rowDistance());
        }

        Diagonal diagonal = Diagonal.of(distance.rowDistance(), distance.columnDistance());

        return from.moveDiagonal(diagonal);
    }

    @Override
    public boolean canPassThrough(List<Gimul> gimuls) {
        return gimuls.isEmpty();
    }
}
