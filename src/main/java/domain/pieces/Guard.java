package domain.pieces;

import static domain.pieces.PieceNames.GUARD;

import domain.Team;
import domain.board.PiecesOnRoute;
import domain.board.Point;
import exceptions.JanggiGameRuleWarningException;
import java.util.List;

public final class Guard implements Piece {

    private final Team team;

    public Guard(final Team team) {
        this.team = team;
    }

    @Override
    public boolean hasEqualTeam(final Team team) {
        return this.team.equals(team);
    }

    @Override
    public boolean isAbleToArrive(final Point start, final Point arrival) {
        throw new JanggiGameRuleWarningException("신하는 이동할 수 없습니다.");
    }

    @Override
    public boolean isMovableOnRoute(final PiecesOnRoute piecesOnRoute) {
        throw new JanggiGameRuleWarningException("신하는 이동할 수 없습니다.");
    }

    @Override
    public List<Point> getRoutePoints(final Point start, final Point arrival) {
        throw new JanggiGameRuleWarningException("신하는 이동할 수 없습니다.");
    }

    @Override
    public String getName() {
        return GUARD.getNameForTeam(team);
    }
}
