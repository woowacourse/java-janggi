package domain.pieces;

import static domain.pieces.PieceNames.GENERAL;

import domain.Team;
import domain.board.PiecesOnRoute;
import domain.board.Point;
import exceptions.JanggiGameRuleWarningException;
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
    public boolean isAbleToArrive(final Point start, final Point arrival) {
        throw new JanggiGameRuleWarningException("궁은 이동할 수 없습니다.");
    }

    @Override
    public boolean isMovableOnRoute(final PiecesOnRoute piecesOnRoute) {
        throw new JanggiGameRuleWarningException("궁은 이동할 수 없습니다.");
    }

    @Override
    public List<Point> getRoutePoints(final Point start, final Point arrival) {
        throw new JanggiGameRuleWarningException("궁은 이동할 수 없습니다.");
    }

    @Override
    public String getName() {
        return GENERAL.getNameForTeam(team);
    }

    @Override
    public boolean canContinueWhenPieceRemove() {
        return false;
    }
}
