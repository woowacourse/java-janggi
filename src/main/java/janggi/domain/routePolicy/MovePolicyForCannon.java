package janggi.domain.routePolicy;

import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.position.Route;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MovePolicyForCannon implements MovePolicy {
    private static final int REQUIRED_JUMP_PIECES_FOR_CANNON = 1;

    @Override
    public Set<Route> getPossibleRoutes(Piece piece, List<Piece> pieces) {
        return piece.calculateRoutes().stream()
                .filter(route -> isValidCannonRoute(route, pieces))
                .collect(Collectors.toSet());
    }

    private boolean isValidCannonRoute(Route route, List<Piece> pieces) {
        return countJumpablePiecesInRoute(route, pieces) == REQUIRED_JUMP_PIECES_FOR_CANNON;
    }

    private int countJumpablePiecesInRoute(Route route, List<Piece> pieces) {
        long cannonOrDestinationCount = pieces.stream()
                .filter(route::hasPosition)
                .filter(currentPiece -> currentPiece.isSameType(PieceType.CANNON) || route.isDestination(currentPiece))
                .count();

        if (cannonOrDestinationCount > 0) {
            return 0;
        }

        return (int) pieces.stream()
                .filter(route::hasPosition)
                .count();
    }
}
