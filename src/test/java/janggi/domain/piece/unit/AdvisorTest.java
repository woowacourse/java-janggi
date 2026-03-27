package janggi.domain.piece.unit;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.board.coordinate.Path;
import janggi.domain.board.coordinate.Point;
import janggi.domain.piece.Direction;
import janggi.domain.piece.Pattern;
import janggi.domain.side.Side;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class AdvisorTest {
    public static Stream<Arguments> patterns() {
        return Stream.of(
                Arguments.of(
                        Side.CHO,
                        List.of(
                                new Pattern(List.of(Direction.NORTH)),
                                new Pattern(List.of(Direction.EAST)),
                                new Pattern(List.of(Direction.WEST)),
                                new Pattern(List.of(Direction.SOUTH)),
                                new Pattern(List.of(Direction.NORTH_EAST)),
                                new Pattern(List.of(Direction.NORTH_WEST)),
                                new Pattern(List.of(Direction.SOUTH_EAST)),
                                new Pattern(List.of(Direction.SOUTH_WEST))
                        )
                ));
    }

    public static Stream<Arguments> availablePoints() {
        return Stream.of(
                Arguments.of(Side.CHO,
                        List.of(
                                new Path(List.of(new Point(0, 2))), new Path(List.of(new Point(1, 2))),
                                new Path(List.of(new Point(1, 3))), new Path(List.of(new Point(1, 4))),
                                new Path(List.of(new Point(0, 4)))
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


    @ParameterizedTest
    @MethodSource
    @DisplayName("availablePoints(): 이동 가능한 좌표의 목록을 반환한다.")
    void availablePoints(Side side, List<Path> paths, Map<Point, Piece> piecesOnPaths, List<Point> expected) {
        Piece piece = new Advisor(side);

        List<Point> points = piece.availablePoints(paths, piecesOnPaths);

        assertThat(expected.containsAll(points)).isTrue();
        assertThat(expected.size()).isEqualTo(points.size());
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("patterns(): 이동 경로의 방향을 전달한다.")
    void patterns(Side side, List<Pattern> expected) {
        Piece piece = new Advisor(side);

        List<Pattern> patterns = piece.patterns();

        assertThat(expected.containsAll(patterns)).isTrue();
    }
}
