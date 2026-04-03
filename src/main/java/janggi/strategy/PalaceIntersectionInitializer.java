package janggi.strategy;

import static janggi.domain.rule.route.Direction.*;

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

    @Override
    public void initialize(Intersection[][] intersections) {
        List<Vector> defaultVectors = createDefaultVectors(intersections);

        for (int row = 0; row < intersections.length; row++) {
            for (int col = 0; col < intersections[row].length; col++) {
                Location currentLocation = Location.of(row, col);

                // 1. Enum 에서 해당 좌표의 추가 벡터 및 궁성 여부 조회
                List<Vector> additionalVectors = PalaceConfiguration.getVectorsFor(currentLocation);
                boolean isPalace = PalaceConfiguration.isPalace(currentLocation);

                // 2. 기본 벡터와 추가 벡터 병합
                List<Vector> allVectors = new ArrayList<>(defaultVectors);
                allVectors.addAll(additionalVectors);

                // 3. 한 번의 호출로 불변 객체 생성
                intersections[row][col] = Intersection.of(allVectors, EmptyPiece.getInstance(), isPalace);
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

    private static int getLength(Intersection[][] intersections) {
        return intersections.length;
    }

    private enum PalaceConfiguration {

        SIDE(List.of(
                Location.of(9, 4),
                Location.of(8, 5),
                Location.of(0, 5),
                Location.of(9, 6),
                Location.of(2, 4),
                Location.of(1, 4),
                Location.of(3, 4),
                Location.of(2, 6)
        ), Collections.emptyList()),

        TOP_LEFT(List.of(
                Location.of(1, 4),
                Location.of(8, 4)
        ), List.of(new Vector(BACK_RIGHT, 2))),

        TOP_RIGHT(List.of(
                Location.of(1, 6),
                Location.of(8, 6)
        ), List.of(new Vector(BACK_LEFT, 2))),

        BOTTOM_LEFT(List.of(
                Location.of(0, 4),
                Location.of(3, 4)
        ), List.of(new Vector(FRONT_RIGHT, 2))),

        BOTTOM_RIGHT(List.of(
                Location.of(0, 6),
                Location.of(3, 6)
        ), List.of(new Vector(FRONT_LEFT, 2))),

        CENTER(List.of(
                Location.of(2, 5),
                Location.of(9, 5)
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
