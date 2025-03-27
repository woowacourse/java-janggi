package domain.pieces;

import static domain.pieces.PieceType.SOLDIER;

import domain.board.PiecesOnRoute;
import domain.board.Point;
import domain.movements.DefaultMovement;
import domain.movements.Direction;
import domain.movements.PieceMovement;
import domain.movements.Route;
import domain.player.Score;
import domain.player.TeamType;
import java.util.ArrayList;
import java.util.List;

public final class Soldier implements Piece {

    private static final PieceType PIECE_TYPE = SOLDIER;

    private final TeamType teamType;
    private final PieceMovement movement;

    public Soldier(final TeamType teamType) {
        this.teamType = teamType;
        this.movement = getDefaultMovementByTeam(teamType);
    }

    public Soldier(TeamType teamType, PieceMovement movement) {
        this.teamType = teamType;
        this.movement = movement;
    }

    @Override
    public boolean hasEqualTeam(final TeamType teamType) {
        return this.teamType.equals(teamType);
    }

    @Override
    public boolean isAbleToArrive(final Point start, final Point arrival) {
        return movement.calculateTotalArrivalPoints(start).contains(arrival);
    }

    @Override
    public boolean isMovableOnRoute(final PiecesOnRoute piecesOnRoute) {
        return !piecesOnRoute.hasSameTeamOnArrivalPoint(teamType);
    }

    @Override
    public List<Point> getRoutePoints(final Point start, final Point arrival) {
        return movement.calculatePointsOnRoute(start, arrival);
    }

    @Override
    public String getName() {
        return PIECE_TYPE.getNameForTeam(teamType);
    }

    @Override
    public Score getScore() {
        return PIECE_TYPE.getScore();
    }

    @Override
    public Piece inRangeOfPalace() {
        return new Soldier(teamType, getMovementInPalaceByTeam(teamType));
    }

    private PieceMovement getDefaultMovementByTeam(final TeamType teamType) {
        return new DefaultMovement(getDefaultRoutes(teamType));
    }

    private PieceMovement getMovementInPalaceByTeam(final TeamType teamType) {
        final List<Route> routes = getDefaultRoutes(teamType);
        routes.add(new Route(List.of(Direction.NORTHEAST)));
        routes.add(new Route(List.of(Direction.NORTHWEST)));
        routes.add(new Route(List.of(Direction.SOUTHEAST)));
        routes.add(new Route(List.of(Direction.SOUTHWEST)));
        return new DefaultMovement(routes);
    }

    private List<Route> getDefaultRoutes(TeamType teamType) {
        final List<Route> routes = new ArrayList<>();
        routes.add(new Route(List.of(Direction.EAST)));
        routes.add(new Route(List.of(Direction.WEST)));
        if (teamType == TeamType.CHO) {
            routes.add(new Route(List.of(Direction.NORTH)));
            return routes;
        }
        routes.add(new Route(List.of(Direction.SOUTH)));
        return routes;
    }
}
