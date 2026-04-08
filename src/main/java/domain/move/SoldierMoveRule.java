package domain.move;

import static domain.move.Vector.DOWN;
import static domain.move.Vector.LEFT;
import static domain.move.Vector.RIGHT;
import static domain.move.Vector.UP;

import domain.intersection.Intersection;
import domain.piece.PieceType;
import domain.point.Point;
import domain.team.Team;
import java.util.List;

public class SoldierMoveRule extends MoveRule {
    public SoldierMoveRule() {
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

    public void validateMoveRule(Intersection from, List<Intersection> path) {
        Intersection to = path.getLast();
        validateIsSameTeam(from, to);
    }

    public Directions getDirections(Intersection from) {
        if (from.isSameTeam(Team.CHO)) {
            return new Directions(List.of(
                    new Direction(List.of(UP)),
                    new Direction(List.of(RIGHT)),
                    new Direction(List.of(LEFT))));
        }
        return directions;
    }
}
