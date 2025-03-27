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
import java.util.List;

public final class Soldier implements Piece {

    private static final PieceType PIECE_TYPE = SOLDIER;
    private final TeamType teamType;
    private final PieceMovement movement;

    public Soldier(final TeamType teamType) {
        this.teamType = teamType;
        this.movement = getDefaultMovementByTeam(teamType);
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

    private PieceMovement getDefaultMovementByTeam(final TeamType teamType) {
        if (teamType == TeamType.HAN) {
            return new DefaultMovement(List.of(
                    new Route(List.of(Direction.SOUTH)),
                    new Route(List.of(Direction.EAST)),
                    new Route(List.of(Direction.WEST))));
        }
        return new DefaultMovement(List.of(
                new Route(List.of(Direction.NORTH)),
                new Route(List.of(Direction.EAST)),
                new Route(List.of(Direction.WEST))));
    }
}
