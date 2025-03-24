package domain.pieces;

import static domain.pieces.PieceNames.EMPTY;

import domain.Team;
import domain.board.PieceOnRoute;
import domain.board.Point;
import execptions.JanggiGameRuleWarningException;
import java.util.List;

public final class Empty implements Piece {

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
    public boolean hasEqualTeam(final Team team) {
        throw new JanggiGameRuleWarningException("기물이 없습니다.");
    }

    @Override
    public boolean isAbleToArrive(final Point startPoint, final Point arrivalPoint) {
        throw new JanggiGameRuleWarningException("기물이 없습니다.");
    }

    @Override
    public boolean isMovable(final PieceOnRoute pieceOnRoute) {
        throw new JanggiGameRuleWarningException("기물이 없습니다.");
    }

    @Override
    public boolean canNotJumpOver() {
        return false;
    }

    @Override
    public List<Point> getRoutePoints(final Point startPoint, final Point arrivalPoint) {
        throw new JanggiGameRuleWarningException("기물이 없습니다.");
    }

    @Override
    public String getName() {
        return EMPTY.getNameForTeam(Team.NONE);
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
