package janggi.domain.piece.unit;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.path.CandidatePath;
import janggi.domain.path.Direction;
import janggi.domain.path.Movement;
import janggi.domain.piece.Piece;
import janggi.domain.piece.fixed.Elephant;
import janggi.domain.point.Point;
import janggi.domain.side.Side;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ElephantTest {
    public static Stream<Arguments> movements() {
        return Stream.of(
                Arguments.of(Side.CHO, List.of(
                        new Movement(List.of(Direction.NORTH, Direction.NORTH_WEST, Direction.NORTH_WEST)),
                        new Movement(List.of(Direction.NORTH, Direction.NORTH_EAST, Direction.NORTH_EAST)),
                        new Movement(List.of(Direction.EAST, Direction.NORTH_EAST, Direction.NORTH_EAST)),
                        new Movement(List.of(Direction.EAST, Direction.SOUTH_EAST, Direction.SOUTH_EAST)),
                        new Movement(List.of(Direction.SOUTH, Direction.SOUTH_EAST, Direction.SOUTH_EAST)),
                        new Movement(List.of(Direction.SOUTH, Direction.SOUTH_WEST, Direction.SOUTH_WEST)),
                        new Movement(List.of(Direction.WEST, Direction.NORTH_WEST, Direction.NORTH_WEST)),
                        new Movement(List.of(Direction.WEST, Direction.SOUTH_WEST, Direction.SOUTH_WEST))
                ))
        );
    }

    public static Stream<Arguments> availablePoints() {
        return Stream.of(
                Arguments.of(Side.CHO,
                        List.of(new CandidatePath(List.of(new Point(1, 2), new Point(2, 1), new Point(3, 0))),
                                // 경로에 기물 존재
                                new CandidatePath(List.of(new Point(1, 2), new Point(2, 3), new Point(3, 4))),
                                new CandidatePath(List.of(new Point(0, 3), new Point(1, 4), new Point(2, 5)))),
                        // 경로에 기물 존재
                        Map.of(new Point(2, 1), new Elephant(Side.CHO), new Point(0, 3), new Elephant(Side.CHO)),
                        List.of(
                                new Point(3, 0),
                                new Point(3, 4)
                        ))
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("availablePoints(): 이동 가능한 좌표의 목록을 반환한다.")
    void availablePoints(Side side, List<CandidatePath> candidatePaths, Map<Point, Piece> piecesOnPaths,
                         List<Point> expected) {
        Piece piece = new Elephant(side);

        List<Point> points = piece.availablePoints(candidatePaths, piecesOnPaths);

        assertThat(expected.containsAll(points)).isTrue();
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("movements(): 이동 경로의 방향을 전달한다.")
    void movements(Side side, List<Movement> expected) {
        Piece piece = new Elephant(side);

        List<Movement> movements = piece.getMovements();

        assertThat(expected.containsAll(movements)).isTrue();
    }
}
