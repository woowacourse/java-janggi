package domain.piece;

import domain.coordinate.Direction;
import domain.Game;
import domain.coordinate.Position;
import domain.Side;

import java.util.ArrayList;
import java.util.List;

public final class Chariot extends Piece {

    public Chariot(Side side) {
        super(side);
    }

    @Override
    public List<Position> getPossibleMoves(Game game, Position start) {
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

                if (!game.isAvailableDestination(current)) {
                    break;
                }

                possiblePositions.add(current);

                if (game.isOpponentPiece(current)) {
                    break;
                }
            }
        }

        return possiblePositions;
    }
}
