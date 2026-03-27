package domain.piece;

import domain.coordinate.Direction;
import domain.Game;
import domain.coordinate.Position;
import domain.Side;
import java.util.ArrayList;
import java.util.List;

public final class Guard extends Piece {

    public Guard(Side side) {
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
            Position destination = start.nextPosition(direction);

            if (!game.isAvailableDestination(destination)) {
                continue;
            }

            possiblePositions.add(destination);
        }

        return possiblePositions;
    }
}
