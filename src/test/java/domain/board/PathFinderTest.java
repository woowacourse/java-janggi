package domain.board;

import domain.board.pathfinder.PathFinder;
import domain.point.Direction;
import domain.point.Path;
import domain.point.Point;
import fixture.BoardFixture;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PathFinderTest {

    @ParameterizedTest(name = "위치 : {0}")
    @MethodSource("pointsInDefaultBoard")
    void 장기판에_존재하는_위치면_true를_반환한다(final Point point) {
        // given
        final PathFinder pathFinder = BoardFixture.createDefaultPathFinder();

        // when
        final boolean actual = pathFinder.existsPoint(point);

        // then
        Assertions.assertThat(actual).isTrue();
    }

    @Test
    void 특정_위치에서_특정_방향으로_가는_경로가_존재하면_true를_반환한다() {
        // given
        final PathFinder pathFinder = BoardFixture.createDefaultPathFinder();
        final Point point = Point.of(2, 1);
        Direction direction = Direction.UP;

        // when
        final boolean actual = pathFinder.hasNextPoint(point, direction);

        // then
        Assertions.assertThat(actual).isTrue();
    }

    @Test
    void 특정_위치에서_특정_방향으로_가는_경로가_없으면_false를_반환한다() {
        // given
        final PathFinder pathFinder = BoardFixture.createDefaultPathFinder();
        final Point point = Point.of(2, 1);
        Direction direction = Direction.LEFT;

        // when
        final boolean actual = pathFinder.hasNextPoint(point, direction);

        // then
        Assertions.assertThat(actual).isFalse();
    }

    @ParameterizedTest(name = "시작 위치 : {0}, 도착 위치: {2}")
    @MethodSource("getNextPointTestCases")
    void 특정_위치에서_특정_방향으로_이동했을_때_도착_위치를_반환한다(final Point source, final Direction direction,
                                            final Point destination) {
        // given
        final PathFinder pathFinder = BoardFixture.createDefaultPathFinder();

        // when
        final Point actual = pathFinder.getNextPoint(source, direction);

        // then
        Assertions.assertThat(actual).isEqualTo(destination);
    }

    @Test
    void 특정_위치에서_경로를_따라_이동한_위치가_판_내부_위치면_true를_반환한다() {
        // given
        final PathFinder pathFinder = BoardFixture.createDefaultPathFinder();
        final Point point = Point.of(2, 3);
        final Path path = Path.RIGHT_RIGHT_UP_PATH;

        // when
        final boolean actual = pathFinder.canMoveByPath(point, path);

        // then
        Assertions.assertThat(actual).isTrue();
    }

    @Test
    void 특정_위치에서_경로를_따라_이동한_위치가_판을_벗어난_위치면_false를_반환한다() {
        // given
        final PathFinder pathFinder = BoardFixture.createDefaultPathFinder();
        final Point point = Point.of(2, 3);
        final Path path = Path.RIGHT_RIGHT_RIGHT_UP_UP_PATH;

        // when
        final boolean actual = pathFinder.canMoveByPath(point, path);

        // then
        Assertions.assertThat(actual).isFalse();
    }

    @ParameterizedTest(name = "시작 위치 : {0}, 도착 위치: {2}")
    @MethodSource("getPointMovedByPathTestCases")
    void 특정_위치에서_경로를_따라_이동한_위치를_반환한다(final Point source, final Path path,
                                     final Point destination) {
        // given
        final PathFinder pathFinder = BoardFixture.createDefaultPathFinder();

        // when
        final Point actual = pathFinder.getPointMovedByPath(source, path);

        // then
        Assertions.assertThat(actual).isEqualTo(destination);
    }

    private static Stream<Arguments> pointsInDefaultBoard() {
        return Stream.of(
                Arguments.arguments(Point.of(1, 1)),
                Arguments.arguments(Point.of(1, 9)),
                Arguments.arguments(Point.of(10, 1)),
                Arguments.arguments(Point.of(10, 9))
        );
    }

    private static Stream<Arguments> getNextPointTestCases() {
        return Stream.of(
                Arguments.arguments(Point.of(1, 1), Direction.DOWN, Point.of(2, 1)),
                Arguments.arguments(Point.of(3, 2), Direction.UP, Point.of(2, 2)),
                Arguments.arguments(Point.of(10, 1), Direction.RIGHT, Point.of(10, 2)),
                Arguments.arguments(Point.of(10, 9), Direction.LEFT, Point.of(10, 8))
        );
    }

    private static Stream<Arguments> getPointMovedByPathTestCases() {
        return Stream.of(
                Arguments.arguments(Point.of(1, 1), Path.DOWN_PATH, Point.of(2, 1)),
                Arguments.arguments(Point.of(3, 2), Path.UP_UP_LEFT_PATH, Point.of(1, 1)),
                Arguments.arguments(Point.of(10, 1), Path.RIGHT_RIGHT_UP_PATH, Point.of(9, 3)),
                Arguments.arguments(Point.of(10, 9), Path.LEFT_LEFT_LEFT_UP_UP_PATH, Point.of(8, 6))
        );
    }
}
