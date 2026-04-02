package janggi.domain.piece.unit;

import janggi.domain.board.point.Point;
import janggi.domain.path.CandidatePath;
import janggi.domain.path.Direction;
import janggi.domain.path.Movement;
import janggi.domain.side.Side;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.provider.Arguments;

class AdvisorTest {
    public static Stream<Arguments> movements() {
        return Stream.of(
                Arguments.of(
                        Side.CHO,
                        List.of(
                                new Movement(List.of(Direction.NORTH)),
                                new Movement(List.of(Direction.EAST)),
                                new Movement(List.of(Direction.WEST)),
                                new Movement(List.of(Direction.SOUTH)),
                                new Movement(List.of(Direction.NORTH_EAST)),
                                new Movement(List.of(Direction.NORTH_WEST)),
                                new Movement(List.of(Direction.SOUTH_EAST)),
                                new Movement(List.of(Direction.SOUTH_WEST))
                        )
                ));
    }

    public static Stream<Arguments> availablePoints() {
        return Stream.of(
                Arguments.of(Side.CHO,
                        List.of(
                                new CandidatePath(List.of(new Point(0, 2))),
                                new CandidatePath(List.of(new Point(1, 2))),
                                new CandidatePath(List.of(new Point(1, 3))),
                                new CandidatePath(List.of(new Point(1, 4))),
                                new CandidatePath(List.of(new Point(0, 4)))
                        ),
                        Collections.EMPTY_MAP,
                        List.of(
                                new Point(1, 3),
                                new Point(1, 4),
                                new Point(0, 4),
                                new Point(1, 2),
                                new Point(0, 2)
                        ))

        );
    }

//    @ParameterizedTest
//    @MethodSource
//    @DisplayName("availablePoints(): 이동 가능한 좌표의 목록을 반환한다.")
//    void availablePoints(Side side, List<CandidatePath> candidatePaths, Map<Point, Piece> piecesOnPaths,
//                         List<Point> expected) {
//        Piece piece = new Advisor(side);
//
//        List<Point> points = piece.availablePoints(candidatePaths, piecesOnPaths);
//
//        assertThat(expected.containsAll(points)).isTrue();
//        assertThat(expected.size()).isEqualTo(points.size());
//    }
//
//    @ParameterizedTest
//    @MethodSource
//    @DisplayName("movements(): 이동 경로의 방향을 전달한다.")
//    void movements(Side side, List<Movement> expected) {
//        Piece piece = new Advisor(side);
//
//        List<Movement> movements = piece.createCandidateMovement();
//
//        assertThat(expected.containsAll(movements)).isTrue();
//    }
}
