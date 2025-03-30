package janggi.domain.piece;


import static janggi.domain.piece.PieceType.CANNON;
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

public class Cannon extends Piece {

    private static final int REQUIRED_JUMP_PIECES = 1;
    private static final int CANNON_SCORE = 7;

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

    public Cannon(final Position position, final Team team) {
        super(position, team);
    }

    @Override
    public boolean isCannon() {
        return true;
    }

    @Override
    public Set<Route> calculateIndependentRoutes() {
        final Set<Route> validRoutes = new HashSet<>();

        for (final Direction direction : CANNON_MOVE) {
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
        return isValidCannonRoute(route, otherPieces);
    }

    private boolean isValidCannonRoute(final Route route, final List<Piece> otherPieces) {
        final List<Piece> piecesInRoute = getPiecesInRoute(route, otherPieces);

        if (hasCannon(piecesInRoute)) {
            return false;
        }
        return checkJumpPieceAndTargetPiece(route, piecesInRoute);
    }

    private List<Piece> getPiecesInRoute(final Route route, final List<Piece> pieces) {
        return pieces.stream()
                .filter(route::hasPosition)
                .toList();
    }

    private boolean hasCannon(final List<Piece> piecesInRoute) {
        return piecesInRoute.stream().anyMatch(Piece::isCannon);
    }

    private boolean checkJumpPieceAndTargetPiece(final Route route, final List<Piece> piecesInRoute) {
        final Piece targetPiece = getDestinationPiece(route, piecesInRoute);
        final List<Piece> filteredPieces = piecesInRoute.stream()
                .filter(piece -> !route.isDestination(piece))
                .toList();
        if (isNotJumpOnePiece(filteredPieces)) {
            return false;
        }
        return targetPiece == null || !targetPiece.isSameTeam(team);
    }

    private Piece getDestinationPiece(final Route route, final List<Piece> piecesInRoute) {
        return piecesInRoute.stream()
                .filter(route::isDestination)
                .findFirst()
                .orElse(null);
    }

    private boolean isNotJumpOnePiece(final List<Piece> jumpPieces) {
        return jumpPieces.size() != REQUIRED_JUMP_PIECES;
    }

    @Override
    public double getScore() {
        return CANNON_SCORE;
    }

    @Override
    public PieceType getPieceType() {
        return CANNON;
    }
}
