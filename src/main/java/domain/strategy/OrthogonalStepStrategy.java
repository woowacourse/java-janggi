package domain.strategy;

import domain.board.Board;
import domain.coordinate.Direction;
import domain.coordinate.Position;
import domain.piece.Piece;

import java.util.ArrayList;
import java.util.List;

public class OrthogonalStepStrategy implements MoveStrategy {

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
            Position dest = start.nextPosition(direction);
            result.add(dest);
        }

        return result;
    }
}
