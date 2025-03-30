package janggi.domain.routePolicy;

import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.position.Route;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class RoutePolicyForCannon implements RoutePolicy {
    private static final int REQUIRED_JUMP_PIECES_FOR_CANNON = 1;

    @Override
    public Set<Route> getPossibleRoutes(Piece piece, List<Piece> pieces) {
        return piece.calculateRoutes().stream()
                .filter(route -> isValidCannonRoute(route, pieces, piece))
                .collect(Collectors.toSet());
    }

    private boolean isValidCannonRoute(Route route, List<Piece> pieces, Piece piece) {
        return countJumpablePiecesInRoute(route, pieces, piece) == REQUIRED_JUMP_PIECES_FOR_CANNON;
    }

    private int countJumpablePiecesInRoute(Route route, List<Piece> pieces, Piece piece) {
        // 목적지에 있는 기물 확인
        Optional<Piece> destinationPiece = pieces.stream()
                .filter(route::isDestination)
                .findFirst();

        // 목적지에 적 기물이 있는 경우 - 유효한 경로가 될 수 있음
        if (destinationPiece.isPresent() && piece.isEnemy(destinationPiece.get())) {
            // 적 기물을 제외한 경로 상의 다른 기물 수 계산
            return (int) pieces.stream()
                    .filter(p -> route.hasPosition(p) && !route.isDestination(p))
                    .count();
        }

        // 목적지에 아군 기물이 있거나 또는 경로상에 포가 있는 경우
        long cannonOrFriendlyDestination = pieces.stream()
                .filter(route::hasPosition)
                .filter(currentPiece ->
                        currentPiece.isSameType(PieceType.CANNON) ||
                                (route.isDestination(currentPiece) && !piece.isEnemy(currentPiece))
                )
                .count();

        if (cannonOrFriendlyDestination > 0) {
            return 0;
        }

        // 목적지가 비어있는 경우, 경로 상의 기물 수를 반환
        return (int) pieces.stream()
                .filter(route::hasPosition)
                .count();
    }
}
