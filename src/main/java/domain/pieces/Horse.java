package domain.pieces;

import domain.board.PiecesOnRoute;
import domain.board.Point;
import domain.movements.DefaultMovement;
import domain.movements.Direction;
import domain.movements.PieceMovement;
import domain.movements.Route;
import domain.player.Score;
import domain.player.TeamType;
import java.util.List;

public final class Horse implements Piece {

    private static final Score score = new Score(5.0);

    private final TeamType teamType;
    private final PieceMovement movement;

    public Horse(final TeamType teamType) {
        this.teamType = teamType;
        this.movement = generateMovementForHorse();
    }

    @Override
    public boolean hasEqualTeam(final TeamType teamType) {
        return this.teamType.equals(teamType);
    }

    @Override
    public boolean isAbleToArrive(final Point start, final Point arrival) {
        return movement.searchTotalArrivalPoints(start).contains(arrival);
    }

    @Override
    public boolean isMovableOnRoute(final PiecesOnRoute piecesOnRoute) {
        if (piecesOnRoute.hasSameTeamOnArrivalPoint(teamType)) {
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
        return PieceName.HORSE.getNameForTeam(teamType);
    }

    @Override
    public Score getScore() {
        return score;
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
