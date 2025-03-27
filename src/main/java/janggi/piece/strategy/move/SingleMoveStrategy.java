package janggi.piece.strategy.move;

import janggi.Board;
import janggi.coordinate.Distance;
import janggi.coordinate.Position;
import janggi.coordinate.Route;

public class SingleMoveStrategy implements MoveStrategy {

    public static final int SINGLE_STEP = 1;

    @Override
    public void validate(final Board board, final Position departure, final Position destination) {
        Distance distance = Distance.of(departure, destination);

        if (distance.getTotal() == SINGLE_STEP) {
            return;
        }

        if (board.isPalace(departure)) {
            Route route = Route.of(departure, destination);

            for (Position position : route.calculateWithDepartureAndDestination()) {
                if (board.isCenterOfPalace(position) && distance.isDiagonal() && distance.getDiagonal() == SINGLE_STEP) {
                    return;
                }
            }
        }

        throw new IllegalArgumentException(EXCEPTION_MESSAGE);
    }
}
