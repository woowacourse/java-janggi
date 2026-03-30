package domain.piece;

import domain.coordinate.Direction;
import domain.Game;
import domain.coordinate.Position;
import domain.Side;

import java.util.ArrayList;
import java.util.List;

public final class Cannon extends Piece {

    public Cannon(Side side) {
        super(side);
    }

    @Override
    public Piece withSide(Side side) {
        return new Cannon(side);
    }

    @Override
    public boolean isEmpty() {
        return false;
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

                if (game.isCannon(current)) {
                    break;
                }

                if (!game.isCannon(current) && game.isNotEmpty(current)) {
                    while (true) {
                        current = current.nextPosition(direction);
                        if (!game.isAvailableDestination(current) || game.isCannon(current)) {
                            break;
                        }

                        possiblePositions.add(current);
                        if (game.isOpponentPiece(current)) {
                            break;
                        }
                    }
                    break;
                }
            }
        }

        return possiblePositions;
    }
}
