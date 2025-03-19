package janggi.piece;

import janggi.Side;
import janggi.board.Position;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class Horse implements Piece {

    private final Side side;

    public Horse(final Side side) {
        this.side = side;
    }

    @Override
    public List<Route> computeCandidatePositions(final Position position) {
        return List.of(
                computeRouteFixDeltaX(position, 1, 1),
                computeRouteFixDeltaX(position, 1, -1),
                computeRouteFixDeltaX(position, -1, 1),
                computeRouteFixDeltaX(position, -1, -1),

                computeRouteFixDeltaY(position, 1, 1),
                computeRouteFixDeltaY(position, 1, -1),
                computeRouteFixDeltaY(position, -1, 1),
                computeRouteFixDeltaY(position, -1, -1)
        );
    }

    private static Route computeRouteFixDeltaX(final Position position, int deltaX, int upDown) {
        Deque<Position> candidate = new ArrayDeque<>();
        Position position1 = position.move(deltaX, 0);
        Position position2 = position1.move(deltaX, upDown);

        candidate.add(position1);
        candidate.add(position2);
        return new Route(candidate);
    }

    private static Route computeRouteFixDeltaY(final Position position, int deltaY, int leftRight) {
        Deque<Position> candidate = new ArrayDeque<>();
        Position position1 = position.move(0, deltaY);
        Position position2 = position1.move(leftRight, deltaY);

        candidate.add(position1);
        candidate.add(position2);
        return new Route(candidate);
    }

    @Override
    public String getSymbol() {
        return "M";
    }

    @Override
    public boolean isCho() {
        return side == Side.CHO;
    }

    @Override
    public boolean isHan() {
        return side == Side.HAN;
    }
}
