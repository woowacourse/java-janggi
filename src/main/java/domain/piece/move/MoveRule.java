package domain.piece.move;

import domain.intersection.Intersection;
import domain.piece.PieceType;
import domain.point.Point;
import java.util.List;

public abstract class MoveRule {

    protected final PieceType pieceType;
    protected final Directions directions;

    protected MoveRule(PieceType pieceType, Directions directions) {
        this.pieceType = pieceType;
        this.directions = directions;
    }

    public abstract boolean support(Intersection from);

    public abstract List<Point> findPossiblePoints(Intersection from, Intersection to);

    public abstract void validateMoveRule(Intersection from, List<Intersection> path);

    protected void validateIsSameTeam(Intersection from, Intersection to) {
        if (from.isSameTeam(to)) {
            throw new IllegalArgumentException("같은 팀의 위치로 이동할 수 없습니다.");
        }
    }

}
