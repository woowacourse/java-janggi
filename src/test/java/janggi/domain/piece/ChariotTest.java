package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.board.Board;
import janggi.domain.path.Direction;
import janggi.domain.path.Movement;
import janggi.domain.piece.linear.Chariot;
import janggi.domain.point.Point;
import janggi.domain.side.Side;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ChariotTest {
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

    public static Stream<Arguments> availablePoints() {
        return Stream.of(
                Arguments.of(Side.CHO,
                        new Point(0, 0),
                        Map.of(new Point(6, 0), new Chariot(Side.HAN), new Point(0, 2), new Chariot(Side.CHO)),
                        List.of(
                                new Point(0, 1),
                                new Point(1, 0),
                                new Point(2, 0),
                                new Point(3, 0),
                                new Point(4, 0),
                                new Point(5, 0),
                                new Point(6, 0)
                        ))

        );
    }


    @ParameterizedTest
    @MethodSource
    @DisplayName("availablePoints(): 이동 가능한 좌표의 목록을 반환한다.")
    void availablePoints(Side side, Point point, Map<Point, Piece> piecesOnPaths,
                         List<Point> expected) {
        Piece piece = new Chariot(side);

        List<Point> points = piece.availablePoints(point, new Board(piecesOnPaths));

        assertThat(points)
                .hasSameSizeAs(expected)
                .containsAll(expected);
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("movements(): 이동 경로의 방향을 전달한다.")
    void movements(Side side, List<Movement> expected) {
        Piece piece = new Chariot(side);

        List<Movement> movements = piece.getMovements();

        assertThat(movements)
                .hasSameSizeAs(expected)
                .containsAll(expected);
    }
}
