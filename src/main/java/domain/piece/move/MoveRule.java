package domain.piece.move;

import domain.intersection.Intersection;
import domain.piece.PieceType;
import domain.point.Point;
import java.util.List;

public abstract class MoveRule {

    protected final PieceType pieceType;
    protected final Directions directions;

    public MoveRule(PieceType pieceType, Directions directions) {
        this.pieceType = pieceType;
        this.directions = directions;
    }

    public abstract boolean support(Intersection from);

    public abstract List<Point> findPossiblePoints(Intersection from, Intersection to);

    public abstract boolean checkMoveRule(Intersection from, List<Intersection> path);

}
