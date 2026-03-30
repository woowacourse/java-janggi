package janggi.model.board.movement;

import janggi.model.board.position.Column;
import janggi.model.board.position.Position;
import janggi.model.board.PositionPath;
import janggi.model.board.position.Row;
import java.util.ArrayList;
import java.util.List;

public class StraightMovement implements Movement {

    @Override
    public PositionPath move(Position from, Position to) {
        if (from.isSameColumn(to) == from.isSameRow(to)) {
            throw new IllegalArgumentException("직선 관계에 위치해 있지 않습니다.");
        }

         if (from.isSameRow(to)) {
            return moveHorizontally(from, to);
         }

        return moveVertically(from, to);
    }

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
