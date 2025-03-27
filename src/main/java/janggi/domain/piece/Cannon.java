package janggi.domain.piece;


import janggi.domain.Team;
import janggi.domain.piece.direction.Direction;
import janggi.domain.piece.direction.Position;
import janggi.domain.piece.direction.Route;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Cannon extends Piece {

    private static final int REQUIRED_JUMP_PIECES = 1;

    public Cannon(final Position position, final Team team) {
        super(position, team);
    }

    @Override
    public Set<Route> calculateIndependentRoutes() {
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
    public boolean isCannon() {
        return true;
    }
}
