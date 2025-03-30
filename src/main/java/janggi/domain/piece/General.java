package janggi.domain.piece;

import static janggi.domain.piece.PieceType.GENERAL;
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

public class General extends Piece {

    private static final int GENERAL_SCORE = 0;

    private static final List<List<Direction>> GENERAL_MOVES = List.of(
            List.of(UP),
            List.of(DOWN),
            List.of(LEFT),
            List.of(RIGHT),
            List.of(RIGHT_UP),
            List.of(RIGHT_DOWN),
            List.of(LEFT_UP),
            List.of(LEFT_DOWN)
    );

    public General(final Position position, final Team team) {
        super(position, team);
    }

    @Override
    public Set<Route> calculateIndependentRoutes() {
        return GENERAL_MOVES.stream()
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

    @Override
    public boolean isGeneral() {
        return true;
    }

    @Override
    public double getScore() {
        return GENERAL_SCORE;
    }

    @Override
    public PieceType getPieceType() {
        return GENERAL;
    }
}
