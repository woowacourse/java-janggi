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

class SoldierTest {
    public static Stream<Arguments> movements() {
        return Stream.of(
                Arguments.of(Side.CHO, List.of(
                        new Movement(List.of(Direction.NORTH)),
                        new Movement(List.of(Direction.EAST)),
                        new Movement(List.of(Direction.WEST)))),

                Arguments.of(Side.HAN, List.of(
                        new Movement(List.of(Direction.SOUTH)),
                        new Movement(List.of(Direction.EAST)),
                        new Movement(List.of(Direction.WEST))))
        );
    }

    public static Stream<Arguments> availablePoints() {
        return Stream.of(
                Arguments.of(Side.CHO,
                        List.of(new CandidatePath(List.of(new Point(4, 0))),
                                new CandidatePath(List.of(new Point(3, 1)))),
                        Collections.EMPTY_MAP,
                        List.of(new Point(4, 0), new Point(3, 1))),
                Arguments.of(Side.HAN,
                        List.of(new CandidatePath(List.of(new Point(5, 8))),
                                new CandidatePath(List.of(new Point(6, 7)))),
                        Collections.EMPTY_MAP,
                        List.of(new Point(5, 8), new Point(6, 7)))
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("availablePoints(): 이동 가능한 좌표의 목록을 반환한다.")
    void availablePoints(Side side, List<CandidatePath> candidatePaths, Map<Point, Piece> piecesOnPaths,
                         List<Point> expected) {
        Piece piece = new Soldier(side);

        List<Point> points = piece.availablePoints(candidatePaths, piecesOnPaths);

        assertThat(expected.containsAll(points)).isTrue();
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("movements(): 이동 경로의 방향을 전달한다.")
    void movements(Side side, List<Movement> expected) {
        Piece piece = new Soldier(side);

        List<Movement> movements = piece.createCandidateMovement();

        assertThat(expected.containsAll(movements)).isTrue();
    }
}
