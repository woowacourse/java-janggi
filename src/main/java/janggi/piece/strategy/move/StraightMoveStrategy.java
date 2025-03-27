package janggi.piece.strategy.move;

import janggi.Board;
import janggi.coordinate.Distance;
import janggi.coordinate.Position;
import janggi.coordinate.Route;

public class StraightMoveStrategy implements MoveStrategy {

    @Override
    public void validate(final Board board, final Position departure, final Position destination) {
        Distance distance = Distance.of(departure, destination);

        if (distance.isStraight()) {
            return;
        }

        if (board.isPalace(departure)) {
            Route route = Route.of(departure, destination);

            for (Position position : route.calculateWithDepartureAndDestination()) {
                if (distance.isDiagonal() && board.isCenterOfPalace(position) && board.isPalace(departure) && board.isPalace(destination)) {
                    return;
                }
            }
        }

        throw new IllegalArgumentException(EXCEPTION_MESSAGE);
    }
}
