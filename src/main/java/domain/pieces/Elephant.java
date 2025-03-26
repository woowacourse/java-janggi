package domain.pieces;

import static domain.pieces.PieceNames.ELEPHANT;

import domain.Team;
import domain.board.PiecesOnRoute;
import domain.board.Point;
import domain.movements.DefaultMovement;
import domain.movements.Direction;
import domain.movements.PieceMovement;
import domain.movements.Route;
import java.util.List;

public final class Elephant implements Piece {

    private final Team team;
    private final PieceMovement movement;

    public Elephant(final Team team) {
        this.team = team;
        this.movement = getDefaultMovementForElephant();
    }

    @Override
    public boolean hasEqualTeam(final Team team) {
        return this.team.equals(team);
    }

    @Override
    public boolean isAbleToArrive(final Point start, final Point arrival) {
        return movement.calculateTotalArrivalPoints(start).contains(arrival);
    }

    @Override
    public boolean isMovableOnRoute(final PiecesOnRoute piecesOnRoute) {
        if (piecesOnRoute.hasSameTeamOnArrivalPoint(team)) {
            return false;
        }
        return piecesOnRoute.hasNotPieceOnRoute();
    }

    @Override
    public List<Point> getRoutePoints(final Point start, final Point arrival) {
        return movement.calculatePointsOnRoute(start, arrival);
    }

    @Override
    public String getName() {
        return ELEPHANT.getNameForTeam(team);
    }

    private DefaultMovement getDefaultMovementForElephant() {
        return new DefaultMovement(List.of(
                new Route(List.of(Direction.NORTH, Direction.NORTHWEST, Direction.NORTHWEST)),
                new Route(List.of(Direction.NORTH, Direction.NORTHEAST, Direction.NORTHEAST)),
                new Route(List.of(Direction.EAST, Direction.NORTHEAST, Direction.NORTHEAST)),
                new Route(List.of(Direction.EAST, Direction.SOUTHEAST, Direction.SOUTHEAST)),
                new Route(List.of(Direction.SOUTH, Direction.SOUTHEAST, Direction.SOUTHEAST)),
                new Route(List.of(Direction.SOUTH, Direction.SOUTHWEST, Direction.SOUTHWEST)),
                new Route(List.of(Direction.WEST, Direction.SOUTHWEST, Direction.SOUTHWEST)),
                new Route(List.of(Direction.WEST, Direction.NORTHWEST, Direction.NORTHWEST))
        ));
    }
}
