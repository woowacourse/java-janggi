package domain.pieces;

import domain.Team;
import domain.board.PieceOnRoute;
import domain.board.BoardPoint;
import domain.movements.PieceMovement;
import static domain.pieces.PieceNames.CANNON;
import java.util.List;

public final class Cannon implements Piece {

    private static final int VALID_BETWEEN_PIECE_COUNT = 1;
    private final Team team;
    private final PieceMovement movements;

    public Cannon(final Team team, final PieceMovement pieceMovement) {
        this.movements = pieceMovement;
        this.team = team;
    }

    @Override
    public boolean hasEqualTeam(final Team team) {
        return this.team.equals(team);
    }

    @Override
    public boolean isAbleToArrive(final BoardPoint startBoardPoint, final BoardPoint arrivalBoardPoint) {
        final List<BoardPoint> arrivalBoardPoints = movements.calculateTotalArrivalPoints(startBoardPoint);
        return arrivalBoardPoints.contains(arrivalBoardPoint);
    }

    @Override
    public List<BoardPoint> getRoutePoints(final BoardPoint startBoardPoint, final BoardPoint arrivalBoardPoint) {
        return movements.calculateRoutePoints(startBoardPoint, arrivalBoardPoint);
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
    public String getName() {
        return CANNON.getNameForTeam(team);
    }

}
