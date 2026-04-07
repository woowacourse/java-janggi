package domain.pathgenerator;

import static domain.TestUtil.createPosition;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import domain.position.Path;
import domain.position.Position;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class GungsungDiagonalPathGeneratorTest {

    @ParameterizedTest
    @MethodSource("distanceOneDiagonalPositions")
    void 거리가_1인_궁성_안의_대각선_위치에_대해_Path를_생성한다(Position source, Position destination) {
        GungsungDiagonalPathGenerator gungsungDiagonalPathGenerator = new GungsungDiagonalPathGenerator();

        Path path = gungsungDiagonalPathGenerator.calculatePath(source, destination).get();

        assertEquals(source, path.source());
        assertEquals(destination, path.destination());
        assertEquals(0, path.waypoints().size());
    }

    @ParameterizedTest
    @MethodSource("distanceTwoDiagonalPositions")
    void 거리가_2인_궁성_안의_대각선_위치에_대해_Path를_생성한다(Position source, Position destination) {
        GungsungDiagonalPathGenerator gungsungDiagonalPathGenerator = new GungsungDiagonalPathGenerator();

        Path path = gungsungDiagonalPathGenerator.calculatePath(source, destination).get();

        assertEquals(source, path.source());
        assertEquals(destination, path.destination());
        List<Position> waypoints = path.waypoints();
        assertEquals(1, waypoints.size());
        Position middlePosition = new Position(
                (source.row() + destination.row()) / 2,
                (source.column() + destination.column()) / 2
        );
        assertEquals(middlePosition, waypoints.getFirst());
    }

    @ParameterizedTest
    @MethodSource("impossibleDiagonalPositions")
    void 이동할_수_없는_위치를_입력하면_빈_Optional을_반환한다(Position source, Position destination) {
        GungsungDiagonalPathGenerator gungsungDiagonalPathGenerator = new GungsungDiagonalPathGenerator();

        assertTrue(gungsungDiagonalPathGenerator.calculatePath(source, destination).isEmpty());
    }

    private static Stream<Arguments> distanceOneDiagonalPositions() {
        return Stream.of(
                Arguments.of(createPosition(7, 3), createPosition(8, 4)),
                Arguments.of(createPosition(7, 5), createPosition(8, 4)),
                Arguments.of(createPosition(8, 4), createPosition(7, 3)),
                Arguments.of(createPosition(8, 4), createPosition(7, 5)),
                Arguments.of(createPosition(8, 4), createPosition(9, 3)),
                Arguments.of(createPosition(8, 4), createPosition(9, 5)),
                Arguments.of(createPosition(9, 3), createPosition(8, 4)),
                Arguments.of(createPosition(9, 5), createPosition(8, 4)),
                Arguments.of(createPosition(0, 3), createPosition(1, 4)),
                Arguments.of(createPosition(0, 5), createPosition(1, 4)),
                Arguments.of(createPosition(1, 4), createPosition(0, 3)),
                Arguments.of(createPosition(1, 4), createPosition(0, 5)),
                Arguments.of(createPosition(1, 4), createPosition(2, 3)),
                Arguments.of(createPosition(1, 4), createPosition(2, 5)),
                Arguments.of(createPosition(2, 3), createPosition(1, 4)),
                Arguments.of(createPosition(2, 5), createPosition(1, 4))
        );
    }

    private static Stream<Arguments> distanceTwoDiagonalPositions() {
        return Stream.of(
                Arguments.of(createPosition(7, 3), createPosition(9, 5)),
                Arguments.of(createPosition(7, 5), createPosition(9, 3)),
                Arguments.of(createPosition(9, 3), createPosition(7, 5)),
                Arguments.of(createPosition(9, 5), createPosition(7, 3)),
                Arguments.of(createPosition(0, 3), createPosition(2, 5)),
                Arguments.of(createPosition(0, 5), createPosition(2, 3)),
                Arguments.of(createPosition(2, 3), createPosition(0, 5)),
                Arguments.of(createPosition(2, 5), createPosition(0, 3))
        );
    }

    private static Stream<Arguments> impossibleDiagonalPositions() {
        return Stream.of(
                Arguments.of(createPosition(1, 1), createPosition(4, 4)),
                Arguments.of(createPosition(7, 3), createPosition(8, 5)),
                Arguments.of(createPosition(0, 3), createPosition(1, 5)),
                Arguments.of(createPosition(8, 3), createPosition(8, 5))
        );
    }
}
