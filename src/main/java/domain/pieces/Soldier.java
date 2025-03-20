package domain.pieces;

import static domain.pieces.PieceNames.SOLDIER;

import domain.PieceOnRoute;
import domain.Point;
import domain.Team;
import domain.movements.PieceMovement;
import java.util.List;

public final class Soldier implements Piece {


    private final Team team;
    private final PieceMovement defaultMovement;

    public Soldier(Team team, PieceMovement defaultMovement) {
        this.team = team;
        this.defaultMovement = defaultMovement;
    }

    @Override
    public boolean hasEqualTeam(final Team team) {
        return this.team.equals(team);
    }

    @Override
    public boolean isAbleToArrive(final Point startPoint, final Point arrivalPoint) {
        return defaultMovement.calculateTotalArrivalPoints(startPoint).contains(arrivalPoint);
    }

    @Override
    public List<Point> getRoutePoints(final Point startPoint, final Point arrivalPoint) {
        return defaultMovement.calculateRoutePoints(startPoint, arrivalPoint);
    }

    @Override
    public boolean isMovable(final PieceOnRoute pieceOnRoute) {
        return !pieceOnRoute.hasArrivalPointInMyTeam(team);
    }

    @Override
    public boolean canNotJumpOver() {
        return false;
    }

    @Override
    public String getName() {
        return SOLDIER.getNameForTeam(team);
    }
}
