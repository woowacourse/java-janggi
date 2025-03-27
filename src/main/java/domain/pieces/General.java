package domain.pieces;

import static domain.pieces.PieceNames.GENERAL;

import domain.Team;
import domain.board.PieceOnRoute;
import domain.board.BoardPoint;
import execptions.JanggiArgumentException;
import java.util.List;

public final class General implements Piece {

    private final Team team;

    public General(final Team team) {
        this.team = team;
    }

    @Override
    public boolean hasEqualTeam(final Team team) {
        return this.team.equals(team);
    }

    @Override
    public boolean isAbleToArrive(final BoardPoint startBoardPoint, final BoardPoint arrivalBoardPoint) {
        throw new JanggiArgumentException("장군은 이동할 수 없습니다.");
    }

    @Override
    public List<BoardPoint> getRoutePoints(final BoardPoint startBoardPoint, final BoardPoint arrivalBoardPoint) {
        throw new JanggiArgumentException("장군은 이동할 수 없습니다.");
    }

    @Override
    public boolean isMovable(final PieceOnRoute pieceOnRoute) {
        throw new JanggiArgumentException("장군은 이동할 수 없습니다.");
    }

    @Override
    public boolean canNotJumpOver() {
        return false;
    }

    @Override
    public String getName() {
        return GENERAL.getNameForTeam(team);
    }
}
