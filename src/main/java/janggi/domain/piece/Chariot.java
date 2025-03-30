package janggi.domain.piece;

import static janggi.domain.piece.PieceType.CHARIOT;
import static janggi.domain.piece.direction.Direction.DOWN;
import static janggi.domain.piece.direction.Direction.LEFT;
import static janggi.domain.piece.direction.Direction.LEFT_DOWN;
import static janggi.domain.piece.direction.Direction.LEFT_UP;
import static janggi.domain.piece.direction.Direction.RIGHT;
import static janggi.domain.piece.direction.Direction.RIGHT_DOWN;
import static janggi.domain.piece.direction.Direction.RIGHT_UP;
import static janggi.domain.piece.direction.Direction.UP;

import janggi.domain.Team;
import janggi.domain.piece.direction.Direction;
import janggi.domain.piece.direction.Position;
import janggi.domain.piece.direction.Route;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Chariot extends Piece {

    private static final int CHARIOT_SCORE = 13;

    private static final List<Direction> CANNON_MOVE = List.of(
            RIGHT_UP,
            RIGHT,
            RIGHT_DOWN,
            UP,
            LEFT_UP,
            LEFT,
            LEFT_DOWN,
            DOWN
    );

    public Chariot(final Position position, final Team team) {
        super(position, team);
    }

    @Override
    public Set<Route> calculateIndependentRoutes() {
        final Set<Route> rawRoutes = new HashSet<>();

        for (final Direction direction : CANNON_MOVE) {
            rawRoutes.addAll(generateRoutesInDirection(direction));
        }
        return rawRoutes;
    }

    private Set<Route> generateRoutesInDirection(final Direction direction) {
        final Set<Route> directionalRoutes = new HashSet<>();

        Position currentPosition = position;
        final List<Position> positions = new ArrayList<>();
        while (currentPosition.canMove(direction)) {
            final Position nextPosition = currentPosition.move(direction);
            if (direction.isDiagonal()) {
                if (cannotMoveDiagonal(nextPosition)) {
                    break;
                }
            }
            positions.add(nextPosition);
            directionalRoutes.add(new Route(new ArrayList<>(positions)));
            currentPosition = nextPosition;
        }
        return directionalRoutes;
    }

    private boolean cannotMoveDiagonal(final Position nextPosition) {
        return !position.canMoveDiagonalPosition() || !position.isInPalace() || !nextPosition.isInPalace();
    }

    @Override
    public boolean isValidRoute(final Route route, final List<Piece> otherPieces) {
        final List<Piece> piecesInRoute = otherPieces.stream()
                .filter(route::hasPosition)
                .toList();
        return checkPiecesInRoute(route, piecesInRoute);
    }

    private boolean checkPiecesInRoute(final Route route, final List<Piece> piecesInRoute) {
        if (piecesInRoute.isEmpty()) {
            return true;
        }
        if (piecesInRoute.size() == 1) {
            final Piece pieceInWay = piecesInRoute.getFirst();
            return route.isDestination(pieceInWay) && isEnemy(pieceInWay);
        }
        return piecesInRoute.stream()
                .allMatch(piece -> route.isDestination(piece) && isEnemy(piece));
    }

    @Override
    public double getScore() {
        return CHARIOT_SCORE;
    }

    @Override
    public PieceType getPieceType() {
        return CHARIOT;
    }
}
