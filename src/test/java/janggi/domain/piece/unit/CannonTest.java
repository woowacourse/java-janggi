package janggi.domain.piece.unit;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.board.Board;
import janggi.domain.board.BoardInfo;
import janggi.domain.path.CandidatePath;
import janggi.domain.path.Direction;
import janggi.domain.path.Movement;
import janggi.domain.piece.Piece;
import janggi.domain.piece.linear.Cannon;
import janggi.domain.piece.single.Soldier;
import janggi.domain.point.Point;
import janggi.domain.side.Side;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class CannonTest {
    public static Stream<Arguments> movements() {
        return Stream.of(
                Arguments.of(
                        Side.CHO,
                        List.of(
                                new Movement(List.of(Direction.NORTH)),
                                new Movement(List.of(Direction.EAST)),
                                new Movement(List.of(Direction.WEST)),
                                new Movement(List.of(Direction.SOUTH))
                        )
                ));
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("movements(): 이동 경로의 방향을 전달한다.")
    void movements(Side side, List<Movement> expected) {
        Piece piece = new Cannon(side);

        List<Movement> movements = piece.getMovements();

        assertThat(movements)
                .hasSameSizeAs(expected)
                .containsAll(expected);
    }

    @Nested
    class AvailablePoints {
        @Test
        @DisplayName("첫번째 기물에 포가 있다면, 경로는 비어있어야 한다.")
        void betweenPoints_firstPieceIsCannon() {
            Side side = Side.CHO;
            CandidatePath candidatePath = new CandidatePath(List.of(
                    new Point(4, 5),
                    new Point(3, 5), new Point(2, 5),
                    new Point(1, 5), new Point(0, 5)
            ));

            BoardInfo boardInfo = new Board(
                    Map.of(new Point(4, 5), new Cannon(Side.HAN)));

            Piece piece = new Cannon(side);
            List<Point> points = piece.availablePoints(List.of(candidatePath), boardInfo);
            List<Point> expected = List.of();

            assertThat(points)
                    .hasSameSizeAs(expected)
                    .containsAll(expected);
        }

        @Test
        @DisplayName("두번째 기물에 포가 있다면 , 마지막 경로를 제외한 사이 경로를 전달한다.")
        void betweenPoints_secondPieceIsCannon() {
            Side side = Side.CHO;
            CandidatePath candidatePath = new CandidatePath(List.of(
                    new Point(5, 6),
                    new Point(5, 7),
                    new Point(5, 8)));

            BoardInfo boardInfo = new Board(
                    Map.of(new Point(5, 6), new Soldier(Side.CHO), new Point(5, 8), new Cannon(Side.CHO)));

            Piece piece = new Cannon(side);
            List<Point> points = piece.availablePoints(List.of(candidatePath), boardInfo);
            List<Point> expected = List.of(new Point(5, 7));

            assertThat(points)
                    .hasSameSizeAs(expected)
                    .containsAll(expected);
        }

        @Test
        @DisplayName("경로에 두개의 기물이 있다면, 기물 사이의 경로를 전달한다.")
        void betweenPoints() {
            Side side = Side.CHO;
            CandidatePath candidatePath = new CandidatePath(List.of(
                    new Point(6, 5),
                    new Point(7, 5),
                    new Point(8, 5),
                    new Point(9, 5)
            ));
            BoardInfo boardInfo = new Board(Map.of(new Point(7, 5), new Soldier(Side.CHO), new Point(9, 5),
                    new Soldier(Side.HAN)));

            Piece piece = new Cannon(side);
            List<Point> points = piece.availablePoints(List.of(candidatePath), boardInfo);
            List<Point> expected = List.of(new Point(8, 5), new Point(9, 5));

            assertThat(points)
                    .hasSameSizeAs(expected)
                    .containsAll(expected);
        }
    }
}
