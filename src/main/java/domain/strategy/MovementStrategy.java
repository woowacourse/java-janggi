package domain.strategy;

import domain.Position;
import domain.Side;
import domain.board.BoardReader;
import java.util.ArrayList;
import java.util.List;

public interface MovementStrategy {
    List<Path> generatePaths(Position current);
    List<Position> getMovablePositions(Position current, BoardReader board, Side side);

    default Path createPath(Position current, Direction direction) {
        List<Position> positions = new ArrayList<>();
        Position position = current;
        while (position.canMove(direction)) {
            position = position.move(direction);
            positions.add(position);
        }
        return new Path(positions);
    }
}
