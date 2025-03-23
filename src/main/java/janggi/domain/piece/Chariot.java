package janggi.domain.piece;

import janggi.domain.Team;
import janggi.domain.piece.direction.Direction;
import janggi.domain.piece.direction.Position;
import janggi.domain.piece.direction.Route;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Chariot extends Piece {

    public Chariot(final Position position, final Team team) {
        super(position, team);
    }

    @Override
    public boolean isChariot() {
        return true;
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
        final List<Piece> piecesInRoute = otherPieces.stream()
                .filter(route::hasPosition)
                .toList();
        if (piecesInRoute.isEmpty()) {
            return true;
        }

        if (piecesInRoute.size() == 1) {
            final Piece pieceInWay = piecesInRoute.get(0);
            return route.isDestination(pieceInWay) && isEnemy(pieceInWay);
        }
        return piecesInRoute.stream()
                .allMatch(piece -> route.isDestination(piece) && isEnemy(piece));
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
        final List<Position> rawPositions = new ArrayList<>();

        for (int step = 1; step <= steps; step++) {
            final int newX = position.x() + (direction.dx() * step);
            final int newY = position.y() + (direction.dy() * step);
            rawPositions.add(new Position(newX, newY));
        }
        return rawPositions;
    }

    private int getMaxSteps(final Direction direction, final Position position) {
        if (direction.dx() > 0) {
            return 8 - position.x();
        } else if (direction.dx() < 0) {
            return position.x();
        } else if (direction.dy() > 0) {
            return 9 - position.y();
        }
        return position.y();
    }
}
