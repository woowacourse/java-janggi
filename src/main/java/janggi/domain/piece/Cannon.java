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
        final Set<Route> validRoutes = new HashSet<>();

        for (final Direction direction : Direction.getStraightDirections()) {
            validRoutes.addAll(generateRoutesInDirection(direction));
        }
        return validRoutes;
    }

    @Override
    protected boolean isValidRoute(final Route route, final List<Piece> otherPieces) {
        return isValidCannonRoute(route, otherPieces);
    }

    private boolean isValidCannonRoute(final Route route, final List<Piece> otherPieces) {
        final List<Piece> piecesInRoute = otherPieces.stream()
                .filter(piece -> route.hasPosition(piece) && !piece.isSamePosition(position))
                .toList();

        if (piecesInRoute.stream().anyMatch(Piece::isCannon)) {
            return false;
        }

        final Position destination = route.getDestination();

        final List<Piece> jumpPieces = new ArrayList<>();
        Piece targetPiece = null;

        for (final Piece piece : piecesInRoute) {
            if (piece.isSamePosition(destination)) {
                targetPiece = piece;
                continue;
            }
            jumpPieces.add(piece);
        }

        if (jumpPieces.size() != REQUIRED_JUMP_PIECES) {
            return false;
        }

        return targetPiece == null || !targetPiece.isSameTeam(team);
    }

    @Override
    public boolean isCannon() {
        return true;
    }

    private Set<Route> generateRoutesInDirection(final Direction direction) {
        final Set<Route> directionalRoutes = new HashSet<>();
        final int maxSteps = calculateMaxSteps(direction);

        for (int steps = 1; steps <= maxSteps; steps++) {
            final List<Position> positions = generatePositionsInDirection(direction, steps);
            directionalRoutes.add(new Route(positions));
        }
        return directionalRoutes;
    }

    private List<Position> generatePositionsInDirection(final Direction direction, final int steps) {
        final List<Position> positions = new ArrayList<>();

        for (int step = 1; step <= steps; step++) {
            final int newX = position.x() + (direction.dx() * step);
            final int newY = position.y() + (direction.dy() * step);

            if (isWithinBoardBounds(newX, newY)) {
                positions.add(new Position(newX, newY));
            } else {
                break;
            }
        }
        return positions;
    }

    private boolean isWithinBoardBounds(final int x, final int y) {
        return x >= MIN_X.getSize() && x <= MAX_X.getSize() &&
                y >= MIN_Y.getSize() && y <= MAX_Y.getSize();
    }

    private int calculateMaxSteps(final Direction direction) {
        if (direction.dx() > 0) {
            return MAX_X.getSize() - position.x();
        } else if (direction.dx() < 0) {
            return position.x() - MIN_X.getSize();
        } else if (direction.dy() > 0) {
            return MAX_Y.getSize() - position.y();
        }
        return position.y() - MIN_Y.getSize();
    }
}
