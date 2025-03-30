package janggi.domain.piece;

import static janggi.domain.Team.RED;
import static janggi.domain.piece.PieceType.SOLDIER;
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
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Soldier extends Piece {

    private static final int SOLDIER_SCORE = 2;

    private static final List<List<Direction>> RED_GUARD_MOVES = List.of(
            List.of(DOWN),
            List.of(LEFT),
            List.of(RIGHT),
            List.of(LEFT_DOWN),
            List.of(RIGHT_DOWN)
    );

    private static final List<List<Direction>> BLUE_GUARD_MOVES = List.of(
            List.of(UP),
            List.of(LEFT),
            List.of(RIGHT),
            List.of(RIGHT_UP),
            List.of(LEFT_UP)
    );

    public Soldier(final Position position, final Team team) {
        super(position, team);
    }

    @Override
    public Set<Route> calculateIndependentRoutes() {
        return getMoveStrategy().stream()
                .map(this::calculateRoute)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    private Route calculateRoute(final List<Direction> move) {
        final List<Position> positions = new ArrayList<>();
        Position currentPosition = position;
        for (final Direction direction : move) {
            if (!currentPosition.canMove(direction)) {
                return null;
            }
            currentPosition = currentPosition.move(direction);
            if (direction.isDiagonal()) {
                if (cannotMoveDiagonal(currentPosition)) {
                    return null;
                }
            }
            positions.add(currentPosition);
        }
        return new Route(positions);
    }

    private boolean cannotMoveDiagonal(final Position currentPosition) {
        return !position.canMoveDiagonalPosition() || !position.isInPalace() || !currentPosition.isInPalace();
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

    private List<List<Direction>> getMoveStrategy() {
        if (team == RED) {
            return RED_GUARD_MOVES;
        }
        return BLUE_GUARD_MOVES;
    }

    @Override
    public double getScore() {
        return SOLDIER_SCORE;
    }

    @Override
    public PieceType getPieceType() {
        return SOLDIER;
    }

}
