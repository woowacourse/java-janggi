package janggi.domain.piece.unit;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.board.point.Point;
import janggi.domain.piece.Direction;
import janggi.domain.piece.Movement;
import janggi.domain.piece.path.CandidatePath;
import janggi.domain.side.Side;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class GeneralTest {
    public static Stream<Arguments> patterns() {
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
                                new CandidatePath(List.of(new Point(0, 3))),
                                new CandidatePath(List.of(new Point(0, 4))),
                                new CandidatePath(List.of(new Point(0, 5))),
                                new CandidatePath(List.of(new Point(1, 3))),
                                new CandidatePath(List.of(new Point(1, 5))),
                                new CandidatePath(List.of(new Point(2, 3))),
                                new CandidatePath(List.of(new Point(2, 4))), new CandidatePath(List.of(new Point(2, 5)))
                        ),
                        Collections.EMPTY_MAP,
                        List.of(
                                new Point(0, 3),
                                new Point(0, 4),
                                new Point(0, 5),
                                new Point(1, 3),
                                new Point(1, 5),
                                new Point(2, 3),
                                new Point(2, 4),
                                new Point(2, 5)
                        ))

        );
    }


    @ParameterizedTest
    @MethodSource
    @DisplayName("availablePoints(): 이동 가능한 좌표의 목록을 반환한다.")
    void availablePoints(Side side, List<CandidatePath> candidatePaths, Map<Point, Piece> piecesOnPaths,
                         List<Point> expected) {
        Piece piece = new General(side);

        List<Point> points = piece.availablePoints(candidatePaths, piecesOnPaths);

        assertThat(expected.containsAll(points)).isTrue();
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("patterns(): 이동 경로의 방향을 전달한다.")
    void patterns(Side side, List<Movement> expected) {
        Piece piece = new General(side);

        List<Movement> movements = piece.createCandidateMovement();

        assertThat(expected.containsAll(movements)).isTrue();
    }
}
