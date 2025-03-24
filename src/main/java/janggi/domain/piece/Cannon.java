package janggi.domain.piece;


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

    private Set<Route> generateRoutesInDirection(final Direction direction) {
        final Set<Route> directionalRoutes = new HashSet<>();

        Position currentPosition = position;
        final List<Position> positions = new ArrayList<>();
        while (currentPosition.canMove(direction)) {
            final Position nextPosition = currentPosition.move(direction);
            positions.add(nextPosition);
            directionalRoutes.add(new Route(new ArrayList<>(positions)));
            currentPosition = nextPosition;
        }
        return directionalRoutes;
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
}
