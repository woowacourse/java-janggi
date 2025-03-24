package domain.pieces;

import static domain.pieces.PieceNames.CHARIOT;

import domain.Team;
import domain.board.PieceOnRoute;
import domain.board.Point;
import domain.movements.EndlessMovement;
import domain.movements.PieceMovement;
import java.util.List;

public final class Chariot implements Piece {

    private final Team team;
    private final PieceMovement movement;

    public Chariot(final Team team) {
        this.team = team;
        this.movement = new EndlessMovement();
    }

    @Override
    public boolean hasEqualTeam(final Team team) {
        return this.team.equals(team);
    }

    @Override
    public boolean isAbleToArrive(final Point startPoint, final Point arrivalPoint) {
        final List<Point> arrivalPoints = movement.calculateTotalArrivalPoints(startPoint);
        return arrivalPoints.contains(arrivalPoint);
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
    public List<Point> getRoutePoints(final Point startPoint, final Point arrivalPoint) {
        return movement.calculateRoutePoints(startPoint, arrivalPoint);
    }

    @Override
    public String getName() {
        return CHARIOT.getNameForTeam(team);
    }
}
