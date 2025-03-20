package domain.pieces;

import domain.PieceOnRoute;
import domain.Point;
import domain.Team;
import domain.movements.EndlessMovement;
import domain.movements.PieceMovement;
import java.util.List;

public class Cannon implements Piece {

    private static final int VALID_BETWEEN_PIECE_COUNT = 1;
    private final Team team;
    private final PieceMovement movements;

    public Cannon(Team team) {
        this.movements = new EndlessMovement();
        this.team = team;
    }

    public Cannon(Team team, PieceMovement pieceMovement) {
        this.movements = pieceMovement;
        this.team = team;
    }

    @Override
    public boolean hasEqualTeam(Team team) {
        return this.team.equals(team);
    }

    @Override
    public boolean isAbleToArrive(Point startPoint, Point arrivalPoint) {
        List<Point> arrivalPoints = movements.calculateTotalArrivalPoints(startPoint);
        return arrivalPoints.contains(arrivalPoint);
    }

    @Override
    public List<Point> getRoutePoints(Point startPoint, Point arrivalPoint) {
        return movements.calculateRoutePoints(startPoint, arrivalPoint);
    }

    @Override
    public boolean isMovable(PieceOnRoute pieceOnRoute) {
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

}
