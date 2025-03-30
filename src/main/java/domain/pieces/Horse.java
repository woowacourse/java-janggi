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
import java.util.List;
import java.util.Objects;

public final class Horse implements Piece {
    private static final PieceDefinition HORSE = PieceDefinition.HORSE;
    private static final Score score = new Score(5.0);

    private final Player player;
    private final PieceMovement movement;

    public Horse(final Player player) {
        this.player = Objects.requireNonNull(player, "Team 정보가 NULL일 수 없습니다.");
        this.movement = generateMovementForHorse();
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
        if (piecesOnRoute.hasSameTeamOnArrivalPoint(player.getTeam())) {
            return false;
        }
        return piecesOnRoute.hasNotPieceOnRoute();
    }

    @Override
    public List<Point> searchRoutePoints(final Point start, final Point arrival) {
        return movement.calculatePointsOnRoute(start, arrival);
    }

    @Override
    public String getName() {
        return HORSE.getNameForTeam(player.getTeam());
    }

    @Override
    public Score getScore() {
        return score;
    }

    @Override
    public PieceDefinition getType() {
        return HORSE;
    }

    @Override
    public int getPlayerId() {
        return player.getId();
    }

    private DefaultMovement generateMovementForHorse() {
        return new DefaultMovement(List.of(
                new Route(List.of(Direction.NORTH, Direction.NORTHWEST)),
                new Route(List.of(Direction.NORTH, Direction.NORTHEAST)),
                new Route(List.of(Direction.EAST, Direction.NORTHEAST)),
                new Route(List.of(Direction.EAST, Direction.SOUTHEAST)),
                new Route(List.of(Direction.SOUTH, Direction.SOUTHEAST)),
                new Route(List.of(Direction.SOUTH, Direction.SOUTHWEST)),
                new Route(List.of(Direction.WEST, Direction.SOUTHWEST)),
                new Route(List.of(Direction.WEST, Direction.NORTHWEST))
        ));
    }
}
