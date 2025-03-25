package domain.pieces;

import static domain.pieces.PieceNames.CANNON;

import domain.Team;
import domain.board.PiecesOnRoute;
import domain.board.Point;
import domain.movements.PieceMovement;
import domain.movements.StraightLineMovement;
import java.util.List;

public final class Cannon implements Piece {

    private static final int VALID_BETWEEN_PIECE_COUNT = 1;

    private final Team team;
    private final PieceMovement movement;

    public Cannon(final Team team) {
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
    public boolean isMovableOnRoute(final PiecesOnRoute piecesOnRoute) {
        if (piecesOnRoute.count() != VALID_BETWEEN_PIECE_COUNT) {
            return false;
        }
        if (piecesOnRoute.canNotJumpOverFirstPiece()) {
            return false;
        }
        return !piecesOnRoute.hasSameTeamOnArrivalPoint(team);
    }

    @Override
    public boolean canNotJumpOver() {
        return true;
    }

    @Override
    public List<Point> getRoutePoints(final Point start, final Point arrival) {
        return movement.calculatePointsOnRoute(start, arrival);
    }

    @Override
    public String getName() {
        return CANNON.getNameForTeam(team);
    }

}
