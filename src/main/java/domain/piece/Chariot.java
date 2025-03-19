package domain.piece;

import domain.Point;
import domain.Team;
import java.util.Collections;
import java.util.List;

public class Chariot implements Piece {

    private final Team team;
    private final List<Route> movements;

    public Chariot(Team team) {
        movements = List.of(
                new Route(Collections.nCopies(10, Direction.NORTH)),
                new Route(Collections.nCopies(10, Direction.EAST)),
                new Route(Collections.nCopies(10, Direction.SOUTH)),
                new Route(Collections.nCopies(10, Direction.WEST))
        );
        this.team = team;
    }

    @Override
    public boolean hasEqualTeam(Team team) {
        return this.team.equals(team);
    }

    @Override
    public boolean isAbleToArrive(Point startPoint, Point arrivalPoint) {
        for (Route route : movements) {
            List<Point> pointsOnRoute = route.getAllPointsOnRoute(startPoint);
            if (pointsOnRoute.contains(arrivalPoint)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Point> getRoutePoints(Point startPoint, Point arrivalPoint) {
        for (Route route : movements) {
            List<Point> pointsOnRoute = route.getAllPointsOnRoute(startPoint);
            if (pointsOnRoute.contains(arrivalPoint)) {
                return pointsOnRoute.subList(0, pointsOnRoute.indexOf(arrivalPoint) + 1);
            }
        }
        throw new IllegalArgumentException("해당 위치로 이동할 수 없습니다.");
    }

    @Override
    public boolean isMovable(PieceOnRoute pieceOnRoute) {
        if (pieceOnRoute.hasArrivalPointInMyTeam(team)) {
            return false;
        }
        return pieceOnRoute.hasNotPieceOnRoute();
    }
}
