package domain.piece.move;

import static domain.piece.move.Vector.DOWN;
import static domain.piece.move.Vector.LEFT;
import static domain.piece.move.Vector.RIGHT;
import static domain.piece.move.Vector.UP;

import domain.intersection.Intersection;
import domain.piece.PieceType;
import domain.point.Point;
import java.util.List;

public class GeneralMoveRule extends MoveRule {

    public GeneralMoveRule() {
        super(PieceType.GENERAL, initializeDirections());
    }

    public static Directions initializeDirections() {
        return new Directions(List.of(
                new Direction(List.of(UP)),
                new Direction(List.of(DOWN)),
                new Direction(List.of(RIGHT)),
                new Direction(List.of(LEFT)))
        );
    }

    public boolean support(Intersection from) {
        return from.isSamePiece(pieceType);
    }

    public List<Point> findPossiblePoints(Intersection from, Intersection to) {
        return directions.findPoints(from.getPoint(), to.getPoint());
    }

    public void validateMoveRule(Intersection from, List<Intersection> path) {
        Intersection to = path.getLast();
        validateIsSameTeam(from, to);
    }

}
