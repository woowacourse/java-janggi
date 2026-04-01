package domain.policy;

import domain.board.Board;
import domain.coordinate.Direction;
import domain.coordinate.Position;

import java.util.ArrayList;
import java.util.List;

public class PathBlockedPolicy implements MovePolicy {

    @Override
    public List<Position> apply(Board board, Position start, List<Direction> directions) {
        List<Position> possibleMoves = new ArrayList<>();
        Position current = start;

        for (Direction direction : directions) {
            current = current.nextPosition(direction);
            possibleMoves.add(current);

            if (!board.isEmpty(current)) {
                break;
            }
        }

        return List.copyOf(possibleMoves);
    }
}
