package domain.piece;

import domain.Direction;
import domain.Game;
import domain.Position;
import domain.Side;

import java.util.ArrayList;
import java.util.List;

public class Elephant extends Piece {

    public Elephant(Side side) {
        super(side);
    }

    @Override
    public List<Position> getPossibleMoves(Game game, Position start) {
        List<List<Direction>> paths = List.of(
                List.of(Direction.UP, Direction.UP_LEFT, Direction.UP_LEFT),
                List.of(Direction.UP, Direction.UP_RIGHT, Direction.UP_RIGHT),
                List.of(Direction.DOWN, Direction.DOWN_LEFT, Direction.DOWN_LEFT),
                List.of(Direction.DOWN, Direction.DOWN_RIGHT, Direction.DOWN_RIGHT),
                List.of(Direction.LEFT, Direction.UP_LEFT, Direction.UP_LEFT),
                List.of(Direction.LEFT, Direction.DOWN_LEFT, Direction.DOWN_LEFT),
                List.of(Direction.RIGHT, Direction.UP_RIGHT, Direction.UP_RIGHT),
                List.of(Direction.RIGHT, Direction.DOWN_RIGHT, Direction.DOWN_RIGHT));

        List<Position> possiblePositions = new ArrayList<>();

        for (List<Direction> path : paths) {
            Position firstMovePosition = start.nextPosition(path.getFirst());
            if (game.isNotEmpty(firstMovePosition)) {
                continue;
            }

            Position secondMovePosition = firstMovePosition.nextPosition(path.get(1));
            if (game.isNotEmpty(secondMovePosition)) {
                continue;
            }

            Position destination = secondMovePosition.nextPosition(path.get(1));
            if (game.isAvailableDestination(destination)) {
                possiblePositions.add(destination);
            }
        }

        return possiblePositions;
    }
}
