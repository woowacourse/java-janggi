package domain.policy;

import domain.board.Board;
import domain.coordinate.Direction;
import domain.coordinate.Position;

import java.util.ArrayList;
import java.util.List;

public class MiddlePathBlockPolicy implements MovePolicy {

    @Override
    public List<Position> apply(Board board, Position start, List<Direction> directions) {
        List<Position> possibleMoves = new ArrayList<>();
        Position current = start;

        for (int i = 0; i < directions.size() - 1; i++) {
            current = current.nextPosition(directions.get(i));

            if (!board.isEmpty(current)) {
                return possibleMoves;
            }
        }

        possibleMoves.add(current.nextPosition(directions.getLast()));
        return List.copyOf(possibleMoves);
    }
}
