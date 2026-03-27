package domain.piece.move;

import static domain.piece.move.Vector.DOWN;
import static domain.piece.move.Vector.LEFT;
import static domain.piece.move.Vector.RIGHT;
import static domain.piece.move.Vector.UP;

import domain.intersection.Intersection;
import domain.piece.PieceType;
import domain.point.Point;
import java.util.List;

public class SoliderMoveRule extends MoveRule {

    public SoliderMoveRule() {
        super(PieceType.SOLDIER, initializeDirections());
    }

    public static Directions initializeDirections() {
        return new Directions(List.of(
                new Direction(List.of(DOWN)),
                new Direction(List.of(RIGHT)),
                new Direction(List.of(LEFT)))
        );
    }

    public boolean support(Intersection from) {
        return from.isSamePiece(pieceType);
    }

    public List<Point> findPossiblePoints(Intersection from, Intersection to) {
        Directions directions = getDirections(from);
        return directions.findPoints(from.getPoint(), to.getPoint());
    }

    public boolean checkMoveRule(Intersection from, List<Intersection> path) {
        Intersection to = path.getLast();
        validateIsSameTeam(from, to); // 도착지가 같은 팀인지 확인
        return true;
    }

    private void validateIsSameTeam(Intersection from, Intersection to) {
        if (from.isSameTeam(to)) {
            throw new IllegalArgumentException("같은 팀의 위치로 이동할 수 없습니다.");
        }
    }

    public Directions getDirections(Intersection from) {
        if (from.isChoIntersection()) {
            return new Directions(List.of(
                    new Direction(List.of(UP)),
                    new Direction(List.of(RIGHT)),
                    new Direction(List.of(LEFT))));
        }
        return directions;
    }

}
