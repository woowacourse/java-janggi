package domain.pieces;

import static domain.pieces.PieceNames.CHARIOT;

import domain.Team;
import domain.board.PieceOnRoute;
import domain.board.BoardPoint;
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
