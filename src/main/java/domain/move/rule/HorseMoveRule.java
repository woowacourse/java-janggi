package domain.move.rule;

import domain.intersection.Intersection;
import domain.move.directions.Direction;
import domain.move.directions.Directions;
import domain.move.path.Path;
import domain.piece.PieceType;
import domain.point.Point;

import java.util.List;

import static domain.move.directions.Vector.*;

public class HorseMoveRule extends MoveRule {

    public HorseMoveRule() {
        super(PieceType.HORSE, initializeDirections());
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
    public boolean checkMoveRule(Intersection from, Path path) {
        path.validateIsSameTeam(from);
        path.validateHasObstacle();
        return true;
    }

    public static Directions initializeDirections() {
        return new Directions(List.of(
                new Direction(List.of(UP, LEFT_UP)),
                new Direction(List.of(UP, RIGHT_UP)),
                new Direction(List.of(DOWN, LEFT_DOWN)),
                new Direction(List.of(DOWN, RIGHT_DOWN)),
                new Direction(List.of(LEFT, LEFT_UP)),
                new Direction(List.of(LEFT, LEFT_DOWN)),
                new Direction(List.of(RIGHT, RIGHT_UP)),
                new Direction(List.of(RIGHT, RIGHT_DOWN))
        ));
    }

}
