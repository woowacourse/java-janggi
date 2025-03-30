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
    public Set<Route> getPossibleRoutes(final Piece piece, final List<Piece> pieces) {
        return piece.calculateRoutes().stream()
                .filter(route -> isValidCannonRoute(route, pieces, piece))
                .collect(Collectors.toSet());
    }

    private boolean isValidCannonRoute(final Route route, final List<Piece> pieces, final Piece piece) {
        return countJumpablePiecesInRoute(route, pieces, piece) == REQUIRED_JUMP_PIECES_FOR_CANNON;
    }

    private int countJumpablePiecesInRoute(final Route route, final List<Piece> pieces, final Piece piece) {
        // 목적지에 있는 기물 확인
        Optional<Piece> destinationPiece = pieces.stream()
                .filter(route::isDestination)
                .findFirst();

        // 목적지에 적 기물이 있는 경우
        if (destinationPiece.isPresent() && piece.isEnemy(destinationPiece.get())) {
            boolean hasCannonInRoute = pieces.stream()
                    .filter(route::hasPosition)
                    .anyMatch(currentPiece -> currentPiece.isSameType(PieceType.CANNON));

            // 포 기물이 있으면 이동 불가능
            if (hasCannonInRoute) {
                return 0;
            }

            // 적 기물을 제외한 경로 상의 다른 기물 수 계산
            return (int) pieces.stream()
                    .filter(p -> route.hasPosition(p) && !route.isDestination(p))
                    .count();
        }

        // 목적지에 아군 기물이 있는 경우 -> 그냥 이동 불가능
        if (destinationPiece.isPresent() && !piece.isEnemy(destinationPiece.get())) {
            return 0;
        }

        // 목적지가 비어있는 경우, 경로 상의 기물 수를 반환, 경로에 포가 있으면 이동 불가
        boolean hasCanonInRoute = pieces.stream()
                .filter(route::hasPosition)
                .anyMatch(currentPiece -> currentPiece.isSameType(PieceType.CANNON));

        if (hasCanonInRoute) {
            return 0;
        }

        return (int) pieces.stream()
                .filter(route::hasPosition)
                .count();
    }
}
