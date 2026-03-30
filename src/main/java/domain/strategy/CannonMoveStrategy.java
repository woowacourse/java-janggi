package domain.strategy;

import domain.board.Board;
import domain.coordinate.Direction;
import domain.coordinate.Position;
import domain.piece.Piece;
import domain.piece.PieceType;

import java.util.ArrayList;
import java.util.List;

public class CannonMoveStrategy implements MoveStrategy {

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
            exploreDirection(board, start, direction, result);
        }

        return result;
    }

    private void exploreDirection(Board board, Position start, Direction direction, List<Position> result) {
        Position current = start;
        boolean jumped = false;

        while (true) {
            current = current.nextPosition(direction);

            if (!board.isValidRange(current)) {
                break;
            }

            Piece target = board.getPiece(current);

            if (target.getType() == PieceType.CANNON) {
                break;
            }

            if (!jumped) {
                if (!target.isNeutral()) {
                    jumped = true;
                }

                continue;
            }

            result.add(current);

            if (!target.isNeutral()) {
                break;
            }
        }
    }
}
