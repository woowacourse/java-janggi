package janggi.domain.piece.unit;

import static org.assertj.core.api.Assertions.assertThat;

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

    public static Stream<Arguments> availablePoints() {
        return Stream.of(
                Arguments.of(Side.CHO,
                        List.of(
                                new CandidatePath(List.of(
                                        new Point(6, 5),
                                        new Point(7, 5),
                                        new Point(8, 5),
                                        new Point(9, 5)
                                )),
                                new CandidatePath(List.of(
                                        new Point(5, 6),
                                        new Point(5, 7),
                                        new Point(5, 8)

                                )),
                                new CandidatePath(List.of(
                                        new Point(4, 5),
                                        new Point(3, 5), new Point(2, 5),
                                        new Point(1, 5), new Point(0, 5)
                                )),
                                new CandidatePath(List.of(
                                        new Point(5, 4), new Point(5, 3),
                                        new Point(5, 2), new Point(5, 1),
                                        new Point(5, 0)
                                ))
                        ),
                        Map.of(new Point(7, 5), new Soldier(Side.CHO), new Point(9, 5), new Soldier(Side.HAN),
                                new Point(5, 4), new Soldier(Side.HAN), new Point(5, 2), new Soldier(Side.CHO),
                                new Point(4, 5), new Cannon(Side.HAN),
                                new Point(5, 6), new Soldier(Side.CHO), new Point(5, 8), new Cannon(Side.CHO)),
                        List.of(
                                new Point(8, 5),
                                new Point(9, 5),
                                new Point(5, 2),
                                new Point(5, 3),
                                new Point(5, 7)
                        ))

        );
    }


    @ParameterizedTest
    @MethodSource
    @DisplayName("availablePoints(): 이동 가능한 좌표의 목록을 반환한다.")
    void availablePoints(Side side, List<CandidatePath> candidatePaths, Map<Point, Piece> piecesOnPaths,
                         List<Point> expected) {
        Piece piece = new Cannon(side);

        List<Point> points = piece.availablePoints(candidatePaths, piecesOnPaths);

        assertThat(points.size()).isEqualTo(expected.size());
        assertThat(expected.containsAll(points)).isEqualTo(true);
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("movements(): 이동 경로의 방향을 전달한다.")
    void movements(Side side, List<Movement> expected) {
        Piece piece = new Cannon(side);

        List<Movement> movements = piece.getMovements();

        assertThat(expected.containsAll(movements)).isTrue();
        assertThat(movements.size()).isEqualTo(expected.size());
    }
}
