package domain.pieces;

import static domain.pieces.PieceNames.CHARIOT;

import domain.PieceOnRoute;
import domain.Point;
import domain.Team;
import domain.movements.PieceMovement;
import java.util.List;

public final class Chariot implements Piece {

    private final Team team;
    private final PieceMovement movements;

    public Chariot(final Team team, final PieceMovement pieceMovement) {
        this.movements = pieceMovement;
        this.team = team;
    }

    @Override
    public boolean hasEqualTeam(final Team team) {
        return this.team.equals(team);
    }

    @Override
    public boolean isAbleToArrive(final Point startPoint, final Point arrivalPoint) {
        final List<Point> arrivalPoints = movements.calculateTotalArrivalPoints(startPoint);
        return arrivalPoints.contains(arrivalPoint);
    }

    @Override
    public List<Point> getRoutePoints(final Point startPoint, final Point arrivalPoint) {
        return movements.calculateRoutePoints(startPoint, arrivalPoint);
    }

    @Override
    public boolean isMovable(final PieceOnRoute pieceOnRoute) {
        if (pieceOnRoute.hasArrivalPointInMyTeam(team)) {
            return false;
        }
        return pieceOnRoute.hasNotPieceOnRoute();
    }

    @Override
    public boolean canNotJumpOver() {
        return false;
    }


    @Override
    public String getName() {
        return CHARIOT.getNameForTeam(team);
    }
}
