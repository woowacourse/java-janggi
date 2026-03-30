package janggi.domain.piece.unit;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.board.point.Point;
import janggi.domain.piece.Direction;
import janggi.domain.piece.Movement;
import janggi.domain.piece.path.CandidatePath;
import janggi.domain.side.Side;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class CannonTest {
    public static Stream<Arguments> patterns() {
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
                                        Point.of(6, 5),
                                        Point.of(7, 5),
                                        Point.of(8, 5),
                                        Point.of(9, 5)
                                )),
                                new CandidatePath(List.of(
                                        Point.of(5, 6),
                                        Point.of(5, 7),
                                        Point.of(5, 8)

                                )),
                                new CandidatePath(List.of(
                                        Point.of(4, 5),
                                        Point.of(3, 5), Point.of(2, 5),
                                        Point.of(1, 5), Point.of(0, 5)
                                )),
                                new CandidatePath(List.of(
                                        Point.of(5, 4), Point.of(5, 3),
                                        Point.of(5, 2), Point.of(5, 1),
                                        Point.of(5, 0)
                                ))
                        ),
                        Map.of(Point.of(7, 5), new Soldier(Side.CHO), Point.of(9, 5), new Soldier(Side.HAN),
                                Point.of(5, 4), new Soldier(Side.HAN), Point.of(5, 2), new Soldier(Side.CHO),
                                Point.of(4, 5), new Cannon(Side.HAN),
                                Point.of(5, 6), new Soldier(Side.CHO), Point.of(5, 8), new Cannon(Side.CHO)),
                        List.of(
                                Point.of(8, 5),
                                Point.of(9, 5),
                                Point.of(5, 2),
                                Point.of(5, 3),
                                Point.of(5, 7)
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
        assertThat(expected.containsAll(points)).isTrue();
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("patterns(): 이동 경로의 방향을 전달한다.")
    void patterns(Side side, List<Movement> expected) {
        Piece piece = new Cannon(side);

        List<Movement> movements = piece.createCandidateMovement();

        assertThat(expected.containsAll(movements)).isTrue();
        assertThat(movements.size()).isEqualTo(expected.size());
    }
}
