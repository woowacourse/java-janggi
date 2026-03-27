package domain.piece;

import domain.Direction;
import domain.board.Board;
import domain.Position;
import domain.Side;

import java.util.ArrayList;
import java.util.List;

public class Chariot extends Piece {

    public Chariot(Side side) {
        super(side);
    }

    @Override
    public List<Position> getPossibleMoves(Board board, Position start) {
        List<Position> possiblePositions = new ArrayList<>();

        List<Direction> directions = List.of(
                Direction.UP,
                Direction.DOWN,
                Direction.LEFT,
                Direction.RIGHT
        );

        for (Direction direction : directions) {
            Position current = start;
            while (true) {
                current = current.nextPosition(direction);

                if (!board.isAvailableDestination(current)) {
                    break;
                }

                possiblePositions.add(current);

                if (board.isOpponentPiece(current)) {
                    break;
                }
            }
        }

        return possiblePositions;
    }
}
