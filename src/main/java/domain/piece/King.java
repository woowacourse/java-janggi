package domain.piece;

import domain.Direction;
import domain.board.Board;
import domain.Position;
import domain.Side;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece {

    public King(Side side) {
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
            Position destination = start.nextPosition(direction);

            if (!board.isAvailableDestination(destination)) {
                continue;
            }

            possiblePositions.add(destination);
        }

        return possiblePositions;
    }
}
