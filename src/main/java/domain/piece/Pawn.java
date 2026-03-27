package domain.piece;

import domain.Direction;
import domain.Game;
import domain.Position;
import domain.Side;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {

    public Pawn(Side side) {
        super(side);
    }

    @Override
    public List<Position> getPossibleMoves(Game game, Position start) {
        List<Position> possiblePositions = new ArrayList<>();

        List<Direction> directions = List.of(
                forward(),
                Direction.LEFT,
                Direction.RIGHT
        );

        for (Direction direction : directions) {
            Position destination = start.nextPosition(direction);

            if (game.isAvailableDestination(destination)) {
                possiblePositions.add(destination);
            }
        }

        return possiblePositions;
    }
}
