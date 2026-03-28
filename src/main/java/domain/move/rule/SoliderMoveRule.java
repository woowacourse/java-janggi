package domain.move.rule;

import domain.intersection.Intersection;
import domain.move.directions.Direction;
import domain.move.directions.Directions;
import domain.piece.PieceType;
import domain.point.Point;

import java.util.List;

import static domain.move.directions.Vector.*;
import static domain.move.directions.Vector.LEFT;
import static domain.move.directions.Vector.RIGHT;

public class SoliderMoveRule extends MoveRule {

    public SoliderMoveRule() {
        super(PieceType.SOLDIER, initializeDirections());
    }

    @Override
    public boolean support(Intersection from) {
        return from.isSamePiece(pieceType);
    }

    @Override
    public List<Point> findPathOfPoints(Intersection from, Intersection to) {
        Directions directions = getDirections(from);
        return directions.findPoints(from.getPoint(), to.getPoint());
    }

    @Override
    public boolean checkMoveRule(Intersection from, List<Intersection> path) {
        Intersection to = path.getLast();
        validateIsSameTeam(from, to);
        return true;
    }

    private void validateIsSameTeam(Intersection from, Intersection to) {
        if (from.isSameTeam(to)) {
            throw new IllegalArgumentException("같은 팀의 위치로 이동할 수 없습니다.");
        }
    }

    public static Directions initializeDirections() {
        return new Directions(List.of(
                new Direction(List.of(DOWN)),
                new Direction(List.of(RIGHT)),
                new Direction(List.of(LEFT)))
        );
    }

    public Directions getDirections(Intersection from){
        if (from.isChoIntersection()) {
            return new Directions(List.of(
                    new Direction(List.of(UP)),
                    new Direction(List.of(RIGHT)),
                    new Direction(List.of(LEFT))));
        }
        return directions;
    }

}
