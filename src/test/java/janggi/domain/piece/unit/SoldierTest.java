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

class SoldierTest {

    private final Palace palace = new Palace();

    @Nested
    @DisplayName("patterns():")
    class Patterns {

        @Test
        @DisplayName("초 진영의 졸은 북, 동, 서 3가지 방향 패턴을 반환한다")
        void patterns_cho() {
            Soldier cho = new Soldier(Side.CHO);
            List<Pattern> expected = List.of(
                    new Pattern(List.of(Direction.NORTH)),
                    new Pattern(List.of(Direction.EAST)),
                    new Pattern(List.of(Direction.WEST))
            );

            List<Pattern> actual = cho.patterns(Point.of(3, 0), palace);

            assertThat(actual)
                    .hasSize(3)
                    .containsExactlyInAnyOrderElementsOf(expected);
        }

        @Test
        @DisplayName("한 진영의 병은 남, 동, 서 3가지 방향 패턴을 반환한다")
        void patterns_han() {
            Soldier han = new Soldier(Side.HAN);
            List<Pattern> expected = List.of(
                    new Pattern(List.of(Direction.SOUTH)),
                    new Pattern(List.of(Direction.EAST)),
                    new Pattern(List.of(Direction.WEST))
            );

            List<Pattern> actual = han.patterns(Point.of(3, 0), palace);

            assertThat(actual)
                    .hasSize(3)
                    .containsExactlyInAnyOrderElementsOf(expected);
        }

        @Test
        @DisplayName("궁성 모서리에 있으면 대각선 방향 패턴이 추가된다")
        void patterns_inPalaceCorner() {
            Soldier cho = new Soldier(Side.CHO);

            List<Pattern> actual = cho.patterns(Point.of(7, 3), palace);

            assertThat(actual).hasSize(4);
            assertThat(actual).contains(new Pattern(List.of(Direction.NORTH_EAST)));

        }
    }

    @Nested
    @DisplayName("availablePoints():")
    class AvailablePoints {

        @Nested
        @DisplayName("초 진영 졸:")
        class Cho {

            private final Soldier soldier = new Soldier(Side.CHO);

            private final List<Path> paths = List.of(
                    new Path(List.of(Point.of(3, 1)), false),
                    new Path(List.of(Point.of(4, 0)), false),
                    new Path(List.of(Point.of(2, 0)), false)
            );

            @Test
            @DisplayName("경로 위에 기물이 없으면 이동 가능한 모든 좌표를 반환한다")
            void availablePoints() {
                List<Point> actual = soldier.availablePoints(paths, Map.of(), palace);

                assertThat(actual).containsExactlyInAnyOrder(
                        Point.of(3, 1),
                        Point.of(4, 0),
                        Point.of(2, 0)
                );
            }

            @Test
            @DisplayName("경로에 적군 기물이 있으면 해당 좌표까지 반환한다")
            void availablePoints_otherSideOnPath() {
                Map<Point, Piece> board = Map.of(Point.of(3, 1), new Soldier(Side.HAN));

                List<Point> actual = soldier.availablePoints(paths, board, palace);

                assertThat(actual).containsExactlyInAnyOrder(
                        Point.of(3, 1),
                        Point.of(4, 0),
                        Point.of(2, 0)
                );
            }

            @Test
            @DisplayName("경로에 아군 기물이 있으면 해당 좌표까지 반환한다")
            void availablePoints_sameSideOnPath() {
                Map<Point, Piece> board = Map.of(Point.of(3, 1), new Soldier(Side.CHO));

                List<Point> actual = soldier.availablePoints(paths, board, palace);

                assertThat(actual).containsExactlyInAnyOrder(
                        Point.of(3, 1),
                        Point.of(4, 0),
                        Point.of(2, 0)
                );
            }
        }

        @Nested
        @DisplayName("한 진영 병:")
        class Han {

            private final Soldier soldier = new Soldier(Side.HAN);

            private final List<Path> paths = List.of(
                    new Path(List.of(Point.of(3, 7)), false),
                    new Path(List.of(Point.of(4, 8)), false),
                    new Path(List.of(Point.of(2, 8)), false)
            );

            @Test
            @DisplayName("경로 위에 기물이 없으면 이동 가능한 모든 좌표를 반환한다")
            void availablePoints() {
                List<Point> actual = soldier.availablePoints(paths, Map.of(), palace);

                assertThat(actual).containsExactlyInAnyOrder(
                        Point.of(3, 7),
                        Point.of(4, 8),
                        Point.of(2, 8)
                );
            }

            @Test
            @DisplayName("경로에 적군 기물이 있으면 해당 좌표까지 반환한다")
            void availablePoints_otherSideOnPath() {
                Map<Point, Piece> board = Map.of(Point.of(3, 7), new Soldier(Side.CHO));

                List<Point> actual = soldier.availablePoints(paths, board, palace);

                assertThat(actual).containsExactlyInAnyOrder(
                        Point.of(3, 7),
                        Point.of(4, 8),
                        Point.of(2, 8)
                );
            }

            @Test
            @DisplayName("경로에 아군 기물이 있으면 해당 좌표까지 반환한다")
            void availablePoints_sameSideOnPath() {
                Map<Point, Piece> board = Map.of(Point.of(3, 7), new Soldier(Side.HAN));

                List<Point> actual = soldier.availablePoints(paths, board, palace);

                assertThat(actual).containsExactlyInAnyOrder(
                        Point.of(3, 7),
                        Point.of(4, 8),
                        Point.of(2, 8)
                );
            }
        }

        @Nested
        @DisplayName("궁성 대각선 이동:")
        class PalaceDiagonal {

            private final Soldier soldier = new Soldier(Side.CHO);

            private final List<Path> diagonalPath = List.of(
                    new Path(List.of(Point.of(8, 4)), true)
            );

            @Test
            @DisplayName("대각선 경로에 기물이 없으면 해당 좌표를 반환한다")
            void availablePoints_diagonal() {

                List<Point> actual = soldier.availablePoints(diagonalPath, Map.of(), palace);

                assertThat(actual).containsExactlyInAnyOrder(Point.of(8, 4));
            }

        }
    }
}
