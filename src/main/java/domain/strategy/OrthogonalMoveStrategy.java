package domain.strategy;

import domain.Position;
import domain.moverule.ExtraPalaceMoveDirection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;

public class OrthogonalMoveStrategy extends MoveStrategy {

    protected OrthogonalMoveStrategy(Position position) {
        super(position);
    }

    @Override
    public boolean isMoveAble(Position destination) {
        if (position.isInPalace() && destination.isInPalace()) {
            return isOrthogonal(destination) || isOnSameDiagonalLine(destination);
        }
        return isOrthogonal(destination);
    }

    @Override
    public boolean isPathRestricted(Position destination, List<Position> piecePositions) {
        List<Position> route = routePositions(destination);
        return piecePositions.stream().anyMatch(route::contains);
    }

    protected List<Position> routePositions(Position destination) {
        if (isSameRow(destination)) {
            return createRoute(destination, position -> nextHorizontal(position, destination));
        }

        if (isSameCol(destination)) {
            return createRoute(destination, position -> nextVertical(position, destination));
        }

        if (position.isInPalace() && destination.isInPalace()) {
            return createRoute(destination, position -> nextDiagonal(position, destination));
        }

        return List.of();
    }

    private boolean isOnSameDiagonalLine(Position destination) {
        if (position.isPalaceSide() && destination.isPalaceSide()) {
            int dr = Math.abs(position.row() - destination.row());
            int dc = Math.abs(position.col() - destination.col());

            return dr / dc == 1 || dr % dc == 0;
        }
        return false;
    }

    private boolean isOrthogonal(Position destination) {
        return isSameCol(destination) || isSameRow(destination);
    }

    private List<Position> createRoute(Position destination, UnaryOperator<Position> nextStep) {
        List<Position> route = new ArrayList<>();
        Position current = nextStep.apply(position);

        while (!current.equals(destination)) {
            route.add(current);
            current = nextStep.apply(current);
        }
        return route;
    }

    private Position nextHorizontal(Position current, Position destination) {
        if (current.col() < destination.col()) {
            return current.right();
        }
        return current.left();
    }

    private Position nextVertical(Position current, Position destination) {
        if (current.row() < destination.row()) {
            return current.down();
        }
        return current.up();
    }

    private Position nextDiagonal(Position current, Position destination) {
        Optional<UnaryOperator<Position>> extraDirection = ExtraPalaceMoveDirection.additionalDirection(current);
        if (extraDirection.isPresent()) {
            return extraDirection.get().apply(current);
        }
        return destination;
    }

    private boolean isSameRow(Position destination) {
        return position.row() == destination.row();
    }

    private boolean isSameCol(Position destination) {
        return position.col() == destination.col();
    }
}
