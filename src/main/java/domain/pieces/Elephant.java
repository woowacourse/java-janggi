package domain.pieces;

import static domain.pieces.PieceType.ELEPHANT;

import domain.board.PiecesOnRoute;
import domain.board.Point;
import domain.movements.DefaultMovement;
import domain.movements.Direction;
import domain.movements.PieceMovement;
import domain.movements.Route;
import domain.player.Score;
import domain.player.TeamType;
import java.util.List;

public final class Elephant implements Piece {

    private static final PieceType PIECE_TYPE = ELEPHANT;
    private final TeamType teamType;
    private final PieceMovement movement;

    public Elephant(final TeamType teamType) {
        this.teamType = teamType;
        this.movement = getDefaultMovementForElephant();
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
        if (piecesOnRoute.hasSameTeamOnArrivalPoint(teamType)) {
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
        return PIECE_TYPE.getNameForTeam(teamType);
    }

    @Override
    public Score getScore() {
        return PIECE_TYPE.getScore();
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
