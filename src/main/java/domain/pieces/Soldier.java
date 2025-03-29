package domain.pieces;

import domain.board.PiecesOnRoute;
import domain.board.Point;
import domain.movements.DefaultMovement;
import domain.movements.Direction;
import domain.movements.PieceMovement;
import domain.movements.Route;
import domain.player.Score;
import domain.player.Team;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Soldier implements Piece {

    private static final Score score = new Score(2.0);

    private final Team team;
    private final PieceMovement movement;

    public Soldier(final Team team) {
        this.team = Objects.requireNonNull(team, "Team 정보가 NULL일 수 없습니다.");
        this.movement = getDefaultMovementByTeam(team);
    }

    public Soldier(Team team, PieceMovement movement) {
        this.team = team;
        this.movement = movement;
    }

    @Override
    public boolean hasEqualTeam(final Team team) {
        return this.team.equals(team);
    }

    @Override
    public boolean isAbleToArrive(final Point start, final Point arrival) {
        return movement.searchTotalArrivalPoints(start).contains(arrival);
    }

    @Override
    public boolean isMovableOnRoute(final PiecesOnRoute piecesOnRoute) {
        return !piecesOnRoute.hasSameTeamOnArrivalPoint(team);
    }

    @Override
    public List<Point> searchRoutePoints(final Point start, final Point arrival) {
        return movement.calculatePointsOnRoute(start, arrival);
    }

    @Override
    public String getName() {
        return PieceName.SOLDIER.getNameForTeam(team);
    }

    @Override
    public Score getScore() {
        return score;
    }

    @Override
    public Piece inRangeOfPalace() {
        return new Soldier(team, generateMovementInPalaceByTeam(team));
    }

    private PieceMovement getDefaultMovementByTeam(final Team team) {
        return new DefaultMovement(generateDefaultRoutes(team));
    }

    private PieceMovement generateMovementInPalaceByTeam(final Team team) {
        final List<Route> routes = generateDefaultRoutes(team);
        routes.add(new Route(List.of(Direction.NORTHEAST)));
        routes.add(new Route(List.of(Direction.NORTHWEST)));
        routes.add(new Route(List.of(Direction.SOUTHEAST)));
        routes.add(new Route(List.of(Direction.SOUTHWEST)));
        return new DefaultMovement(routes);
    }

    private List<Route> generateDefaultRoutes(Team team) {
        final List<Route> routes = new ArrayList<>();
        routes.add(new Route(List.of(Direction.EAST)));
        routes.add(new Route(List.of(Direction.WEST)));
        if (team == Team.CHO) {
            routes.add(new Route(List.of(Direction.NORTH)));
            return routes;
        }
        routes.add(new Route(List.of(Direction.SOUTH)));
        return routes;
    }
}
