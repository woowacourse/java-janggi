package domain.move.rule;

import domain.intersection.Intersection;
import domain.move.directions.Directions;
import domain.move.path.Path;
import domain.piece.PieceType;
import domain.point.Point;

import java.util.List;

public abstract class MoveRule {

    /**
     * 어떤 피스의 이동 전략인지.
     * */

    protected final PieceType pieceType;
    protected final Directions directions;

    public MoveRule(PieceType pieceType, Directions directions) {
        this.pieceType = pieceType;
        this.directions = directions;
    }

    public abstract boolean support(Intersection from);

    public abstract List<Point> findPathOfPoints(Intersection from, Intersection to);

    public abstract boolean checkMoveRule(Intersection from, Path path);

}
