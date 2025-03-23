package janggi.domain.piece;

import static janggi.domain.piece.direction.BoardSize.MAX_X;
import static janggi.domain.piece.direction.BoardSize.MAX_Y;
import static janggi.domain.piece.direction.BoardSize.MIN_X;
import static janggi.domain.piece.direction.BoardSize.MIN_Y;

import janggi.domain.Team;
import janggi.domain.piece.direction.Direction;
import janggi.domain.piece.direction.Position;
import janggi.domain.piece.direction.Route;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Cannon extends Piece {

    private static final int REQUIRED_JUMP_PIECES = 1;

    public Cannon(final Position position, final Team team) {
        super(position, team);
    }

    @Override
    public Set<Route> calculateRoutes() {
        final Set<Route> rawRoutes = new HashSet<>();

        for (final Direction direction : Direction.getStraightDirections()) {
            rawRoutes.addAll(generateRoutesInDirection(direction));
        }
        return rawRoutes;
    }

    @Override
    protected boolean isValidRoute(final Route route, final List<Piece> otherPieces) {
        return isValidCannonRoute(route, otherPieces);
    }

    private boolean isValidCannonRoute(final Route route, final List<Piece> otherPieces) {
        return countPiecesInRoute(route, otherPieces) == REQUIRED_JUMP_PIECES;
    }

    private int countPiecesInRoute(final Route route, final List<Piece> otherPieces) {
        final long cannonOrDestinationCount = otherPieces.stream()
                .filter(route::hasPosition)
                .filter(route::isDestination)
                .count();

        if (cannonOrDestinationCount > 0) {
            return 0;
        }

        return (int) otherPieces.stream()
                .filter(route::hasPosition)
                .count();
    }

    private Set<Route> generateRoutesInDirection(final Direction direction) {
        final Set<Route> directionalRoutes = new HashSet<>();
        final int maxSteps = getMaxSteps(direction, position);

        for (int steps = 1; steps <= maxSteps; steps++) {
            final List<Position> positions = new ArrayList<>(generatePositions(direction, steps, position));
            directionalRoutes.add(new Route(positions));
        }
        return directionalRoutes;
    }

    private List<Position> generatePositions(final Direction direction, final int steps,
                                             final Position position) {
        final List<Position> positions = new ArrayList<>();

        for (int step = 1; step <= steps; step++) {
            final int newX = position.x() + (direction.dx() * step);
            final int newY = position.y() + (direction.dy() * step);
            positions.add(new Position(newX, newY));
        }
        return positions;
    }

    private int getMaxSteps(final Direction direction, final Position position) {
        if (direction.dx() > MIN_X.getSize()) {
            return MAX_X.getSize() - position.x();
        } else if (direction.dx() < MIN_X.getSize()) {
            return position.x();
        } else if (direction.dy() > MIN_Y.getSize()) {
            return MAX_Y.getSize() - position.y();
        }
        return position.y();
    }
}
