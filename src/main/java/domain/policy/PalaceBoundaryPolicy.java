package domain.policy;

import domain.board.Board;
import domain.coordinate.Direction;
import domain.coordinate.Position;

import java.util.ArrayList;
import java.util.List;

public class PalaceBoundaryPolicy implements MovePolicy {

    @Override
    public List<Position> apply(Board board, Position start, List<Direction> directions) {
        List<Position> possibleMoves = new ArrayList<>();
        Position current = start;

        for (Direction direction : directions) {
            current = current.nextPosition(direction);

            if (current.isInPalace()) {
                possibleMoves.add(current);
            }
        }

        return List.copyOf(possibleMoves);
    }
}
