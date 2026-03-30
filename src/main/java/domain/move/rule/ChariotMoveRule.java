package domain.move.rule;

import static domain.move.directions.Vector.DOWN;
import static domain.move.directions.Vector.LEFT;
import static domain.move.directions.Vector.RIGHT;
import static domain.move.directions.Vector.UP;

import domain.intersection.Intersection;
import domain.move.directions.Direction;
import domain.move.directions.Directions;
import domain.move.path.Path;
import domain.piece.PieceType;
import domain.point.Point;
import java.util.List;

public class ChariotMoveRule extends MoveRule {

    public ChariotMoveRule() {
        super(PieceType.CHARIOT, initializeDirections());
    }

    @Override
    public boolean support(Intersection from) {
        return from.isSamePiece(pieceType);
    }

    @Override
    public List<Point> findPathOfPoints(Intersection from, Intersection to) {
        return directions.findPoints(from, to);
    }

    @Override
    public boolean checkMoveRule(Path path) {
        path.validateIsSameTeam();
        path.validateHasObstacle();
        return true;
    }

    public static Directions initializeDirections() {
        return new Directions(List.of(
                new Direction(List.of(UP)),
                new Direction(List.of(UP, UP)),
                new Direction(List.of(UP, UP, UP)),
                new Direction(List.of(UP, UP, UP, UP)),
                new Direction(List.of(UP, UP, UP, UP, UP)),
                new Direction(List.of(UP, UP, UP, UP, UP, UP)),
                new Direction(List.of(UP, UP, UP, UP, UP, UP, UP)),
                new Direction(List.of(UP, UP, UP, UP, UP, UP, UP, UP)),
                new Direction(List.of(UP, UP, UP, UP, UP, UP, UP, UP, UP)),

                new Direction(List.of(DOWN)),
                new Direction(List.of(DOWN, DOWN)),
                new Direction(List.of(DOWN, DOWN, DOWN)),
                new Direction(List.of(DOWN, DOWN, DOWN, DOWN)),
                new Direction(List.of(DOWN, DOWN, DOWN, DOWN, DOWN)),
                new Direction(List.of(DOWN, DOWN, DOWN, DOWN, DOWN, DOWN)),
                new Direction(List.of(DOWN, DOWN, DOWN, DOWN, DOWN, DOWN, DOWN)),
                new Direction(List.of(DOWN, DOWN, DOWN, DOWN, DOWN, DOWN, DOWN, DOWN)),
                new Direction(List.of(DOWN, DOWN, DOWN, DOWN, DOWN, DOWN, DOWN, DOWN, DOWN)),

                new Direction(List.of(RIGHT)),
                new Direction(List.of(RIGHT, RIGHT)),
                new Direction(List.of(RIGHT, RIGHT, RIGHT)),
                new Direction(List.of(RIGHT, RIGHT, RIGHT, RIGHT)),
                new Direction(List.of(RIGHT, RIGHT, RIGHT, RIGHT, RIGHT)),
                new Direction(List.of(RIGHT, RIGHT, RIGHT, RIGHT, RIGHT, RIGHT)),
                new Direction(List.of(RIGHT, RIGHT, RIGHT, RIGHT, RIGHT, RIGHT, RIGHT)),
                new Direction(List.of(RIGHT, RIGHT, RIGHT, RIGHT, RIGHT, RIGHT, RIGHT, RIGHT)),

                new Direction(List.of(LEFT)),
                new Direction(List.of(LEFT, LEFT)),
                new Direction(List.of(LEFT, LEFT, LEFT)),
                new Direction(List.of(LEFT, LEFT, LEFT, LEFT)),
                new Direction(List.of(LEFT, LEFT, LEFT, LEFT, LEFT)),
                new Direction(List.of(LEFT, LEFT, LEFT, LEFT, LEFT, LEFT)),
                new Direction(List.of(LEFT, LEFT, LEFT, LEFT, LEFT, LEFT, LEFT)),
                new Direction(List.of(LEFT, LEFT, LEFT, LEFT, LEFT, LEFT, LEFT, LEFT))
        ));
    }

}
