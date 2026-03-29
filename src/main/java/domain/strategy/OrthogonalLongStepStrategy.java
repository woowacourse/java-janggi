package domain.strategy;

import domain.board.Board;
import domain.coordinate.Direction;
import domain.coordinate.Position;
import domain.piece.Piece;

import java.util.ArrayList;
import java.util.List;

public class OrthogonalLongStepStrategy implements MoveStrategy {

    private static final List<Direction> DIRECTIONS = List.of(
            Direction.UP,
            Direction.DOWN,
            Direction.LEFT,
            Direction.RIGHT
    );

    @Override
    public List<Position> generate(Board board, Position start, Piece piece) {
        List<Position> result = new ArrayList<>();

        for (Direction direction : DIRECTIONS) {
            Position current = start;

            while (true) {
                current = current.nextPosition(direction);

                if (!board.isValidRange(current)) {
                    break;
                }

                result.add(current);

                if (!board.isEmpty(current)) {
                    break;
                }
            }
        }

        return result;
    }
}
