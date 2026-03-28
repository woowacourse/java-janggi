package janggi.domain.rule.route;

import janggi.domain.Location;
import janggi.domain.piece.PieceType;
import java.util.List;

public interface RouteProvider {

    static List<Location> findValidPath(PieceType pieceType, Location from, Location to, List<Route> possibleRoutes) {
        for (Route route : possibleRoutes) {
            List<Location> locationsOnPath = route.calculateLocationsOnPath(from);
            Location expectedDestination = locationsOnPath.getLast();
            if (expectedDestination.equals(to)) {
                return locationsOnPath;
            }
        }

        throw new IllegalArgumentException(
                String.format("%s의 기물 이동 규칙 위반: 해당 위치(%d, %d)에 도달할 수 없습니다.",
                        pieceType.getNameFormat(),
                        to.x(),
                        to.y())
        );
    }

    List<Location> calculateRoute(PieceType pieceType, Location from, Location to);
}
