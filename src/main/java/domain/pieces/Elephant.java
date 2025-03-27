package domain.pieces;

import domain.Team;
import domain.board.PieceOnRoute;
import domain.board.BoardPoint;
import domain.movements.PieceMovement;
import static domain.pieces.PieceNames.ELEPHANT;
import java.util.List;

public final class Elephant implements Piece {

    private final Team team;
    private final PieceMovement defaultMovement;

    public Elephant(final Team team, final PieceMovement defaultMovement) {
        this.defaultMovement = defaultMovement;
        this.team = team;
    }

    @Override
    public boolean hasEqualTeam(final Team team) {
        return this.team.equals(team);
    }

    @Override
    public boolean isAbleToArrive(final BoardPoint startBoardPoint, final BoardPoint arrivalBoardPoint) {
        return defaultMovement.calculateTotalArrivalPoints(startBoardPoint).contains(arrivalBoardPoint);
    }

    @Override
    public List<BoardPoint> getRoutePoints(final BoardPoint startBoardPoint, final BoardPoint arrivalBoardPoint) {
        return defaultMovement.calculateRoutePoints(startBoardPoint, arrivalBoardPoint);
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
        return ELEPHANT.getNameForTeam(team);
    }
}
