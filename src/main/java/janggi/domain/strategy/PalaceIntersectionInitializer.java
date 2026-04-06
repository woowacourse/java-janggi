package janggi.domain.strategy;

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
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PalaceIntersectionInitializer implements IntersectionInitializer {

    private static int getLength(Intersection[][] intersections) {
        return intersections.length;
    }

    @Override
    public void initialize(Intersection[][] intersections) {
        List<Vector> defaultVectors = createDefaultVectors(intersections);

        for (int row = 0; row < intersections.length; row++) {
            for (int col = 0; col < intersections[row].length; col++) {
                Location currentLocation = Location.of(row, col);

                List<Vector> additionalVectors = PalaceConfiguration.getVectorsFor(currentLocation);
                List<Vector> allVectors = new ArrayList<>(defaultVectors);
                allVectors.addAll(additionalVectors);

                boolean isPalace = PalaceConfiguration.isPalace(currentLocation);

                intersections[row][col] = Intersection.of(Location.of(row, col), allVectors, EmptyPiece.getInstance(),
                        isPalace);
            }
        }
    }

    private List<Vector> createDefaultVectors(Intersection[][] intersections) {
        int boardHeight = getLength(intersections);
        int boardWidth = intersections[0].length;

        return List.of(
                new Vector(FRONT, boardHeight),
                new Vector(BACK, boardHeight),
                new Vector(LEFT, boardWidth),
                new Vector(RIGHT, boardWidth)
        );
    }

    private enum PalaceConfiguration {

        SIDE(List.of(
                Location.of(8, 3),
                Location.of(7, 4),
                Location.of(9, 4),
                Location.of(8, 5),
                Location.of(1, 3),
                Location.of(0, 3),
                Location.of(2, 3),
                Location.of(1, 5)
        ), Collections.emptyList()),

        TOP_LEFT(List.of(
                Location.of(0, 3),
                Location.of(7, 3)
        ), List.of(new Vector(BACK_RIGHT, 2))),

        TOP_RIGHT(List.of(
                Location.of(0, 5),
                Location.of(7, 5)
        ), List.of(new Vector(BACK_LEFT, 2))),

        BOTTOM_LEFT(List.of(
                Location.of(9, 3),
                Location.of(2, 3)
        ), List.of(new Vector(FRONT_RIGHT, 2))),

        BOTTOM_RIGHT(List.of(
                Location.of(9, 5),
                Location.of(2, 5)
        ), List.of(new Vector(FRONT_LEFT, 2))),

        CENTER(List.of(
                Location.of(1, 4),
                Location.of(8, 4)
        ), List.of(
                new Vector(BACK_RIGHT, 1),
                new Vector(BACK_LEFT, 1),
                new Vector(FRONT_LEFT, 1),
                new Vector(FRONT_RIGHT, 1))
        );

        private static final Map<Location, List<Vector>> PALACE_VECTORS = new HashMap<>();

        static {
            for (PalaceConfiguration configuration : values()) {
                for (Location location : configuration.locations) {
                    PALACE_VECTORS.computeIfAbsent(location, l -> new ArrayList<>())
                            .addAll(configuration.additionalVectors);
                }
            }
        }

        private final List<Location> locations;
        private final List<Vector> additionalVectors;

        PalaceConfiguration(List<Location> locations, List<Vector> additionalVectors) {
            this.locations = locations;
            this.additionalVectors = additionalVectors;
        }

        public static List<Vector> getVectorsFor(Location location) {
            return PALACE_VECTORS.getOrDefault(location, Collections.emptyList());
        }

        public static boolean isPalace(Location location) {
            return PALACE_VECTORS.containsKey(location);
        }
    }
}
