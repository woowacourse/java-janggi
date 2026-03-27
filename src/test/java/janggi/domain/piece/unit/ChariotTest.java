package janggi.domain.piece.unit;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.board.coordinate.Path;
import janggi.domain.board.coordinate.Point;
import janggi.domain.piece.Direction;
import janggi.domain.piece.Pattern;
import janggi.domain.side.Side;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ChariotTest {
    public static Stream<Arguments> patterns() {
        return Stream.of(
                Arguments.of(
                        Side.CHO,
                        List.of(
                                new Pattern(List.of(Direction.NORTH)),
                                new Pattern(List.of(Direction.EAST)),
                                new Pattern(List.of(Direction.WEST)),
                                new Pattern(List.of(Direction.SOUTH))
                        )
                ));
    }

    public static Stream<Arguments> availablePoints() {
        return Stream.of(
                Arguments.of(Side.CHO,
                        List.of(
                                new Path(List.of(
                                        new Point(1, 0), new Point(2, 0),
                                        new Point(3, 0), new Point(4, 0),
                                        new Point(5, 0), new Point(6, 0),
                                        new Point(7, 0), new Point(8, 0),
                                        new Point(9, 0)
                                )),
                                new Path(List.of(
                                        new Point(0, 1), new Point(0, 2),
                                        new Point(0, 3), new Point(0, 4),
                                        new Point(0, 5), new Point(0, 6),
                                        new Point(0, 7), new Point(0, 8)
                                ))
                        ),
                        Map.of(new Point(6, 0), new Chariot(Side.HAN), new Point(0, 2), new Chariot(Side.CHO)),
                        List.of(
                                new Point(0,1),
                                new Point(0,2),
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
    void availablePoints(Side side, List<Path> paths, Map<Point, Piece> piecesOnPaths, List<Point> expected) {
        Piece piece = new Chariot(side);

        List<Point> points = piece.availablePoints(paths, piecesOnPaths);

        assertThat(expected.containsAll(points)).isTrue();
        assertThat(points.size()).isEqualTo(expected.size());
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("patterns(): 이동 경로의 방향을 전달한다.")
    void patterns(Side side, List<Pattern> expected) {
        Piece piece = new Chariot(side);

        List<Pattern> patterns = piece.patterns();

        assertThat(expected.containsAll(patterns)).isTrue();
        assertThat(patterns.size()).isEqualTo(expected.size());
    }
}
