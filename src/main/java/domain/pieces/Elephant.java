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

public final class Elephant implements Piece {
    private static final PieceDefinition ELEPHANT = PieceDefinition.ELEPHANT;
    private static final Score score = new Score(7.0);

    private final Player player;
    private final PieceMovement movement;

    public Elephant(final Player player) {
        this.player = Objects.requireNonNull(player, "Team 정보가 NULL일 수 없습니다.");
        this.movement = generateMovementForElephant();
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
        return ELEPHANT.getNameForTeam(player.getTeam());
    }

    @Override
    public Score getScore() {
        return score;
    }

    @Override
    public PieceDefinition getType() {
        return ELEPHANT;
    }

    @Override
    public int getPlayerId() {
        return player.getId();
    }

    private DefaultMovement generateMovementForElephant() {
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
