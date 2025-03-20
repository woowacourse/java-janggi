package domain.pieces;

import domain.PieceOnRoute;
import domain.Point;
import domain.Team;
import domain.movements.DefaultMovement;
import domain.movements.Direction;
import domain.movements.PieceMovement;
import domain.movements.Route;
import java.util.List;

public class Soldier implements Piece {

    private final Team team;
    private final PieceMovement defaultMovement;

    public Soldier(Team team) {
        this.team = team;
        if (team.equals(Team.HAN)) {
            defaultMovement = new DefaultMovement(List.of(
                    new Route(List.of(Direction.SOUTH)),
                    new Route(List.of(Direction.EAST)),
                    new Route(List.of(Direction.WEST))
            ));
            return;
        }
        defaultMovement = new DefaultMovement(List.of(
                new Route(List.of(Direction.NORTH)),
                new Route(List.of(Direction.EAST)),
                new Route(List.of(Direction.WEST))
        ));
    }

    @Override
    public boolean hasEqualTeam(Team team) {
        return this.team.equals(team);
    }

    @Override
    public boolean isAbleToArrive(Point startPoint, Point arrivalPoint) {
        return defaultMovement.calculateTotalArrivalPoints(startPoint).contains(arrivalPoint);
    }

    @Override
    public List<Point> getRoutePoints(Point startPoint, Point arrivalPoint) {
        return defaultMovement.calculateRoutePoints(startPoint, arrivalPoint);
    }

    @Override
    public boolean isMovable(PieceOnRoute pieceOnRoute) {
        return !pieceOnRoute.hasArrivalPointInMyTeam(team);
    }

    @Override
    public boolean canNotJumpOver() {
        return false;
    }
}
