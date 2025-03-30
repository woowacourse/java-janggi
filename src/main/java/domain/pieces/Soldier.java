package domain.pieces;

import domain.board.PiecesOnRoute;
import domain.board.Point;
import domain.movements.DefaultMovement;
import domain.movements.Direction;
import domain.movements.PieceMovement;
import domain.movements.Route;
import domain.player.Player;
import domain.player.Score;
import domain.player.Team;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Soldier implements Piece {
    private static final PieceDefinition SOLDIER = PieceDefinition.SOLDIER;
    private static final Score score = new Score(2.0);

    private final Player player;
    private final PieceMovement movement;

    public Soldier(final Player player) {
        this.player = Objects.requireNonNull(player, "Team 정보가 NULL일 수 없습니다.");
        this.movement = getDefaultMovementByTeam(player.getTeam());
    }

    public Soldier(final Player player, final PieceMovement movement) {
        this.player = player;
        this.movement = movement;
    }

    @Override
    public boolean hasEqualTeam(final Team team) {
        return this.player.getTeam().equals(team);
    }

    @Override
    public boolean isAbleToArrive(final Point start, final Point arrival) {
        return movement.searchTotalArrivalPoints(start).contains(arrival);
    }

    @Override
    public boolean isMovableOnRoute(final PiecesOnRoute piecesOnRoute) {
        return !piecesOnRoute.hasSameTeamOnArrivalPoint(player.getTeam());
    }

    @Override
    public List<Point> searchRoutePoints(final Point start, final Point arrival) {
        return movement.calculatePointsOnRoute(start, arrival);
    }

    @Override
    public String getName() {
        return SOLDIER.getNameForTeam(player.getTeam());
    }

    @Override
    public Score getScore() {
        return score;
    }

    @Override
    public PieceDefinition getType() {
        return SOLDIER;
    }

    @Override
    public int getPlayerId() {
        return player.getId();
    }

    @Override
    public Piece inRangeOfPalace() {
        return new Soldier(player, generateMovementInPalaceByTeam(player.getTeam()));
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
