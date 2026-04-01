package janggi.model.movement;

import janggi.model.position.PositionPath;
import janggi.model.position.Column;
import janggi.model.position.Position;
import janggi.model.position.Row;
import java.util.ArrayList;
import java.util.List;

public abstract class MultiStepMovement implements Movement{

    protected PositionPath moveHorizontally(Position from, Position to) {
        List<Position> between = new ArrayList<>();

        int fromValue = from.column().getValue();
        int toValue = to.column().getValue();

        int step = 1;
        if (fromValue > toValue) {
            step = -1;
        }

        int current = fromValue + step;

        while (current != toValue) {
            between.add(new Position(
                    from.row(),
                    Column.of(current++)
            ));
        }

        return new PositionPath(between);
    }

    protected PositionPath moveVertically(Position from, Position to) {
        List<Position> between = new ArrayList<>();

        int fromValue = from.row().getValue();
        int toValue = to.row().getValue();

        int step = 1;
        if (fromValue > toValue) {
            step = -1;
        }

        int current = fromValue + step;

        while (current != toValue) {
            between.add(new Position(
                    Row.of(current),
                    from.column()
            ));

            current += step;
        }

        return new PositionPath(between);
    }
}
