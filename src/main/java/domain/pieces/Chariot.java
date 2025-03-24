package domain.pieces;

import static domain.pieces.PieceNames.CHARIOT;

import domain.Team;
import domain.board.PiecesOnRoute;
import domain.board.Point;
import domain.movements.PieceMovement;
import domain.movements.StraightLineMovement;
import java.util.List;

public final class Chariot implements Piece {

    private final Team team;
    private final PieceMovement movement;

    public Chariot(final Team team) {
        this.team = team;
        this.movement = new StraightLineMovement();
    }

    @Override
    public boolean hasEqualTeam(final Team team) {
        return this.team.equals(team);
    }

    @Override
    public boolean isAbleToArrive(final Point start, final Point arrival) {
        final List<Point> arrivalPoints = movement.calculateTotalArrivalPoints(start);
        return arrivalPoints.contains(arrival);
    }

    @Override
    public boolean isMovable(final PiecesOnRoute pieces) {
        if (pieces.hasSameTeamInArrivalPoint(team)) {
            return false;
        }
        return pieces.hasNotPieceOnRoute();
    }

    @Override
    public boolean canNotJumpOver() {
        return false;
    }

    @Override
    public List<Point> getRoutePoints(final Point start, final Point arrival) {
        return movement.calculatePointsOnRoute(start, arrival);
    }

    @Override
    public String getName() {
        return CHARIOT.getNameForTeam(team);
    }
}
