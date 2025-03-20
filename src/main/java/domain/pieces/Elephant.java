package domain.pieces;

import domain.PieceOnRoute;
import domain.Point;
import domain.Team;
import domain.movements.DefaultMovement;
import domain.movements.Direction;
import domain.movements.PieceMovement;
import domain.movements.Route;
import java.util.List;

public final class Elephant implements Piece {

    private final Team team;
    private final PieceMovement defaultMovement;


    public Elephant(final Team team) {
        List<Route> routes = List.of(
                new Route(List.of(Direction.NORTH, Direction.NORTHWEST, Direction.NORTHWEST)),
                new Route(List.of(Direction.NORTH, Direction.NORTHEAST, Direction.NORTHEAST)),
                new Route(List.of(Direction.EAST, Direction.NORTHEAST, Direction.NORTHEAST)),
                new Route(List.of(Direction.EAST, Direction.SOUTHEAST, Direction.SOUTHEAST)),
                new Route(List.of(Direction.SOUTH, Direction.SOUTHEAST, Direction.SOUTHEAST)),
                new Route(List.of(Direction.SOUTH, Direction.SOUTHWEST, Direction.SOUTHWEST)),
                new Route(List.of(Direction.WEST, Direction.SOUTHWEST, Direction.SOUTHWEST)),
                new Route(List.of(Direction.WEST, Direction.NORTHWEST, Direction.NORTHWEST))
        );

        this.defaultMovement = new DefaultMovement(routes);
        this.team = team;
    }

    public Elephant(Team team, PieceMovement defaultMovement) {
        this.defaultMovement = defaultMovement;
        this.team = team;
    }


    @Override
    public boolean hasEqualTeam(final Team team) {
        return this.team.equals(team);
    }

    @Override
    public boolean isAbleToArrive(final Point startPoint, final Point arrivalPoint) {
        return defaultMovement.calculateTotalArrivalPoints(startPoint).contains(arrivalPoint);
    }

    @Override
    public List<Point> getRoutePoints(final Point startPoint, final Point arrivalPoint) {
        return defaultMovement.calculateRoutePoints(startPoint, arrivalPoint);
    }

    @Override
    public boolean isMovable(final PieceOnRoute pieceOnRoute) {
        if (pieceOnRoute.hasArrivalPointInMyTeam(team)) {
            return false;
        }
        return pieceOnRoute.hasNotPieceOnRoute();
    }

    @Override
    public boolean canNotJumpOver() {
        return false;
    }
}
