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

class ChariotTest {

    private final Palace palace = new Palace();
    private final Chariot chariot = new Chariot(Side.CHO);

    private final List<Path> horizontalPath = List.of(
            new Path(List.of(
                    Point.of(1, 0), Point.of(2, 0), Point.of(3, 0),
                    Point.of(4, 0), Point.of(5, 0), Point.of(6, 0),
                    Point.of(7, 0), Point.of(8, 0), Point.of(9, 0)
            ), false)
    );

    private final List<Path> verticalPath = List.of(
            new Path(List.of(
                    Point.of(0, 1), Point.of(0, 2), Point.of(0, 3),
                    Point.of(0, 4), Point.of(0, 5), Point.of(0, 6),
                    Point.of(0, 7), Point.of(0, 8)
            ), false)
    );

    @Nested
    @DisplayName("patterns()")
    class Patterns {

        @Test
        @DisplayName("4가지 방향 패턴을 반환한다")
        void patterns() {
            List<Pattern> expected = List.of(
                    new Pattern(List.of(Direction.NORTH)),
                    new Pattern(List.of(Direction.EAST)),
                    new Pattern(List.of(Direction.WEST)),
                    new Pattern(List.of(Direction.SOUTH))
            );

            List<Pattern> actual = chariot.patterns(Point.of(0, 0), palace);

            assertThat(actual)
                    .hasSize(4)
                    .containsExactlyInAnyOrderElementsOf(expected);
        }

        @Test
        @DisplayName("궁성 모서리에 있으면 대각선 방향 패턴이 추가된다")
        void patterns_inPalaceCorner() {
            List<Pattern> actual = chariot.patterns(Point.of(0, 3), palace);

            assertThat(actual).hasSize(5);
            assertThat(actual).contains(new Pattern(List.of(Direction.NORTH_EAST)));
        }
    }

    @Nested
    @DisplayName("availablePoints()")
    class AvailablePoints {

        @Test
        @DisplayName("경로 위에 기물이 없으면 보드 끝까지 가능한 모든 좌표를 반환한다")
        void availablePoints() {
            List<Path> paths = List.of(horizontalPath.get(0), verticalPath.get(0));

            List<Point> actual = chariot.availablePoints(paths, Map.of(), palace);

            assertThat(actual).containsExactlyInAnyOrder(
                    Point.of(1, 0), Point.of(2, 0), Point.of(3, 0),
                    Point.of(4, 0), Point.of(5, 0), Point.of(6, 0),
                    Point.of(7, 0), Point.of(8, 0), Point.of(9, 0),
                    Point.of(0, 1), Point.of(0, 2), Point.of(0, 3),
                    Point.of(0, 4), Point.of(0, 5), Point.of(0, 6),
                    Point.of(0, 7), Point.of(0, 8)
            );
        }

        @Test
        @DisplayName("경로에 기물(적군)이 있으면 해당 좌표까지만 반환한다")
        void availablePoints_otherSideOnPath() {
            Map<Point, Piece> board = Map.of(Point.of(6, 0), new Chariot(Side.HAN));

            List<Point> actual = chariot.availablePoints(horizontalPath, board, palace);

            assertThat(actual).containsExactlyInAnyOrder(
                    Point.of(1, 0), Point.of(2, 0), Point.of(3, 0),
                    Point.of(4, 0), Point.of(5, 0), Point.of(6, 0)
            );
        }

        @Test
        @DisplayName("경로에 기물(아군)이 있으면 해당 좌표까지만 반환한다")
        void availablePoints_sameSideOnPath() {
            Map<Point, Piece> board = Map.of(Point.of(0, 2), new Chariot(Side.CHO));

            List<Point> actual = chariot.availablePoints(verticalPath, board, palace);

            assertThat(actual).containsExactlyInAnyOrder(
                    Point.of(0, 1), Point.of(0, 2)
            );
        }

        @Nested
        @DisplayName("궁성 대각선 이동:")
        class PalaceDiagonal {

            private final List<Path> diagonalPath = List.of(
                    new Path(List.of(Point.of(1, 4), Point.of(2, 5)), true)
            );

            @Test
            @DisplayName("궁성 대각선 경로에서 이동 가능한 모든 대각선 좌표를 반환한다")
            void availablePoints_diagonal() {
                List<Point> actual = chariot.availablePoints(diagonalPath, Map.of(), palace);

                assertThat(actual).containsExactlyInAnyOrder(
                        Point.of(1, 4),
                        Point.of(2, 5)
                );
            }

            @Test
            @DisplayName("궁성 밖의 대각선 좌표는 이동 불가하다")
            void availablePoints_diagonalOutPalace() {
                List<Path> path = List.of(
                        new Path(List.of(Point.of(2, 5), Point.of(3, 6)), true)
                );

                List<Point> actual = chariot.availablePoints(path, Map.of(), palace);

                assertThat(actual).containsExactlyInAnyOrder(Point.of(2, 5));
            }

        }
    }
}
