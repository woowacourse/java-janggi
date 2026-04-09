package domain.move;

import domain.intersection.Intersection;
import domain.piece.PieceType;
import domain.point.Point;
import domain.team.Team;
import java.util.List;

public abstract class MoveRule {
    protected final PieceType pieceType;

    protected MoveRule(PieceType pieceType) {
        this.pieceType = pieceType;
    }

    public abstract boolean support(Intersection from);

    public abstract List<Point> findPossiblePoints(Intersection from, Intersection to);

    protected abstract Directions makeDirections(Intersection from, Intersection to);

    public abstract void validateMoveRule(Intersection from, List<Intersection> path);

    protected void validateIsSameTeam(Intersection from, Intersection to) {
        Team toTeam = to.getTeam();
        if (from.isSameTeam(toTeam)) {
            throw new IllegalArgumentException("같은 팀의 위치로 이동할 수 없습니다.");
        }
    }
}
