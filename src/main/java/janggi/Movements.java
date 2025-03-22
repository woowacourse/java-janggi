package janggi;

import janggi.board.Position;
import java.util.ArrayList;
import java.util.List;

public class Movements {
    private final List<Movement> movements;

    public Movements(List<Movement> movements) {
        this.movements = movements;
    }

    public Movements(Movement... movements) {
        this(List.of(movements));
    }

    public Path makePath(Position start) {
        List<Position> path = new ArrayList<>();
        path.add(start);
        Position position = start;
        for (Movement movement : movements) {
            position = movement.movePosition(position);
            path.add(position);
        }
        return new Path(path);
    }
}
