package janggi.support;

import static janggi.domain.rule.route.Direction.BACK;
import static janggi.domain.rule.route.Direction.BACK_LEFT;
import static janggi.domain.rule.route.Direction.BACK_RIGHT;
import static janggi.domain.rule.route.Direction.FRONT;
import static janggi.domain.rule.route.Direction.FRONT_LEFT;
import static janggi.domain.rule.route.Direction.FRONT_RIGHT;
import static janggi.domain.rule.route.Direction.LEFT;
import static janggi.domain.rule.route.Direction.RIGHT;

import janggi.domain.Intersection;
import janggi.domain.Location;
import janggi.domain.location.Vector;
import janggi.domain.piece.EmptyPiece;
import janggi.domain.piece.Piece;
import java.util.ArrayList;
import java.util.List;

public class TestIntersectionUtil {

    public static Intersection getDefaultIntersection(Location location, Piece piece) {
        return Intersection.of(location, createDefaultVectors(), piece, false);
    }

    public static Intersection getPalaceCenterIntersection(Location location, Piece piece) {
        List<Vector> vectors = new ArrayList<>(createDefaultVectors());
        vectors.addAll(createCenterVectors());
        return Intersection.of(location, vectors, piece, true);
    }

    public static Intersection getPalaceLeftTopIntersection(Location location, Piece piece) {
        List<Vector> vectors = new ArrayList<>(createDefaultVectors());
        vectors.add(new Vector(FRONT_RIGHT, 2));
        return Intersection.of(location, vectors, piece, true);
    }

    public static Intersection getDefaultEmptyPieceIntersection(Location location) {
        return Intersection.of(location, createDefaultVectors(), EmptyPiece.getInstance(), false);
    }

    public static Intersection getPalaceEmptyPieceIntersection(Location location) {
        return Intersection.of(location, createDefaultVectors(), EmptyPiece.getInstance(), true);
    }

    private static List<Vector> createDefaultVectors() {
        int defaultVectorDistance = 10;
        return List.of(
                new Vector(FRONT, defaultVectorDistance),
                new Vector(BACK, defaultVectorDistance),
                new Vector(LEFT, defaultVectorDistance),
                new Vector(RIGHT, defaultVectorDistance)
        );
    }

    private static List<Vector> createCenterVectors() {
        return List.of(
                new Vector(BACK_RIGHT, 1),
                new Vector(BACK_LEFT, 1),
                new Vector(FRONT_LEFT, 1),
                new Vector(FRONT_RIGHT, 1));
    }


}
