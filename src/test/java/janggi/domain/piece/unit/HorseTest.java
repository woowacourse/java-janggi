package janggi.domain.piece.unit;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;

import janggi.domain.board.Palace;
import janggi.domain.board.coordinate.Path;
import janggi.domain.board.coordinate.Point;
import janggi.domain.piece.Direction;
import janggi.domain.piece.Pattern;
import janggi.domain.side.Side;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class HorseTest {

    private final Palace palace = new Palace();
    private final Horse horse = new Horse(Side.CHO);

    private final List<Path> paths = List.of(
            new Path(List.of(Point.of(0, 3), Point.of(1, 4)), true),
            new Path(List.of(Point.of(1, 2), Point.of(2, 3)), true),
            new Path(List.of(Point.of(1, 2), Point.of(2, 1)), true),
            new Path(List.of(Point.of(0, 1), Point.of(1, 0)), true)
    );

    @Nested
    @DisplayName("patterns():")
    class Patterns {

        @Test
        @DisplayName("8가지 이동 패턴을 반환한다")
        void patterns() {
            List<Pattern> expected = List.of(
                    new Pattern(List.of(Direction.NORTH, Direction.NORTH_WEST)),
                    new Pattern(List.of(Direction.NORTH, Direction.NORTH_EAST)),
                    new Pattern(List.of(Direction.EAST, Direction.NORTH_EAST)),
                    new Pattern(List.of(Direction.EAST, Direction.SOUTH_EAST)),
                    new Pattern(List.of(Direction.SOUTH, Direction.SOUTH_EAST)),
                    new Pattern(List.of(Direction.SOUTH, Direction.SOUTH_WEST)),
                    new Pattern(List.of(Direction.WEST, Direction.NORTH_WEST)),
                    new Pattern(List.of(Direction.WEST, Direction.SOUTH_WEST))
            );

            List<Pattern> actual = horse.patterns(Point.of(0, 2), palace);

            assertThat(actual)
                    .hasSize(8)
                    .containsExactlyInAnyOrderElementsOf(expected);
        }
    }

    @Nested
    @DisplayName("availablePoints():")
    class AvailablePoints {

        @Test
        @DisplayName("경로 위의 이동 가능한 모든 좌표를 반환한다")
        void availablePoints() {
            List<Point> actual = horse.availablePoints(paths, Map.of(), palace);

            assertThat(actual).containsExactlyInAnyOrder(
                    Point.of(1, 4),
                    Point.of(2, 3),
                    Point.of(2, 1),
                    Point.of(1, 0)
            );
        }

        @Test
        @DisplayName("경로 중간에 기물(적군)이 있으면 해당 경로로 이동할 수 없다")
        void availablePoints_otherSideOnPath() {
            Map<Point, Piece> board = Map.of(Point.of(0, 3), new Horse(Side.HAN));

            List<Point> actual = horse.availablePoints(paths, board, palace);

            assertThat(actual).containsExactlyInAnyOrder(
                    Point.of(2, 3),
                    Point.of(2, 1),
                    Point.of(1, 0)
            );
        }

        @Test
        @DisplayName("경로 중간에 기물(아군)이 있으면 해당 경로로 이동할 수 없다")
        void availablePoints_sameSideOnPath() {
            Map<Point, Piece> board = Map.of(
                    Point.of(0, 3), new Horse(Side.CHO),
                    Point.of(1, 2), new Horse(Side.CHO)
            );

            List<Point> actual = horse.availablePoints(paths, board, palace);

            assertThat(actual).containsExactlyInAnyOrder(
                    Point.of(1, 0)
            );
        }

        @Test
        @DisplayName("도착 지점에 기물(적군)이 있으면 해당 좌표까지 반환한다")
        void availablePoints_otherSideOnDestination() {
            Map<Point, Piece> board = Map.of(Point.of(1, 4), new Horse(Side.HAN));

            List<Point> actual = horse.availablePoints(paths, board, palace);

            assertThat(actual).containsExactlyInAnyOrder(
                    Point.of(1, 4),
                    Point.of(2, 3),
                    Point.of(2, 1),
                    Point.of(1, 0)
            );
        }

        @Test
        @DisplayName("도착 지점에 기물(아군)이 있으면 해당 좌표까지 반환한다")
        void availablePoints_sameSideOnDestination() {
            Map<Point, Piece> board = Map.of(Point.of(1, 4), new Horse(Side.CHO));

            List<Point> actual = horse.availablePoints(paths, board, palace);

            assertThat(actual).containsExactlyInAnyOrder(
                    Point.of(1, 4),
                    Point.of(2, 3),
                    Point.of(2, 1),
                    Point.of(1, 0)
            );
        }
    }
}
