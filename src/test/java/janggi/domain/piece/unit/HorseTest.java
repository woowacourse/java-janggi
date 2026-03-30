package janggi.domain.piece.unit;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.board.path.Path;
import janggi.domain.board.point.Point;
import janggi.domain.piece.Direction;
import janggi.domain.piece.Pattern;
import janggi.domain.side.Side;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class HorseTest {
    public static Stream<Arguments> patterns() {
        return Stream.of(
                Arguments.of(Side.CHO, List.of(
                        new Pattern(List.of(Direction.NORTH, Direction.NORTH_WEST)),
                        new Pattern(List.of(Direction.NORTH, Direction.NORTH_EAST)),
                        new Pattern(List.of(Direction.EAST, Direction.NORTH_EAST)),
                        new Pattern(List.of(Direction.EAST, Direction.SOUTH_EAST)),
                        new Pattern(List.of(Direction.SOUTH, Direction.SOUTH_EAST)),
                        new Pattern(List.of(Direction.SOUTH, Direction.SOUTH_WEST)),
                        new Pattern(List.of(Direction.WEST, Direction.NORTH_WEST)),
                        new Pattern(List.of(Direction.WEST, Direction.SOUTH_WEST))
                ))
        );
    }

    public static Stream<Arguments> availablePoints() {
        return Stream.of(
                Arguments.of(Side.CHO,
                        List.of(new Path(List.of(Point.of(1, 1), Point.of(2, 0))),
                                new Path(List.of(Point.of(1, 1), Point.of(2, 2))),
                                new Path(List.of(Point.of(0, 2), Point.of(1, 3)))), // 경로에 기물 존재
                        Map.of(Point.of(0, 2), new Horse(Side.CHO)),
                        List.of(Point.of(2, 0), Point.of(2, 2)))
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("availablePoints(): 이동 가능한 좌표의 목록을 반환한다.")
    void availablePoints(Side side, List<Path> paths, Map<Point, Piece> piecesOnPaths, List<Point> expected) {
        Piece horse = new Horse(side);

        List<Point> points = horse.availablePoints(paths, piecesOnPaths);

        assertThat(expected.containsAll(points)).isTrue();
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("patterns(): 이동 경로의 방향을 전달한다.")
    void patterns(Side side, List<Pattern> expected) {
        Piece horse = new Horse(side);

        List<Pattern> patterns = horse.patterns();

        assertThat(expected.containsAll(patterns)).isTrue();
    }
}
