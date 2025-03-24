package domain.pieces;

import static domain.pieces.PieceNames.EMPTY;

import domain.Team;
import domain.board.PiecesOnRoute;
import domain.board.Point;
import execptions.JanggiArgumentException;
import execptions.JanggiGameRuleWarningException;
import java.util.List;

public final class Empty implements Piece {
    private static final Team team = Team.NONE;
    private static Empty empty = null;

    private Empty() {
    }

    public static Empty getInstance() {
        if (empty == null) {
            empty = new Empty();
        }
        return empty;
    }

    @Override
    public boolean hasEqualTeam(final Team other) {
        return team == other;
    }

    @Override
    public boolean isAbleToArrive(final Point start, final Point arrival) {
        throw new JanggiGameRuleWarningException("이동시킬 기물이 없습니다: " + start);
    }

    @Override
    public boolean isMovable(final PiecesOnRoute pieces) {
        throw new JanggiArgumentException("잘못된 요청입니다: " + this.getClass());
    }

    @Override
    public boolean canNotJumpOver() {
        return false;
    }

    @Override
    public List<Point> getRoutePoints(final Point start, final Point arrival) {
        throw new JanggiGameRuleWarningException("이동시킬 기물이 없습니다: " + start);
    }

    @Override
    public String getName() {
        return EMPTY.getNameForTeam(team);
    }

    @Override
    public boolean equals(final Object obj) {
        return obj instanceof Empty;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }


}
