package domain.strategy;

import domain.board.Board;
import domain.coordinate.Direction;
import domain.coordinate.Position;
import domain.piece.Piece;

import java.util.ArrayList;
import java.util.List;

public class PawnMoveStrategy implements MoveStrategy {

    @Override
    public List<Position> generate(Board board, Position start, Piece piece) {
        List<Position> result = new ArrayList<>();

        List<Direction> directions = List.of(
                piece.forward(),
                Direction.LEFT,
                Direction.RIGHT
        );

        for (Direction direction : directions) {
            result.add(start.nextPosition(direction));
        }

        return result;
    }
}
