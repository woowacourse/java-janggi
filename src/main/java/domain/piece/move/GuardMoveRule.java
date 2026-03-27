package domain.piece.move;

import domain.intersection.Intersection;
import domain.piece.PieceType;
import domain.point.Point;

import java.util.List;

import static domain.piece.move.Vector.*;

public class GuardMoveRule extends MoveRule{

    public GuardMoveRule() {
        super(PieceType.GUARD, initializeDirections());
    }

    public boolean support(Intersection from) {
        return from.isSamePiece(pieceType);
    }

    public List<Point> findPossiblePoints(Intersection from, Intersection to) {
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

    // NOTE 사이클 1에서는 궁성이 없으므로, 상하좌우만 설정
    public static Directions initializeDirections() {
        return new Directions(List.of(
                new Direction(List.of(UP)),
                new Direction(List.of(DOWN)),
                new Direction(List.of(RIGHT)),
                new Direction(List.of(LEFT)))
        );
    }

}
