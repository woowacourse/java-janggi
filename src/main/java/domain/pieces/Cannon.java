package domain.pieces;

import static domain.pieces.PieceNames.CANNON;

import domain.Team;
import domain.board.PieceOnRoute;
import domain.board.Point;
import domain.movements.EndlessMovement;
import domain.movements.PieceMovement;
import java.util.List;

public final class Cannon implements Piece {

    private static final int VALID_BETWEEN_PIECE_COUNT = 1;

    private final Team team;
    private final PieceMovement movement;

    public Cannon(final Team team) {
        this.team = team;
        this.movement = new EndlessMovement();
    }

    public Cannon(final Team team, final PieceMovement movement) {
        this.team = team;
        this.movement = movement;
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
        if (pieceOnRoute.countPieceOnRoute() != VALID_BETWEEN_PIECE_COUNT) {
            return false;
        }
        if (pieceOnRoute.canNotJumpOverFirstPiece()) {
            return false;
        }
        return !pieceOnRoute.hasArrivalPointInMyTeam(team);
    }

    @Override
    public boolean canNotJumpOver() {
        return true;
    }

    @Override
    public List<Point> getRoutePoints(final Point startPoint, final Point arrivalPoint) {
        return movement.calculateRoutePoints(startPoint, arrivalPoint);
    }

    @Override
    public String getName() {
        return CANNON.getNameForTeam(team);
    }

}
