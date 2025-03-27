package domain.pieces;

import static domain.pieces.PieceType.GUARD;

import domain.board.PiecesOnRoute;
import domain.board.Point;
import domain.player.Score;
import domain.player.TeamType;
import exceptions.JanggiGameRuleWarningException;
import java.util.List;

public final class Guard implements Piece {

    private static final PieceType PIECE_TYPE = GUARD;
    private final TeamType teamType;

    public Guard(final TeamType teamType) {
        this.teamType = teamType;
    }

    @Override
    public boolean hasEqualTeam(final TeamType teamType) {
        return this.teamType.equals(teamType);
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
        return PIECE_TYPE.getNameForTeam(teamType);
    }

    @Override
    public Score getScore() {
        return PIECE_TYPE.getScore();
    }
}
