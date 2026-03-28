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
                String.format("%s은(는) 해당 위치(%s)에 도달할 수 없습니다.", pieceType.getNameFormat(), to)
        );
    }

    List<Location> calculateRoute(PieceType pieceType, Location from, Location to);
}
