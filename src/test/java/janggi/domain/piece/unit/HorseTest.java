package janggi.domain.piece.unit;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.board.Board;
import janggi.domain.path.Direction;
import janggi.domain.path.Movement;
import janggi.domain.piece.Piece;
import janggi.domain.piece.stepped.Elephant;
import janggi.domain.piece.stepped.Horse;
import janggi.domain.point.Point;
import janggi.domain.side.Side;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class HorseTest {
    public static Stream<Arguments> movements() {
        return Stream.of(
                Arguments.of(Side.CHO, List.of(
                        new Movement(List.of(Direction.NORTH, Direction.NORTH_WEST)),
                        new Movement(List.of(Direction.NORTH, Direction.NORTH_EAST)),
                        new Movement(List.of(Direction.EAST, Direction.NORTH_EAST)),
                        new Movement(List.of(Direction.EAST, Direction.SOUTH_EAST)),
                        new Movement(List.of(Direction.SOUTH, Direction.SOUTH_EAST)),
                        new Movement(List.of(Direction.SOUTH, Direction.SOUTH_WEST)),
                        new Movement(List.of(Direction.WEST, Direction.NORTH_WEST)),
                        new Movement(List.of(Direction.WEST, Direction.SOUTH_WEST))
                ))
        );
    }

    public static Stream<Arguments> availablePoints() {
        return Stream.of(
                Arguments.of(Side.CHO,
                        new Point(0, 1), // 경로에 기물 존재
                        Map.of(new Point(0, 2), new Elephant(Side.CHO)),
                        List.of(new Point(2, 0), new Point(2, 2)))
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("availablePoints(): 이동 가능한 좌표의 목록을 반환한다.")
    void availablePoints(Side side, Point point, Map<Point, Piece> piecesOnPaths,
                         List<Point> expected) {
        Piece horse = new Horse(side);

        List<Point> points = horse.availablePoints(point, new Board(piecesOnPaths));

        assertThat(points)
                .hasSameSizeAs(expected)
                .containsAll(expected);
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("movements(): 이동 경로의 방향을 전달한다.")
    void movements(Side side, List<Movement> expected) {
        Piece horse = new Horse(side);

        List<Movement> movements = horse.getMovements();

        assertThat(movements)
                .hasSameSizeAs(expected)
                .containsAll(expected);
    }
}
