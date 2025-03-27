package domain.pieces;

import static domain.pieces.PieceNames.SOLDIER;

import domain.Team;
import domain.board.PieceOnRoute;
import domain.board.BoardPoint;
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
    public boolean isAbleToArrive(final BoardPoint startBoardPoint, final BoardPoint arrivalBoardPoint) {
        return defaultMovement.calculateTotalArrivalPoints(startBoardPoint).contains(arrivalBoardPoint);
    }

    @Override
    public List<BoardPoint> getRoutePoints(final BoardPoint startBoardPoint, final BoardPoint arrivalBoardPoint) {
        return defaultMovement.calculateRoutePoints(startBoardPoint, arrivalBoardPoint);
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
