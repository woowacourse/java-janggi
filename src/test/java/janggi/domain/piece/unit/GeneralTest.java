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

class GeneralTest {

    private final Palace palace = new Palace();
    private final General general = new General(Side.CHO);

    private final List<Path> paths = List.of(
            new Path(List.of(Point.of(0, 3)), true),
            new Path(List.of(Point.of(0, 4)), false),
            new Path(List.of(Point.of(0, 5)), true),
            new Path(List.of(Point.of(1, 3)), false),
            new Path(List.of(Point.of(1, 5)), false),
            new Path(List.of(Point.of(2, 3)), true),
            new Path(List.of(Point.of(2, 4)), false),
            new Path(List.of(Point.of(2, 5)), true)
    );

    @Nested
    @DisplayName("patterns():")
    class Patterns {

        @Test
        @DisplayName("8가지 방향 패턴을 반환한다")
        void patterns() {
            List<Pattern> expected = List.of(
                    new Pattern(List.of(Direction.NORTH)),
                    new Pattern(List.of(Direction.EAST)),
                    new Pattern(List.of(Direction.WEST)),
                    new Pattern(List.of(Direction.SOUTH)),
                    new Pattern(List.of(Direction.NORTH_EAST)),
                    new Pattern(List.of(Direction.NORTH_WEST)),
                    new Pattern(List.of(Direction.SOUTH_EAST)),
                    new Pattern(List.of(Direction.SOUTH_WEST))
            );

            List<Pattern> actual = general.patterns(Point.of(1, 4), palace);

            assertThat(actual)
                    .hasSize(8)
                    .containsExactlyInAnyOrderElementsOf(expected);
        }
    }

    @Nested
    @DisplayName("availablePoints():")
    class AvailablePoints {

        @Test
        @DisplayName("경로 위에 기물이 없으면 궁성 안의 갈 수 있는 모든 좌표를 반환한다")
        void availablePoints() {
            List<Point> actual = general.availablePoints(paths, Map.of(), palace);

            assertThat(actual).containsExactlyInAnyOrder(
                    Point.of(0, 3),
                    Point.of(0, 4),
                    Point.of(0, 5),
                    Point.of(1, 3),
                    Point.of(1, 5),
                    Point.of(2, 3),
                    Point.of(2, 4),
                    Point.of(2, 5)
            );
        }

        @Test
        @DisplayName("궁성 내에 적군 기물이 있으면 해당 좌표까지 반환한다")
        void availablePoints_otherSideOnPath() {
            Map<Point, Piece> board = Map.of(Point.of(0, 3), new General(Side.HAN));

            List<Point> actual = general.availablePoints(paths, board, palace);

            assertThat(actual).containsExactlyInAnyOrder(
                    Point.of(0, 3),
                    Point.of(0, 4),
                    Point.of(0, 5),
                    Point.of(1, 3),
                    Point.of(1, 5),
                    Point.of(2, 3),
                    Point.of(2, 4),
                    Point.of(2, 5)
            );
        }

        @Test
        @DisplayName("궁성 내에 아군 기물이 있으면 해당 좌표까지 반환한다")
        void availablePoints_sameSideOnPath() {
            Map<Point, Piece> board = Map.of(Point.of(0, 3), new General(Side.CHO));

            List<Point> actual = general.availablePoints(paths, board, palace);

            assertThat(actual).containsExactlyInAnyOrder(
                    Point.of(0, 3),
                    Point.of(0, 4),
                    Point.of(0, 5),
                    Point.of(1, 3),
                    Point.of(1, 5),
                    Point.of(2, 3),
                    Point.of(2, 4),
                    Point.of(2, 5)
            );
        }

        @Test
        @DisplayName("궁성 밖으로 나갈 수 없다.")
        void availablePoints_outsidePalace() {
            List<Path> outsidePaths = List.of(
                    new Path(List.of(Point.of(0, 3)), true),
                    new Path(List.of(Point.of(0, 4)), false),
                    new Path(List.of(Point.of(0, 5)), true),
                    new Path(List.of(Point.of(1, 3)), false),
                    new Path(List.of(Point.of(1, 5)), false),
                    new Path(List.of(Point.of(2, 3)), true),
                    new Path(List.of(Point.of(2, 4)), false),
                    new Path(List.of(Point.of(2, 5)), true),
                    new Path(List.of(Point.of(3, 4)), false)
            );

            List<Point> actual = general.availablePoints(outsidePaths, Map.of(), palace);

            assertThat(actual).doesNotContain(Point.of(3, 4));
        }
    }
}
