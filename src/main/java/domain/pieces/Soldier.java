package domain.pieces;

import static domain.pieces.PieceNames.SOLDIER;

import domain.Team;
import domain.board.PiecesOnRoute;
import domain.board.Point;
import domain.movements.DefaultMovement;
import domain.movements.Direction;
import domain.movements.PieceMovement;
import domain.movements.Route;
import java.util.List;

public final class Soldier implements Piece {

    private final Team team;
    private final PieceMovement movement;

    public Soldier(final Team team) {
        this.team = team;
        this.movement = getDefaultMovementByTeam(team);
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
        return !piecesOnRoute.hasSameTeamOnArrivalPoint(team);
    }
    
    @Override
    public List<Point> getRoutePoints(final Point start, final Point arrival) {
        return movement.calculatePointsOnRoute(start, arrival);
    }

    @Override
    public String getName() {
        return SOLDIER.getNameForTeam(team);
    }

    private PieceMovement getDefaultMovementByTeam(final Team team) {
        if (team == Team.HAN) {
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
