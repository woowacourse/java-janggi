package domain.pieces;

import domain.PieceOnRoute;
import domain.Point;
import domain.Team;
import execptions.JanggiArgumentException;
import java.util.List;

public class General implements Piece {

    private final Team team;

    public General(Team team) {
        this.team = team;
    }

    @Override
    public boolean hasEqualTeam(Team team) {
        return this.team.equals(team);
    }

    @Override
    public boolean isAbleToArrive(Point startPoint, Point arrivalPoint) {
        throw new JanggiArgumentException("장군은 이동할 수 없습니다.");
    }

    @Override
    public List<Point> getRoutePoints(Point startPoint, Point arrivalPoint) {
        throw new JanggiArgumentException("장군은 이동할 수 없습니다.");
    }

    @Override
    public boolean isMovable(PieceOnRoute pieceOnRoute) {
        throw new JanggiArgumentException("장군은 이동할 수 없습니다.");
    }

    @Override
    public boolean canNotJumpOver() {
        return false;
    }
}
