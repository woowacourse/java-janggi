package domain.pieces;

import domain.PieceOnRoute;
import domain.Point;
import domain.Team;
import execptions.JanggiArgumentException;
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
    public boolean isAbleToArrive(final Point startPoint, final Point arrivalPoint) {
        throw new JanggiArgumentException("신하는 이동할 수 없습니다.");
    }

    @Override
    public List<Point> getRoutePoints(final Point startPoint, final Point arrivalPoint) {
        throw new JanggiArgumentException("신하는 이동할 수 없습니다.");
    }

    @Override
    public boolean isMovable(final PieceOnRoute pieceOnRoute) {
        throw new JanggiArgumentException("장군은 이동할 수 없습니다.");
    }

    @Override
    public boolean canNotJumpOver() {
        return false;
    }
}
