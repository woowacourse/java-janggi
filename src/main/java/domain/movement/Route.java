package domain.movement;

import domain.board.Intersection;
import domain.board.Palace;
import domain.game.Side;
import domain.piece.AlivePieces;
import domain.piece.Piece;
import java.util.List;
import java.util.Objects;

public class Route {

    private final Palace palace = Palace.getInstance();
    private final List<Intersection> route;

    public Route(List<Intersection> intersections) {
        validateRoute(intersections);

        this.route = List.copyOf(intersections);
    }

    public boolean canReachDestinationThroughPath(AlivePieces alivePieces, Side ownSide) {
        return isPathAvailable(alivePieces)
                && isDestinationAvailable(alivePieces, ownSide);
    }

    public boolean isDestinationAvailable(AlivePieces alivePieces, Side ownSide) {
        return getDestination().isInBoard()
                && alivePieces.placedNotSameSide(getDestination(), ownSide);
    }

    public boolean containsOnlyPalace() {
        return route.stream()
                .allMatch(palace::contains);
    }

    public List<Piece> getPiecesOnPath(AlivePieces alivePieces) {
        return getPath().stream()
                .filter(alivePieces::isNotEmpty)
                .map(alivePieces::placedAt)
                .toList();
    }

    public Intersection getDestination() {
        return route.getLast();
    }

    private List<Intersection> getPath() {
        if (route.size() <= 1) {
            return List.of();
        }

        int indexOfDestination = route.size() - 1;

        return route.subList(0, indexOfDestination);
    }

    private boolean isPathAvailable(AlivePieces alivePieces) {
        boolean pathEmpty = getPath()
                .stream()
                .allMatch(alivePieces::isEmpty);
        boolean pathInBoard = getPath()
                .stream()
                .allMatch(Intersection::isInBoard);

        return pathEmpty && pathInBoard;
    }

    private void validateRoute(List<Intersection> path) {
        if (path.isEmpty()) {
            throw new IllegalArgumentException("경로에는 하나 이상의 좌표 정보가 필요합니다.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Route route1 = (Route) o;
        return Objects.equals(route, route1.route);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(route);
    }
}
