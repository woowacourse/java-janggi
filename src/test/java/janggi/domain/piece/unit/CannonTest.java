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

class CannonTest {

    private final Palace palace = new Palace();
    private final Cannon cannon = new Cannon(Side.CHO);

    private final List<Path> eastPath = List.of(
            new Path(List.of(
                    Point.of(3, 1), Point.of(4, 1), Point.of(5, 1),
                    Point.of(6, 1), Point.of(7, 1), Point.of(8, 1), Point.of(9, 1)
            ), false)
    );

    private final List<Path> northPath = List.of(
            new Path(List.of(
                    Point.of(2, 2), Point.of(2, 3), Point.of(2, 4),
                    Point.of(2, 5), Point.of(2, 6), Point.of(2, 7), Point.of(2, 8)
            ), false)
    );

    private final List<Path> westPath = List.of(
            new Path(List.of(
                    Point.of(1, 1), Point.of(0, 1)
            ), false)
    );

    private final List<Path> southPath = List.of(
            new Path(List.of(
                    Point.of(2, 0)
            ), false)
    );

    @Nested
    @DisplayName("patterns():")
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

            List<Pattern> actual = cannon.patterns(Point.of(2, 1), palace);

            assertThat(actual)
                    .hasSize(4)
                    .containsExactlyInAnyOrderElementsOf(expected);
        }

        @Test
        @DisplayName("궁성 모서리에 있으면 대각선 방향 패턴이 추가된다")
        void patterns_inPalaceCorner() {
            List<Pattern> actual = cannon.patterns(Point.of(0, 3), palace);

            assertThat(actual).hasSize(5);
            assertThat(actual).contains(new Pattern(List.of(Direction.NORTH_EAST)));
        }
    }

    @Nested
    @DisplayName("availablePoints():")
    class AvailablePoints {

        @Test
        @DisplayName("포는 중간에 기물 하나가 없으면 이동이 불가하다")
        void availablePoints_noPiece() {
            List<Point> actual = cannon.availablePoints(eastPath, Map.of(), palace);

            assertThat(actual).isEmpty();
        }

        @Test
        @DisplayName("포는 기물 하나를 넘으며 이동한다")
        void availablePoints_overPiece() {
            Map<Point, Piece> board = Map.of(
                    Point.of(4, 1), new Soldier(Side.CHO),
                    Point.of(6, 1), new Soldier(Side.HAN)
            );

            List<Point> actual = cannon.availablePoints(eastPath, board, palace);

            assertThat(actual).containsExactlyInAnyOrder(
                    Point.of(5, 1),
                    Point.of(6, 1)
            );
        }

        @Test
        @DisplayName("포는 다른 포를 넘을 수 없다")
        void availablePoints_cannotOverCannon() {
            Map<Point, Piece> board = Map.of(
                    Point.of(1, 1), new Cannon(Side.HAN)
            );

            List<Point> actual = cannon.availablePoints(westPath, board, palace);

            assertThat(actual).isEmpty();
        }

        @Test
        @DisplayName("포는 다른 포를 잡을 수 없다")
        void availablePoints_cannotCaptureCannon() {
            Map<Point, Piece> board = Map.of(
                    Point.of(2, 3), new Soldier(Side.CHO),
                    Point.of(2, 6), new Cannon(Side.HAN)
            );

            List<Point> actual = cannon.availablePoints(northPath, board, palace);

            assertThat(actual).containsExactlyInAnyOrder(
                    Point.of(2, 4),
                    Point.of(2, 5)
            );
        }

        @Nested
        @DisplayName("궁성 대각선 이동:")
        class PalaceDiagonal {

            private final List<Path> diagonalPath = List.of(
                    new Path(List.of(Point.of(1, 4), Point.of(2, 5)), true)
            );

            @Test
            @DisplayName("궁성 대각선에서 기물 하나를 넘어 이동할 수 있다")
            void availablePoints_diagonalOverPiece() {
                Map<Point, Piece> board = Map.of(Point.of(1, 4), new Soldier(Side.CHO));

                List<Point> actual = cannon.availablePoints(diagonalPath, board, palace);

                assertThat(actual).containsExactlyInAnyOrder(Point.of(2, 5));
            }

            @Test
            @DisplayName("궁성 밖으로 나가는 대각선 좌표는 이동 불가하다")
            void availablePoints_diagonalOutsidePalace() {
                List<Path> path = List.of(
                        new Path(List.of(Point.of(2, 5), Point.of(3, 6)), true)
                );
                Map<Point, Piece> board = Map.of(Point.of(2, 5), new Soldier(Side.CHO));

                List<Point> actual = cannon.availablePoints(path, board, palace);

                assertThat(actual).isEmpty();
            }

        }
    }
}
