package domain.move;

import static domain.move.Vector.DOWN;
import static domain.move.Vector.LEFT;
import static domain.move.Vector.LEFT_DOWN;
import static domain.move.Vector.LEFT_UP;
import static domain.move.Vector.RIGHT;
import static domain.move.Vector.RIGHT_DOWN;
import static domain.move.Vector.RIGHT_UP;
import static domain.move.Vector.UP;

import domain.intersection.Intersection;
import domain.piece.PieceType;
import domain.point.Point;
import domain.team.Team;
import java.util.List;

public class SoldierMoveRule extends MoveRule {
    public SoldierMoveRule() {
        super(PieceType.SOLDIER);
    }

    private Directions defaultDirection() {
        return new Directions(List.of(
                new Direction(List.of(DOWN)),
                new Direction(List.of(RIGHT)),
                new Direction(List.of(LEFT)))
        );
    }

    private Directions defaultChoDirection() {
        return new Directions(List.of(
                new Direction(List.of(UP)),
                new Direction(List.of(RIGHT)),
                new Direction(List.of(LEFT)))
        );
    }

    private Directions palaceDirection() {
        return new Directions(List.of(
                new Direction(List.of(LEFT_DOWN)),
                new Direction(List.of(RIGHT_DOWN))
        ));
    }

    private Directions palaceChoDirection() {
        return new Directions(List.of(
                new Direction(List.of(LEFT_UP)),
                new Direction(List.of(RIGHT_UP))
        ));
    }

    public boolean support(Intersection from) {
        return from.isSamePiece(pieceType);
    }

    public List<Point> findPossiblePoints(Intersection from, Intersection to) {
        Directions directions = makeDirections(from, to);
        return directions.findPoints(from.getPoint(), to.getPoint());
    }

    protected Directions makeDirections(Intersection from, Intersection to) {
        if (from.isSameTeam(Team.CHO) && from.isPalaceDiagonal() && to.isPalaceDiagonal()) {
            return defaultChoDirection().merge(palaceChoDirection());
        }
        if (from.isSameTeam(Team.CHO)) {
            return defaultChoDirection();
        }
        if (from.isPalaceDiagonal() && to.isPalaceDiagonal()) {
            return defaultDirection().merge(palaceDirection());
        }
        return defaultDirection();
    }

    public void validateMoveRule(Intersection from, List<Intersection> path) {
        Intersection to = path.getLast();
        validateIsSameTeam(from, to);
    }
}
